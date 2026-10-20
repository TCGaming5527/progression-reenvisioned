package net.lixir.pre.common.init;

import net.lixir.pre.mixin.accessor.ArmorMaterialsAccessor;
import net.lixir.pre.common.init.tag.PREItemTags;
import net.minecraft.item.equipment.ArmorMaterial;
import net.minecraft.item.equipment.EquipmentType;
import net.minecraft.sound.SoundEvents;

import java.util.Map;

public class PREArmorMaterials {
    public static final ArmorMaterial ELECTRUM = new ArmorMaterial(
            37, createDefenseMap(3, 6, 8, 3, 11), 15, SoundEvents.ITEM_ARMOR_EQUIP_NETHERITE, 3.0F, 0.0F, PREItemTags.REPAIRS_ELECTRUM_ARMOR, PREEquipmentAssetKeys.ELECTRUM
    );
    public static final ArmorMaterial BRASS = new ArmorMaterial(
            33, createDefenseMap(3, 6, 8, 3, 11), 10, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 2.0F, 0.0F, PREItemTags.REPAIRS_BRASS_ARMOR, PREEquipmentAssetKeys.BRASS
    );
    public static final ArmorMaterial ALUMNABRASS = new ArmorMaterial(
            37, createDefenseMap(3, 6, 8, 3, 11), 15, SoundEvents.ITEM_ARMOR_EQUIP_NETHERITE, 3.0F, 0.0F,PREItemTags.REPAIRS_ALUMNABRASS_ARMOR, PREEquipmentAssetKeys.ALUMNABRASS
    );
    public static final ArmorMaterial VOCALITE = new ArmorMaterial(
            37, createDefenseMap(3, 6, 8, 3, 11), 15, SoundEvents.ITEM_ARMOR_EQUIP_NETHERITE, 3.0F, 0.0F, PREItemTags.REPAIRS_VOCALITE_ARMOR, PREEquipmentAssetKeys.VOCALITE
    );
    public static final ArmorMaterial COPPER = new ArmorMaterial(
            15, createDefenseMap(1, 3, 5, 2, 5), 9, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.0F, 0.0F, PREItemTags.REPAIRS_COPPER_ARMOR, PREEquipmentAssetKeys.COPPER
    );
    public static final ArmorMaterial ROSE_GOLD = new ArmorMaterial(
            33, createDefenseMap(3, 6, 8, 3, 11), 10, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 2.0F, 0.0F, PREItemTags.REPAIRS_ROSE_GOLD_ARMOR, PREEquipmentAssetKeys.ROSE_GOLD
    );

    public static Map<EquipmentType, Integer> createDefenseMap(int bootsDefense, int leggingsDefense, int chestplateDefense, int helmetDefense, int bodyDefense) {
        return ArmorMaterialsAccessor.invokeCreateDefenseMap(bootsDefense, leggingsDefense, chestplateDefense, helmetDefense, bodyDefense);
    }
}
