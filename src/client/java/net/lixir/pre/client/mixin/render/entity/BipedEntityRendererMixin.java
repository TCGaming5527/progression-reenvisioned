package net.lixir.pre.client.mixin.render.entity;

import net.lixir.pre.client.duck.model.BipedEntityRenderStateDuck;
import net.lixir.pre.client.render.HandTransformation;
import net.lixir.pre.client.render.HandTransformationRegistry;
import net.lixir.pre.client.render.HandTransformations;
import net.lixir.pre.item.HandTransformationType;
import net.minecraft.client.item.ItemModelManager;
import net.minecraft.client.render.entity.BipedEntityRenderer;
import net.minecraft.client.render.entity.state.BipedEntityRenderState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BipedEntityRenderer.class)
public abstract class BipedEntityRendererMixin {

    @Inject(
            method = "updateBipedRenderState",
            at = @At(
                    value = "TAIL"
            )
    )
    private static void pre$update(LivingEntity entity, BipedEntityRenderState state, float tickProgress, ItemModelManager itemModelResolver, CallbackInfo ci) {
        BipedEntityRenderStateDuck duck = (BipedEntityRenderStateDuck) state;
        duck.setHandTransformation(getHandTransformation(entity.getWeaponStack()));
    }

    @Unique
    private static HandTransformation getHandTransformation(ItemStack stack) {
        if (!stack.isEmpty()) {
            Item item = stack.getItem();
            HandTransformationType handTransformationType = item.getHandTransformationType();
            if (handTransformationType != null)
                return HandTransformationRegistry.getTransformation(handTransformationType.getId());
        }
        return HandTransformations.EMPTY;
    }
}
