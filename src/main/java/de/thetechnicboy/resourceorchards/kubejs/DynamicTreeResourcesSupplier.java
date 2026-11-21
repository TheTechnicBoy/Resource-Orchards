package de.thetechnicboy.resourceorchards.kubejs;

import net.minecraft.server.packs.PackLocationInfo;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;

public final class DynamicTreeResourcesSupplier implements Pack.ResourcesSupplier {

    private final PackType packType;

    public DynamicTreeResourcesSupplier(PackType packType) {
        this.packType = packType;
    }

    @Override
    public PackResources openPrimary(PackLocationInfo location) {
        return new DynamicTreeResourcePack(location, packType);
    }

    @Override
    public PackResources openFull(PackLocationInfo location, Pack.Metadata metadata) {
        return new DynamicTreeResourcePack(location, packType);
    }
}