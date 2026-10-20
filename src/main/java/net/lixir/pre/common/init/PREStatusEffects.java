package net.lixir.pre.common.init;

import net.lixir.pre.common.ProgressionReEnvisioned;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;

public class PREStatusEffects {
    //public static final RegistryEntry<StatusEffect> REWIND = register("rewind", new RewindStatusEffect(StatusEffectCategory.BENEFICIAL, 3402751));

    private static RegistryEntry<StatusEffect> register(String id, StatusEffect statusEffect) {
        return Registry.registerReference(Registries.STATUS_EFFECT, ProgressionReEnvisioned.id(id), statusEffect);
    }

    public static void initialize() {

    }
}
