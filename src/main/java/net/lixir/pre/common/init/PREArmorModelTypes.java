package net.lixir.pre.common.init;

import net.lixir.pre.common.ProgressionReEnvisioned;
import net.lixir.pre.common.item.equipment.ArmorModelType;
import net.minecraft.util.Identifier;

public enum PREArmorModelTypes implements ArmorModelType {
    VOCALITE(ProgressionReEnvisioned.id("vocalite")),
    ELECTRUM(ProgressionReEnvisioned.id("electrum"));

    private final Identifier id;

    PREArmorModelTypes(Identifier id) {
        this.id = id;
    }

    @Override
    public Identifier getId() {
        return id;
    }
}
