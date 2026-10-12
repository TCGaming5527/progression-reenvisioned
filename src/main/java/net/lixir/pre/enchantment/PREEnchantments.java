package net.lixir.pre.enchantment;

import net.lixir.pre.ProgressionReEnvisioned;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;

public class PREEnchantments {
    public static final RegistryKey<Enchantment> LUNGE = of("lunge");

    private static RegistryKey<Enchantment> of(String id) {
        return RegistryKey.of(RegistryKeys.ENCHANTMENT, ProgressionReEnvisioned.id(id));
    }
}
