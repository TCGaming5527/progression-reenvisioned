package net.lixir.pre.client.render;

import net.lixir.pre.common.item.HandTransformationType;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public final class HandTransformationRegistry {
    private HandTransformationRegistry() {

    }

    private static final HashMap<Identifier, HandTransformation> REGISTRY = new HashMap<>();

    public static void register(@NotNull HandTransformationType type, HandTransformation transformation) {
        REGISTRY.put(type.getId(), transformation);
    }


    public static HandTransformation getTransformation(Identifier id) {
        return REGISTRY.getOrDefault(id, HandTransformations.EMPTY);
    }
}
