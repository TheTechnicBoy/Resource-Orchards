package de.thetechnicboy.resourceorchards.tree.block;

import de.thetechnicboy.resourceorchards.tree.TreeColorProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class TintedAmberBlock extends Block implements TreeColorProvider {

    private final int color;

    public TintedAmberBlock(int color) {
        super(BlockBehaviour.Properties.of()
                .mapColor(MapColor.COLOR_ORANGE)
                .strength(3.0F)
                .noOcclusion()
                .sound(SoundType.AMETHYST));
        this.color = color;
    }

    @Override
    public int getColor() {
        return color;
    }




}
