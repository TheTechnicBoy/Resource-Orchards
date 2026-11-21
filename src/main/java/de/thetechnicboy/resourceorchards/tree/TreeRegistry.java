package de.thetechnicboy.resourceorchards.tree;

import de.thetechnicboy.resourceorchards.ResourceOrchards;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public final class TreeRegistry {
    private static final Map<String, RegisteredTree> TREES = new LinkedHashMap<>();


    public static RegisteredTree registerTree(String name, int color) {
        if (TREES.containsKey(name)) {
            throw new IllegalStateException("Tree '" + name + "' is already registered");
        }

        RegisteredTree tree = new RegisteredTree(name, color);
        TREES.put(name, tree);
        return tree;
    }

    public static Map<String, RegisteredTree> getTrees() {
        return Collections.unmodifiableMap(TREES);
    }

    public static ResourceLocation id(String name, String suffix) {
        return ResourceLocation.fromNamespaceAndPath(ResourceOrchards.MODID, name + suffix);
    }

    private TreeRegistry() {}
}