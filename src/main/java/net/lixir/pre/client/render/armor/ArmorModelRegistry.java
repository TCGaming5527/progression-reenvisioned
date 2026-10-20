package net.lixir.pre.client.render.armor;


import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.lixir.pre.common.init.PREItems;
import net.lixir.pre.common.item.equipment.ArmorModelType;
import net.lixir.pre.common.item.equipment.ArmorModelTypes;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.item.ItemConvertible;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public final class ArmorModelRegistry {
    private ArmorModelRegistry() {}

    private static final Map<ArmorModelType, Entry> REGISTRY = new HashMap<>();

    public static void register(
            ArmorModelType type,
            ArmorModelProvider provider,
            EntityModelLayer layerLocation,
            ItemConvertible... items
    ) {
        REGISTRY.put(type, new Entry(provider, layerLocation, items));
    }

    public record Entry(ArmorModelProvider provider, EntityModelLayer layerLocation, ItemConvertible... items) {}

    public static @NotNull Entry get(ArmorModelType type) {
        Entry entry = REGISTRY.get(type);
        if (entry == null) {
            throw new IllegalStateException(
                "No armor model entry registered for " + type.getId()
            );
        }
        return entry;
    }

    public static void renderAll() {
        for (Entry entry : REGISTRY.values()) {
            ArmorRenderer.register(
                    (matrices, vertexConsumers, stack, state, slot, light, contextModel) -> {
                        CustomArmorModel model = entry.provider.create( MinecraftClient.getInstance()
                                .getLoadedEntityModels()
                                .getModelPart(entry.layerLocation()));

                        contextModel.copyTransforms(model);

                        model.setVisibility(slot);
                        ArmorRenderer.renderPart(matrices, vertexConsumers, light, stack, model, model.getTexture());
                    },
                    entry.items
            );
        }
    }

    public static void initialize() {
        register(
                ArmorModelTypes.VOCALITE,
                VocaliteArmorModel::new,
                VocaliteArmorModel.LAYER_LOCATION,
                PREItems.VOCALITE_HELMET,
                PREItems.VOCALITE_CHESTPLATE,
                PREItems.VOCALITE_LEGGINGS,
                PREItems.VOCALITE_BOOTS
        );
        register(
                ArmorModelTypes.ELECTRUM,
                ElectrumArmorModel::new,
                ElectrumArmorModel.LAYER_LOCATION,
                PREItems.ELECTRUM_HELMET,
                PREItems.ELECTRUM_CHESTPLATE,
                PREItems.ELECTRUM_LEGGINGS,
                PREItems.ELECTRUM_BOOTS);
    }
}
