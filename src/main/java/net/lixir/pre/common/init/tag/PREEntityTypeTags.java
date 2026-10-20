package net.lixir.pre.common.init.tag;

import net.lixir.pre.common.ProgressionReEnvisioned;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;

public class PREEntityTypeTags {
    public static final TagKey<EntityType<?>> SENSITIVE_TO_UNDEAD_DAMAGE = of("sensitive_to_undead_damage");
    public static final TagKey<EntityType<?>> SENSITIVE_TO_ENDER_DAMAGE = of("sensitive_to_ender_damage");
    public static final TagKey<EntityType<?>> SENSITIVE_TO_WILD_DAMAGE = of("sensitive_to_wild_damage");
    public static final TagKey<EntityType<?>> SENSITIVE_TO_NETHER_DAMAGE = of("sensitive_to_nether_damage");

    private static TagKey<EntityType<?>> of(String name) {
        return TagKey.of(RegistryKeys.ENTITY_TYPE, ProgressionReEnvisioned.id(name));
    }
}
