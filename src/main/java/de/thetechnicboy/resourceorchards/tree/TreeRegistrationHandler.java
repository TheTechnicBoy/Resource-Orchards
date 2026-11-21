package de.thetechnicboy.resourceorchards.tree;

import de.thetechnicboy.resourceorchards.ResourceOrchards;
import de.thetechnicboy.resourceorchards.tree.block.*;
import de.thetechnicboy.resourceorchards.tree.item.TintedAcornItem;
import de.thetechnicboy.resourceorchards.tree.item.TintedResinItem;
import de.thetechnicboy.resourceorchards.tree.item.TintedRoastedAcornItem;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.neoforge.registries.RegisterEvent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import java.util.Optional;

public final class TreeRegistrationHandler {

    public static void onRegister(RegisterEvent event) {
        event.register(Registries.BLOCK, helper -> {
            for (RegisteredTree tree : TreeRegistry.getTrees().values()) {
                int opaqueColor = 0xFF000000 | tree.color();

                helper.register(TreeRegistry.id(tree.name(), "_log"),
                        new TintedLogBlock(opaqueColor));

                helper.register(TreeRegistry.id(tree.name(), "_leaves"),
                        new TintedLeavesBlock(opaqueColor));


                ResourceKey<ConfiguredFeature<?, ?>> treeFeatureKey = ResourceKey.create(
                        Registries.CONFIGURED_FEATURE,
                        ResourceLocation.fromNamespaceAndPath(ResourceOrchards.MODID, tree.name() + "_tree"));

                TreeGrower grower = new TreeGrower(
                        ResourceOrchards.MODID + "_" + tree.name(),
                        Optional.empty(),
                        Optional.of(treeFeatureKey),
                        Optional.empty());

                helper.register(TreeRegistry.id(tree.name(), "_sapling"),
                        new TintedSaplingBlock(opaqueColor, grower));

                helper.register(TreeRegistry.id(tree.name(), "_amber"),
                        new TintedAmberBlock(opaqueColor));

                helper.register(TreeRegistry.id(tree.name(), "_potted_tree"),
                        new TintedTreeDisplayBlock(opaqueColor));

            }
        });

        event.register(Registries.ITEM, helper -> {
            for (RegisteredTree tree : TreeRegistry.getTrees().values()) {
                int opaqueColor = 0xFF000000 | tree.color();

                Block log = BuiltInRegistries.BLOCK.get(TreeRegistry.id(tree.name(), "_log"));
                Block leaves = BuiltInRegistries.BLOCK.get(TreeRegistry.id(tree.name(), "_leaves"));
                Block sapling = BuiltInRegistries.BLOCK.get(TreeRegistry.id(tree.name(), "_sapling"));
                Block amber = BuiltInRegistries.BLOCK.get(TreeRegistry.id(tree.name(), "_amber"));

                helper.register(TreeRegistry.id(tree.name(), "_log"), new BlockItem(log, new Item.Properties()));
                helper.register(TreeRegistry.id(tree.name(), "_leaves"), new BlockItem(leaves, new Item.Properties()));
                helper.register(TreeRegistry.id(tree.name(), "_sapling"), new BlockItem(sapling, new Item.Properties()));
                helper.register(TreeRegistry.id(tree.name(), "_amber"), new BlockItem(amber, new Item.Properties()));

                helper.register(TreeRegistry.id(tree.name(), "_acorn"),new TintedAcornItem(opaqueColor));
                helper.register(TreeRegistry.id(tree.name(), "_resin"), new TintedResinItem(opaqueColor));
                helper.register(TreeRegistry.id(tree.name(), "_roasted_acorn"), new TintedRoastedAcornItem(opaqueColor));

            }
        });
    }

    private TreeRegistrationHandler() {}
}