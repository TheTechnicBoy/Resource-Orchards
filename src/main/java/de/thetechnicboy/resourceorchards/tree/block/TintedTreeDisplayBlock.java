package de.thetechnicboy.resourceorchards.tree.block;

import de.thetechnicboy.resourceorchards.tree.TreeColorProvider;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;

public class TintedTreeDisplayBlock extends Block implements TreeColorProvider {
    private final int color;

    public TintedTreeDisplayBlock(int color) {
        super(BlockBehaviour.Properties.of()
                .mapColor(MapColor.PLANT)
                .noCollission()
                .instabreak()
                .sound(SoundType.GRASS));
        this.color = color;
    }

    @Override
    public int getColor() {
        return color;
    }
}