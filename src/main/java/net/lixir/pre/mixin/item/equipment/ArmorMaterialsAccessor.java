package net.lixir.pre.mixin.item.equipment;

import net.minecraft.item.equipment.ArmorMaterials;
import net.minecraft.item.equipment.EquipmentType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.Map;

@Mixin(ArmorMaterials.class)
public interface ArmorMaterialsAccessor {

    @Invoker("createDefenseMap")
    static Map<EquipmentType, Integer> invokeCreateDefenseMap(
            int bootsDefense, int leggingsDefense, int chestplateDefense, int helmetDefense, int bodyDefense
    ) {
        throw new AssertionError();
    }
}
