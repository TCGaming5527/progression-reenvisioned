package net.lixir.pre.mixin.entity.player;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.lixir.pre.component.PREDataComponentTypes;
import net.lixir.pre.sound.PRESoundEvents;
import net.minecraft.entity.EntityEquipment;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.collection.DefaultedList;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;

@Mixin(PlayerInventory.class)
public abstract class PlayerInventoryMixin {

    @Shadow @Final private DefaultedList<ItemStack> main;
    @Shadow @Final public PlayerEntity player;

    @Shadow @Final private EntityEquipment equipment;
    @Unique
    private static final int ITEMS_PER_ECHO_SHARD = 100;

    @Unique
    private int remainingQuota;
    @Unique
    private int availableQuota;
    @Unique
    private int recoveredEquipment;
    @Unique
    private boolean hasRecovery;
    @Unique
    private final HashMap<Integer, Integer> recoveredSlots = new HashMap<>();

    @Inject(method = "dropAll", at = @At("HEAD"))
    private void pre$calculateRecoveryQuota(CallbackInfo ci) {
        recoveredSlots.clear();
        availableQuota = 0;
        remainingQuota = 0;

        hasRecovery = main.stream()
                .anyMatch(s -> s.getOrDefault(PREDataComponentTypes.RECOVERS_INVENTORY, false));
        if (!hasRecovery)
            return;

        for (ItemStack stack : main) {
            if (stack.isOf(Items.ECHO_SHARD)) {
                availableQuota += stack.getCount() * ITEMS_PER_ECHO_SHARD;
            }
        }

        recoveredEquipment = 0;
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            ItemStack equipment = player.getEquippedStack(slot);
            if (!equipment.isEmpty())
                recoveredEquipment += equipment.getCount();
        }
        remainingQuota = availableQuota;
        remainingQuota -= recoveredEquipment;
        if (remainingQuota < 0) {
            recoveredEquipment -= remainingQuota;
            remainingQuota = 0;
            if (recoveredEquipment < 0)
                recoveredEquipment = 0;
        }
    }

    @Inject(method = "dropAll", at = @At("TAIL"))
    private void pre$consumeEchoShards(CallbackInfo ci) {
        int usedQuota = availableQuota - remainingQuota;
        if (usedQuota <= 0)
            return;
        player.getWorld().playSound(
                null,
                player.getX(),
                player.getY(),
                player.getZ(),
                PRESoundEvents.ITEM_RECOVERY_COMPASS_RESONANCE,
                SoundCategory.PLAYERS
        );

        int shardsToConsume = (usedQuota + ITEMS_PER_ECHO_SHARD - 1) / ITEMS_PER_ECHO_SHARD;

        for (int i = 0; i < main.size() && shardsToConsume > 0; i++) {
            ItemStack stack = main.get(i);
            if (!stack.isOf(Items.ECHO_SHARD))
                continue;

            int consume = Math.min(stack.getCount(), shardsToConsume);
            stack.decrement(consume);
            shardsToConsume -= consume;
        }
    }

    @WrapOperation(
            method = "dropAll",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/PlayerEntity;dropItem(Lnet/minecraft/item/ItemStack;ZZ)Lnet/minecraft/entity/ItemEntity;"
            )
    )
    private ItemEntity pre$splitDroppedStack(
            PlayerEntity player,
            ItemStack stack,
            boolean throwRandomly,
            boolean retainOwnership,
            Operation<ItemEntity> original,
            @Local int i
    ) {
        if (stack.isOf(Items.ECHO_SHARD) && hasRecovery)
            return null;
        // Hard exclusions
        if (remainingQuota <= 0 || stack.getOrDefault(PREDataComponentTypes.PERSISTS_PLAYER_DEATH, false)) {
            return original.call(player, stack, throwRandomly, retainOwnership);
        }

        int count = stack.getCount();
        // Fully saved
        if (count <= remainingQuota) {
            remainingQuota -= count;
            recoveredSlots.put(i, count);
            return null; // nothing drops
        }

        // Partial save
        ItemStack dropped = stack.copy();
        dropped.setCount(count - remainingQuota);
        recoveredSlots.put(i, remainingQuota);
        remainingQuota = 0;

        return original.call(player, dropped, throwRandomly, retainOwnership);
    }

    @WrapOperation(
            method = "dropAll",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/util/collection/DefaultedList;set(ILjava/lang/Object;)Ljava/lang/Object;"
            )
    )
    private Object pre$keepSavedStacks(
            DefaultedList<?> list,
            int index,
            Object newValue,
            Operation<Object> original
    ) {
        ItemStack stack = (ItemStack) list.get(index);
        boolean saved = recoveredSlots.containsKey(index);
        if (stack.isOf(Items.ECHO_SHARD) && hasRecovery)
            return stack;
        if (!stack.isEmpty() && saved) {
            stack.setCount(recoveredSlots.get(index));
            return stack;
        } else {
            return original.call(list, index, newValue);
        }
    }
    @WrapOperation(
            method = "dropAll",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/EntityEquipment;dropAll(Lnet/minecraft/entity/LivingEntity;)V"
            )
    )
    private void pre$keepEquipment(EntityEquipment instance, LivingEntity entity, Operation<Void> original) {
        if (recoveredEquipment > 0) {
            int i = 0;
            for (ItemStack stack : instance.map.values()) {
                int count = stack.getCount();

                if (recoveredEquipment >= count) {
                    recoveredEquipment -= count;
                } else {
                    ItemStack drop = stack.copy();
                    drop.setCount(count - recoveredEquipment);
                    instance.put(EquipmentSlot.values()[i], ItemStack.EMPTY);
                    recoveredEquipment = 0;
                    entity.dropItem(drop, true, false);
                }
                i++;
            }

        } else {
            original.call(instance, entity);
        }
    }

    @WrapOperation(
            method = "dropAll",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/item/ItemStack;isEmpty()Z"
            )
    )
    private boolean pre$keepSavedStacks(
            ItemStack instance, Operation<Boolean> original
    ) {
        if (instance.getOrDefault(PREDataComponentTypes.PERSISTS_PLAYER_DEATH, false) || (instance.isOf(Items.ECHO_SHARD) && hasRecovery))
            return true;
        return original.call(instance);
    }
}
