package net.lixir.pre.common.init;

import net.lixir.pre.common.ProgressionReEnvisioned;
import net.minecraft.item.equipment.EquipmentAsset;
import net.minecraft.item.equipment.EquipmentAssetKeys;
import net.minecraft.registry.RegistryKey;

public class PREEquipmentAssetKeys {
    public static final RegistryKey<EquipmentAsset> ROSE_GOLD = register("rose_gold");
    public static final RegistryKey<EquipmentAsset> ELECTRUM = register("electrum");
    public static final RegistryKey<EquipmentAsset> ALUMNABRASS = register("alumnabrass");
    public static final RegistryKey<EquipmentAsset> BRASS = register("brass");
    public static final RegistryKey<EquipmentAsset> VOCALITE = register("vocalite");
    public static final RegistryKey<EquipmentAsset> COPPER = register("copper");

    static RegistryKey<EquipmentAsset> register(String name) {
        return RegistryKey.of(EquipmentAssetKeys.REGISTRY_KEY, ProgressionReEnvisioned.id(name));
    }
}
