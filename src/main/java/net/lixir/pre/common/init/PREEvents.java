package net.lixir.pre.common.init;

import net.fabricmc.fabric.api.item.v1.EnchantmentEvents;
import net.fabricmc.fabric.api.util.TriState;
import net.lixir.pre.common.init.tag.PREItemTags;
import net.minecraft.enchantment.Enchantments;

public interface PREEvents {
    static void initialize() {
        EnchantmentEvents.ALLOW_ENCHANTING.register((registryEntry, itemStack, enchantingContext) -> {
            if (itemStack.isIn(PREItemTags.SPEARS) &&
                    (registryEntry.matchesKey(Enchantments.SHARPNESS) ||
                            registryEntry.matchesKey(PREEnchantments.LUNGE) ||
                            registryEntry.matchesKey(Enchantments.FIRE_ASPECT) ||
                            registryEntry.matchesKey(Enchantments.SMITE) ||
                            registryEntry.matchesKey(Enchantments.BANE_OF_ARTHROPODS) ||
                            registryEntry.matchesKey(Enchantments.SHARPNESS) ||
                            registryEntry.matchesKey(Enchantments.KNOCKBACK) ||
                            registryEntry.matchesKey(Enchantments.LOOTING)
                    ))
                return TriState.TRUE;
            return TriState.DEFAULT;
        });

    }
}
