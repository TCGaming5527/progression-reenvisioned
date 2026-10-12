package net.lixir.pre.mixin.screen.slot;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.lixir.pre.registry.tag.PREItemTags;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Slot.class)
public abstract class SlotMixin {
    @WrapOperation(method = "setStackNoCallbacks", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/inventory/Inventory;setStack(ILnet/minecraft/item/ItemStack;)V"))
    private void banSetStacks(Inventory instance, int i, @NotNull ItemStack stack, Operation<Void> original) {
        if (!stack.isIn(PREItemTags.BANNED))
            original.call(instance, i, stack);
    }
}
