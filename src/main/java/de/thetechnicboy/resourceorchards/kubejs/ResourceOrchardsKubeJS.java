package de.thetechnicboy.resourceorchards.kubejs;

import com.mojang.logging.LogUtils;
import de.thetechnicboy.resourceorchards.tree.RegisteredTree;
import de.thetechnicboy.resourceorchards.tree.TreeRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import org.slf4j.Logger;

public class ResourceOrchardsKubeJS {
    private static final Logger LOGGER = LogUtils.getLogger();

    public static RegisteredTree registerTree(String name, int color) {
        RegisteredTree tree = TreeRegistry.registerTree(name, color);
        LOGGER.info("Registered tree '{}' via KubeJS", name);
        return tree;
    }

    private ResourceOrchardsKubeJS() {}
}
