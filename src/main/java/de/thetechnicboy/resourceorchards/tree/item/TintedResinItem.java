package de.thetechnicboy.resourceorchards.tree.item;

import de.thetechnicboy.resourceorchards.tree.TreeColorProvider;
import net.minecraft.world.item.Item;

public class TintedResinItem extends Item implements TreeColorProvider {
    private final int color;

    public TintedResinItem(int color) {
        super(new Item.Properties());
        this.color = color;
    }

    @Override
    public int getColor() {
        return color;
    }
}
