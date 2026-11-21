package de.thetechnicboy.resourceorchards.tree.item;

import de.thetechnicboy.resourceorchards.tree.TreeColorProvider;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;

public class TintedRoastedAcornItem extends Item implements TreeColorProvider {
    private final int color;

    public TintedRoastedAcornItem(int color) {
        super(new Item.Properties()
                .food(new FoodProperties.Builder()
                        .nutrition(2)
                        .saturationModifier(0.3F)
                        .build()));
        this.color = color;
    }

    @Override
    public int getColor() {
        return color;
    }
}
