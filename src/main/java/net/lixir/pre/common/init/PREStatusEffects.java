package net.lixir.pre.common.init;

import net.lixir.pre.common.ProgressionReEnvisioned;
import net.lixir.pre.common.mob_effect.UniqueMobEffect;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;

public interface PREStatusEffects {

    RegistryEntry<StatusEffect> GODS_GRACE = register("gods_grace", new UniqueMobEffect(StatusEffectCategory.BENEFICIAL, 0xFFCC00));

    private static RegistryEntry<StatusEffect> register(String id, StatusEffect statusEffect) {
        return Registry.registerReference(Registries.STATUS_EFFECT, ProgressionReEnvisioned.id(id), statusEffect);
    }

    static void initialize() {

    }
}
