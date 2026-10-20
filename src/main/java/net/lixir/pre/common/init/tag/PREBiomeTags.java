package net.lixir.pre.common.init.tag;

import net.lixir.pre.common.ProgressionReEnvisioned;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.world.biome.Biome;

public class PREBiomeTags {
    public static final TagKey<Biome> HAS_WARM_FISHING = of("has_warm_fishing");
    public static final TagKey<Biome> HAS_COLD_FISHING = of("has_cold_fishing");

    private static TagKey<Biome> of(String name) {
        return TagKey.of(RegistryKeys.BIOME, ProgressionReEnvisioned.id(name));
    }
}
