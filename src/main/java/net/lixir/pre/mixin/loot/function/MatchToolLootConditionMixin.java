package net.lixir.pre.mixin.loot.function;

import net.lixir.pre.common.init.PREDataComponentTypes;
import net.lixir.pre.common.component.SilkTouchComponent;
import net.lixir.pre.mixin.accessor.EnchantmentsPredicateAccessor;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.condition.MatchToolLootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.predicate.component.ComponentPredicate;
import net.minecraft.predicate.component.ComponentPredicateTypes;
import net.minecraft.predicate.component.ComponentsPredicate;
import net.minecraft.predicate.item.EnchantmentPredicate;
import net.minecraft.predicate.item.EnchantmentsPredicate;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.registry.entry.RegistryEntry;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Optional;
@Mixin(MatchToolLootCondition.class)
public abstract class MatchToolLootConditionMixin {

    @Shadow @Final private Optional<ItemPredicate> predicate;

    @Inject(
            method = "test(Lnet/minecraft/loot/context/LootContext;)Z",
            at = @At("HEAD"),
            cancellable = true
    )
    private void detour$detectSilkTouchRequirement(
            LootContext lootContext,
            CallbackInfoReturnable<Boolean> cir
    ) {
        if (predicate.isEmpty()) return;

        ItemPredicate itemPredicate = predicate.get();
        ComponentsPredicate components = itemPredicate.components();
        ComponentPredicate sub =
                components.partial().get(ComponentPredicateTypes.ENCHANTMENTS);

        if (!(sub instanceof EnchantmentsPredicate enchants)) {
            return;
        }

        List<EnchantmentPredicate> list =
                ((EnchantmentsPredicateAccessor) enchants).detour$getEnchantments();

        boolean requiresSilk = list.stream().anyMatch(p -> {
            if (!p.levels().test(1)) {
                return false;
            }

            if (p.enchantments().isEmpty()) {
                return false;
            }

            for (RegistryEntry<Enchantment> ench : p.enchantments().get()) {
                if (ench.getKey().isPresent()
                        && ench.getKey().get().equals(Enchantments.SILK_TOUCH)) {
                    return true;
                }
            }

            return false;
        });

        if (!requiresSilk) return;

        ItemStack tool = lootContext.get(LootContextParameters.TOOL);

        if (tool != null && tool.getComponents().getOrDefault(PREDataComponentTypes.SILK_TOUCH, new SilkTouchComponent(false)).enabled()) {
            cir.setReturnValue(true);
            cir.cancel();
        }
    }
}


