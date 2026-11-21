package de.thetechnicboy.resourceorchards.jei;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import de.thetechnicboy.resourceorchards.ResourceOrchards;
import de.thetechnicboy.resourceorchards.tree.RegisteredTree;
import de.thetechnicboy.resourceorchards.tree.TreeRegistry;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.helpers.IGuiHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class TreesCategory implements IRecipeCategory<RegisteredTree> {

    public static final RecipeType<RegisteredTree> RECIPE_TYPE =
            RecipeType.create(ResourceOrchards.MODID, "tree_drops", RegisteredTree.class);

    private final List<IDrawable> icons;
    private final IDrawable arrow;

    private final int outputRows;

    private enum Source {
        LOG("jei.resourceorchards.source.log"),
        LEAVES("jei.resourceorchards.source.leaves");

        final String key;
        Source(String key) { this.key = key; }
    }

    private record Drop(ItemStack stack, double chance, int min, int max, Source source) {}

    public TreesCategory(IGuiHelper guiHelper) {
        icons = new ArrayList<>();
        var trees = TreeRegistry.getTrees().values().stream().toList();
        for (int i = 0; i < trees.size(); i++) {
            var icon = guiHelper.createDrawableItemStack(
                    new ItemStack(trees.get(i).saplingItem())
            );
            icons.add(icon);
        }

        this.arrow = guiHelper.getRecipeArrowFilled();

        AtomicInteger maxDropsCount = new AtomicInteger();
        TreeRegistry.getTrees().forEach((id, tree) -> {
            int dropsCount = 0;
            if(tree.logChance() > 0 && tree.logMax() > 0) dropsCount++;
            if(tree.acornChance() > 0 && tree.acornMax() > 0) dropsCount++;
            if(tree.resinChance() > 0 && tree.resinMax() > 0) dropsCount++;
            if(tree.saplingChance() > 0 && tree.saplingMax() > 0) dropsCount++;
            dropsCount += tree.logExtraDrops().stream().filter(extraDrop -> extraDrop.chance() > 0 && extraDrop.max() > 0).count();
            dropsCount += tree.leavesExtraDrops().stream().filter(extraDrop -> extraDrop.chance() > 0 && extraDrop.max() > 0).count();

            if(dropsCount > maxDropsCount.get()) maxDropsCount.set(dropsCount);
        });

        outputRows = Math.max((int) Math.ceil(maxDropsCount.get() / 4.0), 2);

    }

    @Override
    public RecipeType<RegisteredTree> getRecipeType() {
        return RECIPE_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable(
                "jei.resourceorchards.trees"
        );
    }

    @Override
    public @Nullable IDrawable getIcon() {
        long index = (System.currentTimeMillis() / 2000) % icons.size();

        return icons.get((int) index);
    }

    @Override
    public int getWidth() {
        return 200;
    }

    @Override
    public int getHeight() {
        return 100;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, RegisteredTree recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 70, 70)
                .addItemStack(new ItemStack(recipe.saplingBlock()))
                .setStandardSlotBackground();

        List<Drop> outputs = getDrops(recipe);

        int outputX = 120;
        int outputY = 50 - (outputRows*18/2);

        int slotCounter = 0;
        for (int row = 0; row < outputRows; row++) {
            for (int col = 0; col < 4; col++) {

                var slot = builder.addSlot(
                        RecipeIngredientRole.OUTPUT,
                        outputX + col * 18,
                        outputY + row * 18
                );

                slot.setStandardSlotBackground();

                if (slotCounter < outputs.size()) {
                    Drop drop = outputs.get(slotCounter);
                    slot.addItemStack(drop.stack());
                    slot.addRichTooltipCallback((slotView, tooltip) -> {
                        tooltip.add(Component.translatable("jei.resourceorchards.chance",
                                String.format("%.1f%%", drop.chance() * 100)).withStyle(ChatFormatting.GOLD));
                        tooltip.add(Component.translatable("jei.resourceorchards.amount",
                                        drop.min() == drop.max() ? String.valueOf(drop.min()) : drop.min() + "–" + drop.max())
                                .withStyle(ChatFormatting.GRAY));
                        tooltip.add(Component.translatable("jei.resourceorchards.source",
                                Component.translatable(drop.source().key)).withStyle(ChatFormatting.AQUA));
                    });
                }

                slotCounter++;
            }
        }
    }

    @Override
    public void draw(RegisteredTree recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics graphics, double mouseX, double mouseY) {
        arrow.draw(graphics, 90, 42);

        Minecraft mc = Minecraft.getInstance();

        PoseStack pose = graphics.pose();
        pose.pushPose();

        pose.translate(45, 70, 100);
        pose.scale(10.0F, -10.0F, 10.0F);

        pose.mulPose(Axis.XP.rotationDegrees(30));
        pose.mulPose(Axis.YP.rotationDegrees(45));

        pose.translate(-0.5, -0.5, -0.5);

        RenderSystem.enableDepthTest();

        MultiBufferSource.BufferSource buffer = graphics.bufferSource();

        renderTree(recipe, pose, buffer, mc);

        buffer.endBatch();

        pose.popPose();
    }

    private void renderTree(RegisteredTree recipe, PoseStack pose, MultiBufferSource.BufferSource buffer, Minecraft mc) {
        BlockState log = recipe.logBlock().defaultBlockState();
        BlockState leaves = recipe.leaveBlock().defaultBlockState();

        Random rnd = new Random(recipe.name().hashCode());

        int trunkHeight = 3 + rnd.nextInt(3); // 3-5

        for (int y = 0; y < trunkHeight; y++) {
            renderBlock(
                    log,
                    0,
                    y,
                    0,
                    pose,
                    buffer,
                    mc
            );
        }

        generateLeafCanopy(
                leaves,
                trunkHeight,
                pose,
                buffer,
                mc,
                rnd
        );
    }

    private boolean isAdjacentToTrunk(BlockPos pos) {
        int dx = Math.abs(pos.getX());
        int dz = Math.abs(pos.getZ());

        return dx <= 1 && dz <= 1;
    }

    private void generateLeafCanopy(BlockState leaves, int trunkHeight, PoseStack pose, MultiBufferSource.BufferSource buffer, Minecraft mc, Random rnd) {
        java.util.Set<BlockPos> generatedLeaves = new java.util.HashSet<>();

        int canopyBottom = trunkHeight - 1;
        int canopyTop = trunkHeight + 2;

        for (int y = canopyBottom; y <= canopyTop; y++) {

            int layer = y - canopyBottom;

            int radius;

            if (layer == 0) {
                radius = 2;
            } else if (layer == 1) {
                radius = 2;
            } else if (layer == 2) {
                radius = 1;
            } else {
                radius = 0;
            }

            for (int x = -radius; x <= radius; x++) {
                for (int z = -radius; z <= radius; z++) {

                    double distance = Math.sqrt(x * x + z * z);

                    double chance;

                    if (distance <= 1.0) {
                        chance = 0.95;
                    } else if (distance <= 1.5) {
                        chance = 0.80;
                    } else {
                        chance = 0.55;
                    }

                    if (layer == 3 && x == 0 && z == 0) {
                        chance = 1.0;
                    }

                    if (rnd.nextDouble() < chance) {
                        generatedLeaves.add(
                                new BlockPos(x, y, z)
                        );
                    }
                }
            }
        }

        generatedLeaves.removeIf(pos -> {
            if (isAdjacentToTrunk(pos)) {
                return false;
            }

            for (Direction direction : Direction.values()) {
                BlockPos neighbour = pos.relative(direction);

                if (generatedLeaves.contains(neighbour)) {
                    return false;
                }
            }

            return true;
        });

        generatedLeaves.removeIf(pos -> {
            int neighbours = 0;

            for (Direction direction : Direction.values()) {
                if (generatedLeaves.contains(pos.relative(direction))) {
                    neighbours++;
                }
            }

            return neighbours == 0;
        });


        for (BlockPos pos : generatedLeaves) {
            renderBlock(
                    leaves,
                    pos.getX(),
                    pos.getY(),
                    pos.getZ(),
                    pose,
                    buffer,
                    mc
            );
        }
    }


    private void renderBlock(BlockState state, int x, int y, int z, PoseStack pose, MultiBufferSource.BufferSource buffer, Minecraft mc) {
        pose.pushPose();

        pose.translate(x - 0.5, y - 0.5, z - 0.5);

        mc.getBlockRenderer().renderSingleBlock(
                state,
                pose,
                buffer,
                LightTexture.FULL_BRIGHT,
                OverlayTexture.NO_OVERLAY
        );

        pose.popPose();
    }

    private List<Drop> getDrops(RegisteredTree tree) {
        List<Drop> drops = new ArrayList<>();

        drops.add(new Drop(new ItemStack(tree.logItem()),
                tree.logChance(), tree.logMin(), tree.logMax(), Source.LOG));

        if (tree.resinChance() > 0 && tree.resinMax() > 0) {
            drops.add(new Drop(new ItemStack(tree.resinItem()),
                    tree.resinChance(), tree.resinMin(), tree.resinMax(), Source.LOG));
        }

        for (RegisteredTree.ExtraDrop e : tree.logExtraDrops()) {
            if (e.chance() > 0 && e.max() > 0) {
                drops.add(new Drop(
                        new ItemStack(BuiltInRegistries.ITEM.get(ResourceLocation.parse(e.itemId()))),
                        e.chance(), e.min(), e.max(), Source.LOG));
            }
        }

        if (tree.acornChance() > 0 && tree.acornMax() > 0) {
            drops.add(new Drop(new ItemStack(tree.acornItem()),
                    tree.acornChance(), tree.acornMin(), tree.acornMax(), Source.LEAVES));
        }

        if (tree.saplingChance() > 0 && tree.saplingMax() > 0) {
            drops.add(new Drop(new ItemStack(tree.saplingItem()),
                    tree.saplingChance(), tree.saplingMin(), tree.saplingMax(), Source.LEAVES));
        }

        for (RegisteredTree.ExtraDrop e : tree.leavesExtraDrops()) {
            if (e.chance() > 0 && e.max() > 0) {
                drops.add(new Drop(
                        new ItemStack(BuiltInRegistries.ITEM.get(ResourceLocation.parse(e.itemId()))),
                        e.chance(), e.min(), e.max(), Source.LEAVES));
            }
        }

        return drops;
    }
}