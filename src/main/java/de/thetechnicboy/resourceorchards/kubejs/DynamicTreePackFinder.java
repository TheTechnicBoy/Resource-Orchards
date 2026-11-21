package de.thetechnicboy.resourceorchards.kubejs;

import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackLocationInfo;
import net.minecraft.server.packs.PackSelectionConfig;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.neoforged.neoforge.event.AddPackFindersEvent;

import java.util.Optional;

public final class DynamicTreePackFinder {

    public static void onAddPackFinders(AddPackFindersEvent event) {
        PackType packType = event.getPackType();
        if (packType != PackType.CLIENT_RESOURCES && packType != PackType.SERVER_DATA) return;

        String id = packType == PackType.CLIENT_RESOURCES
                ? "resourceorchards_dynamic_trees_assets"
                : "resourceorchards_dynamic_trees_data";

        PackLocationInfo locationInfo = new PackLocationInfo(
                id,
                Component.literal("Resource Orchards (Dynamic Trees)"),
                PackSource.BUILT_IN,
                Optional.empty()
        );

        Pack pack = Pack.readMetaAndCreate(
                locationInfo,
                new DynamicTreeResourcesSupplier(packType),
                packType,
                new PackSelectionConfig(true, Pack.Position.TOP, false)
        );

        if (pack != null) {
            event.addRepositorySource(consumer -> consumer.accept(pack));
        }
    }

    private DynamicTreePackFinder() {}
}