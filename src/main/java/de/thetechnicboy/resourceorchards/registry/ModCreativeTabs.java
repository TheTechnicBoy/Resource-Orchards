package de.thetechnicboy.resourceorchards.registry;


import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import de.thetechnicboy.resourceorchards.ResourceOrchards;
import de.thetechnicboy.resourceorchards.tree.TreeRegistry;

public final class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ResourceOrchards.MODID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> RESOURCE_ORCHARDS_TAB = TABS.register(
            "resource_orchards",
            () -> new AnimatedCreativeTab(
                    CreativeModeTab.builder()
                            .title(Component.translatable("itemGroup." + ResourceOrchards.MODID + ".main"))
                            .displayItems((params, output) -> TreeRegistry.getTrees().values().forEach(tree -> {
                                output.accept(tree.saplingItem());
                                output.accept(tree.logItem());
                                output.accept(tree.leavesItem());
                                output.accept(tree.amberItem());
                                output.accept(tree.resinItem());
                                output.accept(tree.acornItem());
                                output.accept(tree.roastedAcornItem());
                            }))
            )
    );

    public static void register(IEventBus bus) {
            TABS.register(bus);
    }

    private ModCreativeTabs() {}
}