package de.thetechnicboy.resourceorchards.registry;

import de.thetechnicboy.resourceorchards.tree.TreeRegistry;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class AnimatedCreativeTab extends CreativeModeTab {

    public AnimatedCreativeTab(Builder builder) {
        super(builder);
    }

    @Override
    public ItemStack getIconItem() {
        var trees = TreeRegistry.getTrees().values().stream().toList();

        if (trees.isEmpty()) {
            return new ItemStack(Items.OAK_SAPLING);
        }

        long index = (System.currentTimeMillis() / 2000) % trees.size();

        return new ItemStack(trees.get((int) index).saplingItem());
    }
}