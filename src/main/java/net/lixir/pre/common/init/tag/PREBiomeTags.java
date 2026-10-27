package net.lixir.pre.common.init.tag;

import net.lixir.pre.common.ProgressionReEnvisioned;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.world.biome.Biome;

public interface PREBiomeTags {
    TagKey<Biome> HAS_WARM_FISHING = of("has_warm_fishing");
    TagKey<Biome> HAS_COLD_FISHING = of("has_cold_fishing");

    static TagKey<Biome> of(String name) {
        return TagKey.of(RegistryKeys.BIOME, ProgressionReEnvisioned.id(name));
    }
}
