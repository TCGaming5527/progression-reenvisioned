package net.lixir.pre.mixin.loot.function;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.lixir.pre.common.component.FortuneComponent;
import net.lixir.pre.common.init.PREDataComponentTypes;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.function.ApplyBonusLootFunction;
import net.minecraft.registry.entry.RegistryEntry;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ApplyBonusLootFunction.class)
public abstract class ApplyBonusLootFunctionMixin {

    @WrapOperation(
            method = "process",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/enchantment/EnchantmentHelper;getLevel(Lnet/minecraft/registry/entry/RegistryEntry;Lnet/minecraft/item/ItemStack;)I"
            )
    )
    private int detour$fortuneComponent(
            @NotNull RegistryEntry<Enchantment> enchantment,
            ItemStack stack,
            Operation<Integer> original
    ) {
        var key = enchantment.getKey();
        if (key.isPresent() && key.get() == Enchantments.FORTUNE) {
            FortuneComponent fortuneBonus = stack.getOrDefault(PREDataComponentTypes.BONUS_FORTUNE, new FortuneComponent(0));
            if (fortuneBonus.value() != 0)
                return original.call(enchantment, stack) + fortuneBonus.value();
        }
        return original.call(enchantment, stack);
    }
}
