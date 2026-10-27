package net.lixir.pre.common.init.tag;

import net.lixir.pre.common.ProgressionReEnvisioned;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;

public interface PREDamageTypeTags {
    TagKey<DamageType> EXPLOSION_PROTECTION_PROTECTS = of("explosion_protection_protects");
    TagKey<DamageType> MAGIC_PROTECTION_PROTECTS = of("magic_protection_protects");
    TagKey<DamageType> FIRE_PROTECTION_PROTECTS = of("fire_protection_protects");
    TagKey<DamageType> PHYSICAL_PROTECTION_PROTECTS = of("physical_protection_protects");
    TagKey<DamageType> PROJECTILE_PROTECTION_PROTECTS = of("projectile_protection_protects");

    static TagKey<DamageType> of(String name) {
        return TagKey.of(RegistryKeys.DAMAGE_TYPE, ProgressionReEnvisioned.id(name));
    }
}
