package net.lixir.pre.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.lixir.pre.common.item.SpearItem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin {

    @Shadow @Nullable public ClientPlayerEntity player;

    @Shadow public abstract RenderTickCounter getRenderTickCounter();

    // Prevent early attacking with spear
    @WrapOperation(
            method = "handleInputEvents",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/MinecraftClient;doAttack()Z"
            )
    )
    private boolean pre$doAttack(MinecraftClient instance, Operation<Boolean> original) {
        ItemStack stack = this.player.getWeaponStack();
        Item item = stack.getItem();
        if (item instanceof SpearItem spearItem && !spearItem.canAttack(this.player, stack, getRenderTickCounter().getTickProgress(false)))
            return false;
        return original.call(instance);
    }
}
