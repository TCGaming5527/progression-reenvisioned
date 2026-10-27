package net.lixir.pre.common.init;

import net.lixir.pre.common.ProgressionReEnvisioned;
import net.minecraft.item.equipment.EquipmentAsset;
import net.minecraft.item.equipment.EquipmentAssetKeys;
import net.minecraft.registry.RegistryKey;

public interface PREEquipmentAssetKeys {
    RegistryKey<EquipmentAsset> ROSE_GOLD = register("rose_gold");
    RegistryKey<EquipmentAsset> ELECTRUM = register("electrum");
    RegistryKey<EquipmentAsset> ALUMNABRASS = register("alumnabrass");
    RegistryKey<EquipmentAsset> BRASS = register("brass");
    RegistryKey<EquipmentAsset> VOCALITE = register("vocalite");
    RegistryKey<EquipmentAsset> COPPER = register("copper");

    static RegistryKey<EquipmentAsset> register(String name) {
        return RegistryKey.of(EquipmentAssetKeys.REGISTRY_KEY, ProgressionReEnvisioned.id(name));
    }
}
