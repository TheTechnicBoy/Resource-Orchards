package de.thetechnicboy.resourceorchards.tree.block;


import net.minecraft.world.level.block.RotatedPillarBlock;
import de.thetechnicboy.resourceorchards.tree.TreeColorProvider;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;

public class TintedLogBlock extends RotatedPillarBlock implements TreeColorProvider {
    private final int color;

    public TintedLogBlock(int color) {
        super(BlockBehaviour.Properties.of()
                .mapColor(MapColor.WOOD)
                .strength(2.0F)
                .sound(SoundType.WOOD));
        this.color = color;
    }

    @Override
    public int getColor() {
        return color;
    }
}
