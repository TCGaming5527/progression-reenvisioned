package net.lixir.pre.mixin.client.render.item;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.lixir.pre.client.render.ItemTransformations;
import net.lixir.pre.common.item.SpearItem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Arm;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(HeldItemRenderer.class)
public abstract class HeldItemRendererMixin {
    @Shadow @Final private MinecraftClient client;

    @Shadow protected abstract void applyEquipOffset(MatrixStack matrices, Arm arm, float equipProgress);

    @WrapOperation(method = "renderFirstPersonItem",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/AbstractClientPlayerEntity;isUsingItem()Z",
            ordinal = 1))
    private boolean detour$renderFirstPersonItem(AbstractClientPlayerEntity instance, Operation<Boolean> original,
                                                 @Local(argsOnly = true) @NotNull ItemStack stack, @Local(ordinal = 0, argsOnly = true) float tickDelta,
                                                 @Local(ordinal = 2, argsOnly = true) float swingProgress, @Local(ordinal = 3, argsOnly = true) float equipProgress,
                                                 @Local(argsOnly = true) MatrixStack matrices, @Local Arm arm) {
        if (stack.getItem() instanceof SpearItem && instance.isUsingItem()) {
            this.applyEquipOffset(matrices, arm, equipProgress);
            ItemTransformations.applySpearTransformation(matrices, client.player, arm, stack, tickDelta, equipProgress, swingProgress);
        }
        return original.call(instance);
    }
}
