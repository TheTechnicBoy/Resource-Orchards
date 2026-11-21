package de.thetechnicboy.resourceorchards.tree.block;

import net.minecraft.world.level.block.LeavesBlock;
import de.thetechnicboy.resourceorchards.tree.TreeColorProvider;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

public class TintedLeavesBlock extends LeavesBlock implements TreeColorProvider {
    private final int color;

    public TintedLeavesBlock(int color) {
        super(BlockBehaviour.Properties.of()
                .mapColor(MapColor.PLANT)
                .strength(0.2F)
                .randomTicks()
                .sound(SoundType.GRASS)
                .noOcclusion()
                .isSuffocating((s, l, p) -> false)
                .isViewBlocking((s, l, p) -> false)
                .ignitedByLava()
                .pushReaction(PushReaction.DESTROY));
        this.color = color;
    }

    @Override
    public int getColor() {
        return color;
    }
}