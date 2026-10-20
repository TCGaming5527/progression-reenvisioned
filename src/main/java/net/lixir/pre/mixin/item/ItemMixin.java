package net.lixir.pre.mixin.item;


import net.lixir.pre.common.init.PREDataComponentTypes;
import net.lixir.pre.common.duck.item.ItemDuck;
import net.lixir.pre.common.init.tag.PREBlockTags;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.server.world.ServerWorld;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public abstract class ItemMixin implements ItemDuck {

    @Inject(
            method = "inventoryTick",
            at = @At("TAIL")
    )
    private void pre$inventoryTick(
            ItemStack stack,
            ServerWorld world,
            Entity entity,
            EquipmentSlot slot,
            CallbackInfo ci
    ) {
        if (stack.getOrDefault(PREDataComponentTypes.CHARGE, 0) < stack.getOrDefault(PREDataComponentTypes.MAX_CHARGE, 0)) {
            if (stack.getOrDefault(PREDataComponentTypes.CHARGING_TICKS, 0) < stack.getOrDefault(PREDataComponentTypes.MAX_CHARGING_TICKS, 0)) {
                stack.set(
                        PREDataComponentTypes.CHARGING_TICKS,
                        stack.getOrDefault(PREDataComponentTypes.CHARGING_TICKS, 0) + 1
                );
            } else {
                stack.set(
                        PREDataComponentTypes.CHARGE,
                        stack.getOrDefault(PREDataComponentTypes.CHARGE, 0) + 1
                );
                stack.set(PREDataComponentTypes.CHARGING_TICKS, 0);
            }
        }
    }

    @Inject(
            method = "isCorrectForDrops",
            at = @At("HEAD"),
            cancellable = true
    )
    private void allowAnyPickaxe(ItemStack stack, @NotNull BlockState state, CallbackInfoReturnable<Boolean> cir) {
        if (state.isIn(PREBlockTags.MINEABLE_ANY_PICKAXE) && stack.isIn(ItemTags.PICKAXES)) {
            cir.setReturnValue(true);
        }
    }
}