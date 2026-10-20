package net.lixir.pre.common.item;

import net.lixir.pre.common.ProgressionReEnvisioned;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

public enum HandTransformationTypes implements HandTransformationType {
    SPEAR(ProgressionReEnvisioned.id("spear"));

    @NotNull
    private final Identifier id;

    HandTransformationTypes(@NotNull Identifier id) {
        this.id = id;
    }

    @Override
    public @NotNull Identifier getId() {
        return id;
    }
}
