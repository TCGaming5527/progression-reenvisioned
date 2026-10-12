package net.lixir.pre.client.mixin.network;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.authlib.GameProfile;
import net.lixir.pre.item.SpearItem;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin extends PlayerEntity {
    @Unique
    private final ClientPlayerEntity pre$self = (ClientPlayerEntity) (Object) this;

    public ClientPlayerEntityMixin(World world, BlockPos pos, float yaw, GameProfile gameProfile) {
        super(world, pos, yaw, gameProfile);
    }

    // Remove use slowdown with spear
    @WrapOperation(
            method = "applyMovementSpeedFactors",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;isUsingItem()Z")
    )
    private boolean pre$applyMovementSpeedFactors(ClientPlayerEntity instance, @NotNull Operation<Boolean> original) {
        if (original.call(instance) && instance.getWeaponStack().getItem() instanceof SpearItem) {
            return false;
        }
        return original.call(instance);
    }

    @WrapOperation(
            method = "canStartSprinting",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;isUsingItem()Z")
    )
    private boolean pre$canStartSprinting(ClientPlayerEntity instance, @NotNull Operation<Boolean> original) {
        if (original.call(instance) && instance.getWeaponStack().getItem() instanceof SpearItem) {
            return false;
        }
        return original.call(instance);
    }
}
