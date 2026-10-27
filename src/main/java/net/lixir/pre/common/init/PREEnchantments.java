package net.lixir.pre.common.init;

import net.lixir.pre.common.ProgressionReEnvisioned;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;

public interface PREEnchantments {
    RegistryKey<Enchantment> LUNGE = of("lunge");

    private static RegistryKey<Enchantment> of(String id) {
        return RegistryKey.of(RegistryKeys.ENCHANTMENT, ProgressionReEnvisioned.id(id));
    }
}
