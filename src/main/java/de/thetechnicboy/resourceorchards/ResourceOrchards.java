package de.thetechnicboy.resourceorchards;

import com.mojang.logging.LogUtils;
import de.thetechnicboy.resourceorchards.client.ModColorHandlers;
import de.thetechnicboy.resourceorchards.kubejs.DynamicTreePackFinder;
import de.thetechnicboy.resourceorchards.registry.ModCreativeTabs;
import de.thetechnicboy.resourceorchards.tree.TreeRegistrationHandler;
import de.thetechnicboy.resourceorchards.tree.TreeRegistry;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import org.slf4j.Logger;

@Mod(ResourceOrchards.MODID)
public class ResourceOrchards {
    public static final String MODID = "resourceorchards";
    private static final Logger LOGGER = LogUtils.getLogger();

    public static ResourceLocation fromNamespaceAndPath(String name){return ResourceLocation.fromNamespaceAndPath(MODID, name);}

    public ResourceOrchards(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::onClientSetup);
        NeoForge.EVENT_BUS.register(this);

        ModCreativeTabs.register(modEventBus);

        modEventBus.addListener(TreeRegistrationHandler::onRegister);
        modEventBus.addListener(DynamicTreePackFinder::onAddPackFinders);
        modEventBus.addListener(ModColorHandlers::registerBlockColors);
        modEventBus.addListener(ModColorHandlers::registerItemColors);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        LOGGER.info("HELLO FROM COMMON SETUP");
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        LOGGER.info("HELLO from server starting");
    }

    private void onClientSetup(FMLClientSetupEvent event){
        event.enqueueWork(() -> {
            TreeRegistry.getTrees().forEach((name, tree) -> {
                ItemBlockRenderTypes.setRenderLayer(
                        tree.amberBlock(),
                        RenderType.translucent()
                );
            });

        });
    }
}
