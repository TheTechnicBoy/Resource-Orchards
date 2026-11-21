package de.thetechnicboy.resourceorchards.client;


import net.minecraft.world.level.block.Block;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import de.thetechnicboy.resourceorchards.ResourceOrchards;
import de.thetechnicboy.resourceorchards.tree.RegisteredTree;
import de.thetechnicboy.resourceorchards.tree.TreeColorProvider;
import de.thetechnicboy.resourceorchards.tree.TreeRegistry;

import java.util.List;
import java.util.stream.Stream;

public final class ModColorHandlers {

    @SubscribeEvent
    public static void registerBlockColors(RegisterColorHandlersEvent.Block event) {
        List<Block> blocks = TreeRegistry.getTrees().values().stream()
                .flatMap((RegisteredTree tree) -> Stream.<Block>of(tree.logBlock(), tree.leaveBlock(), tree.saplingBlock(), tree.amberBlock(), tree.pottedTreeBlock()))
                .toList();

        event.register((state, level, pos, tintIndex) ->
                        state.getBlock() instanceof TreeColorProvider provider ? provider.getColor() : -1,
                blocks.toArray(new Block[0]));
    }

    @SubscribeEvent
    public static void registerItemColors(RegisterColorHandlersEvent.Item event) {
        var items = TreeRegistry.getTrees().values().stream()
                .flatMap((RegisteredTree tree) -> Stream.<net.minecraft.world.item.Item>of(
                        tree.logItem(), tree.leavesItem(), tree.saplingItem(), tree.amberItem(),
                        tree.acornItem(), tree.resinItem(), tree.roastedAcornItem()))
                .toList();

        event.register((stack, tintIndex) -> {
            net.minecraft.world.item.Item item = stack.getItem();
            if (item instanceof net.minecraft.world.item.BlockItem blockItem
                    && blockItem.getBlock() instanceof TreeColorProvider provider) {
                return provider.getColor();
            }
            if (item instanceof TreeColorProvider provider) {
                return provider.getColor();
            }
            return -1;
        }, items.toArray(new net.minecraft.world.item.Item[0]));
    }

    private ModColorHandlers() {}
}