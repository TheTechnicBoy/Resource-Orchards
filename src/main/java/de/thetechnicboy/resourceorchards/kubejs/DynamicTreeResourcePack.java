package de.thetechnicboy.resourceorchards.kubejs;

import de.thetechnicboy.resourceorchards.ResourceOrchards;
import net.minecraft.SharedConstants;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackLocationInfo;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.MetadataSectionSerializer;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import net.minecraft.server.packs.resources.IoSupplier;

import javax.annotation.Nullable;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Set;

public class DynamicTreeResourcePack implements PackResources {

    private final PackLocationInfo locationInfo;
    private final PackMetadataSection packMetadata;

    public DynamicTreeResourcePack(PackLocationInfo locationInfo, PackType packType) {
        this.locationInfo = locationInfo;
        this.packMetadata = new PackMetadataSection(
                Component.literal("Resource Orchards dynamic tree assets"),
                SharedConstants.getCurrentVersion().getPackVersion(packType)
        );
    }

    @Override
    public PackLocationInfo location() {
        return locationInfo;
    }

    @Nullable
    @Override
    public IoSupplier<InputStream> getRootResource(String... elements) {
        return null;
    }

    @Nullable
    @Override
    public IoSupplier<InputStream> getResource(PackType type, ResourceLocation location) {
        String json;
        if (type == PackType.CLIENT_RESOURCES && location.getNamespace().equals(ResourceOrchards.MODID)) {
            json = DynamicTreeAssets.generate(location.getPath());
        } else if (type == PackType.SERVER_DATA) {
            json = DynamicTreeAssets.generateData(location.getNamespace(), location.getPath());
        } else {
            return null;
        }

        if (json == null) return null;
        byte[] bytes = json.getBytes(StandardCharsets.UTF_8);
        return () -> new ByteArrayInputStream(bytes);
    }

    @Override
    public void listResources(PackType type, String namespace, String path, ResourceOutput resourceOutput) {
        if (type == PackType.CLIENT_RESOURCES && namespace.equals(ResourceOrchards.MODID)) {
            for (String candidate : DynamicTreeAssets.allPaths()) {
                if (candidate.startsWith(path)) {
                    emit(resourceOutput, namespace, candidate, DynamicTreeAssets.generate(candidate));
                }
            }
        } else if (type == PackType.SERVER_DATA) {
            for (String candidate : DynamicTreeAssets.allDataPaths(namespace)) {
                if (candidate.startsWith(path)) {
                    emit(resourceOutput, namespace, candidate, DynamicTreeAssets.generateData(namespace, candidate));
                }
            }
        }
    }

    private static void emit(ResourceOutput output, String namespace, String path, @Nullable String json) {
        if (json == null) return;
        byte[] bytes = json.getBytes(StandardCharsets.UTF_8);
        output.accept(ResourceLocation.fromNamespaceAndPath(namespace, path), () -> new ByteArrayInputStream(bytes));
    }

    @Override
    public Set<String> getNamespaces(PackType type) {
        if (type == PackType.CLIENT_RESOURCES) return Set.of(ResourceOrchards.MODID);
        if (type == PackType.SERVER_DATA) return Set.of(ResourceOrchards.MODID, "minecraft");
        return Set.of();
    }

    @Nullable
    @Override
    @SuppressWarnings("unchecked")
    public <T> T getMetadataSection(MetadataSectionSerializer<T> serializer) {
        if (serializer == PackMetadataSection.TYPE) {
            return (T) packMetadata;
        }
        return null;
    }

    @Override
    public void close() {
    }
}