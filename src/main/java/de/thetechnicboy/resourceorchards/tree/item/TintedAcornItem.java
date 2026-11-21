package de.thetechnicboy.resourceorchards.tree.item;

import de.thetechnicboy.resourceorchards.tree.TreeColorProvider;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;

public class TintedAcornItem extends Item implements TreeColorProvider {
    private final int color;

    public TintedAcornItem(int color) {
        super(new Item.Properties()
                .food(new FoodProperties.Builder()
                        .nutrition(1)
                        .saturationModifier(0.1F)
                        .build()));
        this.color = color;
    }

    @Override
    public int getColor() {
        return color;
    }
}
