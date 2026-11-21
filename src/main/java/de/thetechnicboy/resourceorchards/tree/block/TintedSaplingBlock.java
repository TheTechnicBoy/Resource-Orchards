package de.thetechnicboy.resourceorchards.tree.block;

import de.thetechnicboy.resourceorchards.tree.TreeColorProvider;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

public class TintedSaplingBlock extends SaplingBlock implements TreeColorProvider {
    private final int color;

    public TintedSaplingBlock(int color, TreeGrower treeGrower) {
        super(treeGrower, BlockBehaviour.Properties.of()
                .mapColor(MapColor.PLANT)
                .noCollission()
                .randomTicks()
                .instabreak()
                .sound(SoundType.GRASS)
                .pushReaction(PushReaction.DESTROY));
        this.color = color;
    }

    @Override
    public int getColor() {
        return color;
    }
}