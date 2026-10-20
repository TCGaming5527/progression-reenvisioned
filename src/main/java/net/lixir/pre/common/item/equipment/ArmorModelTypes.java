package net.lixir.pre.common.item.equipment;

import net.lixir.pre.common.ProgressionReEnvisioned;
import net.minecraft.util.Identifier;

public enum ArmorModelTypes implements ArmorModelType {
    VOCALITE(ProgressionReEnvisioned.id("vocalite")),
    ELECTRUM(ProgressionReEnvisioned.id("electrum"));

    private final Identifier id;

    ArmorModelTypes(Identifier id) {
        this.id = id;
    }

    @Override
    public Identifier getId() {
        return id;
    }
}
