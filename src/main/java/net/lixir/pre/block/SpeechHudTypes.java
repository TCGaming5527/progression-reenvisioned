package net.lixir.pre.block;

import net.lixir.pre.ProgressionReEnvisioned;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

public enum SpeechHudTypes implements SpeechHudType {
    RECYCLER(ProgressionReEnvisioned.id("recycler"));

    @NotNull
    private final Identifier id;

    SpeechHudTypes(@NotNull Identifier id) {
        this.id = id;
    }

    @Override
    public @NotNull Identifier getId() {
        return id;
    }
}
