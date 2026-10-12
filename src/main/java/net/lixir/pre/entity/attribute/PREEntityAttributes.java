package net.lixir.pre.entity.attribute;

import net.lixir.pre.ProgressionReEnvisioned;
import net.minecraft.entity.attribute.ClampedEntityAttribute;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;

public class PREEntityAttributes {
    public static final RegistryEntry<EntityAttribute> EXPLOSION_PROTECTION = register(
            "explosion_protection",
            new ClampedEntityAttribute("attribute.name.explosion_protection", 100.0, 0.0, 1000.0).setTracked(false)
    );
    public static final RegistryEntry<EntityAttribute> FIRE_PROTECTION = register(
            "fire_protection",
            new ClampedEntityAttribute("attribute.name.fire_protection", 100.0, 0.0, 1000.0).setTracked(false)
    );
    public static final RegistryEntry<EntityAttribute> PHYSICAL_PROTECTION = register(
            "physical_protection",
            new ClampedEntityAttribute("attribute.name.physical_protection", 100.0, 0.0, 1000.0).setTracked(false)
    );
    public static final RegistryEntry<EntityAttribute> PROJECTILE_PROTECTION = register(
            "projectile_protection",
            new ClampedEntityAttribute("attribute.name.projectile_protection", 100.0, 0.0, 1000.0).setTracked(false)
    );
    public static final RegistryEntry<EntityAttribute> MAGIC_PROTECTION = register(
            "magic_protection",
            new ClampedEntityAttribute("attribute.name.magic_protection", 100.0, 0.0, 1000.0).setTracked(false)
    );
    public static final RegistryEntry<EntityAttribute> NETHER_DAMAGE = register(
            "nether_damage",
            new ClampedEntityAttribute("attribute.name.nether_damage", 100.0, 0.0, 1000.0).setTracked(false)
    );
    public static final RegistryEntry<EntityAttribute> UNDEAD_DAMAGE = register(
            "undead_damage",
            new ClampedEntityAttribute("attribute.name.undead_damage", 100.0, 0.0, 1000.0).setTracked(false)
    );
    public static final RegistryEntry<EntityAttribute> ENDER_DAMAGE = register(
            "ender_damage",
            new ClampedEntityAttribute("attribute.name.ender_damage", 100.0, 0.0, 1000.0).setTracked(false)
    );
    public static final RegistryEntry<EntityAttribute> WILD_DAMAGE = register(
            "wild_damage",
            new ClampedEntityAttribute("attribute.name.wild_damage", 100.0, 0.0, 1000.0).setTracked(false)
    );

    private static RegistryEntry<EntityAttribute> register(String id, EntityAttribute attribute) {
        return Registry.registerReference(Registries.ATTRIBUTE, ProgressionReEnvisioned.id(id), attribute);
    }


    public static void initialize() {

    }
}
