package net.lixir.pre.mixin.entity;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.lixir.pre.common.payload.ItemPickupNotifierPayload;
import net.lixir.pre.common.init.tag.PREItemTags;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin  {

    @Inject(
            method = "onPlayerCollision",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/PlayerEntity;sendPickup(Lnet/minecraft/entity/Entity;I)V",
                    shift = At.Shift.AFTER
            )
    )
    private void pre$playerCollision(
            PlayerEntity player,
            CallbackInfo ci,
            @Local Item item,
            @Local int count
    ) {
        ServerPlayNetworking.send((ServerPlayerEntity) player, new ItemPickupNotifierPayload(item, count));
    }

    @WrapOperation(
            method = "setStack",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/data/DataTracker;set(Lnet/minecraft/entity/data/TrackedData;Ljava/lang/Object;)V"
            )
    )
    private <T> void pre$replaceItem(DataTracker instance, TrackedData<T> key, T value, Operation<Void> original) {
        if (value instanceof ItemStack stack && !stack.isIn(PREItemTags.BANNED)) {
            original.call(instance, key, value);
        }
    }
}
