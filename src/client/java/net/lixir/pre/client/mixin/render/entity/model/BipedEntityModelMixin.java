package net.lixir.pre.client.mixin.render.entity.model;

import net.lixir.pre.client.duck.model.BipedEntityRenderStateDuck;
import net.lixir.pre.client.render.HandTransformation;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.ModelWithArms;
import net.minecraft.client.render.entity.model.ModelWithHead;
import net.minecraft.client.render.entity.state.BipedEntityRenderState;
import net.minecraft.util.Arm;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BipedEntityModel.class)
public abstract class BipedEntityModelMixin<T extends BipedEntityRenderState> extends EntityModel<T> implements ModelWithArms, ModelWithHead {

    @Shadow
    @Mutable @Final
    public ModelPart head;
    @Shadow @Mutable
    @Final public ModelPart hat;
    @Shadow @Mutable @Final public ModelPart body;
    @Shadow @Mutable @Final public ModelPart rightArm;
    @Shadow @Mutable @Final public ModelPart leftArm;
    @Shadow @Mutable @Final public ModelPart rightLeg;
    @Shadow @Mutable @Final public ModelPart leftLeg;

    protected BipedEntityModelMixin(ModelPart root) {
        super(root);
    }

    @Inject(
            method = "positionRightArm",
            at = @At(
                    value = "HEAD"
            ),
            cancellable = true)
    private void detour$rightArm(T state, BipedEntityModel.ArmPose armPose, CallbackInfo ci) {
        BipedEntityRenderStateDuck duck = (BipedEntityRenderStateDuck) state;
        HandTransformation handTransformation = duck.getHandTransformation();
        if (!handTransformation.isEmpty()) {
            if (handTransformation.getTransform().transform(Arm.RIGHT, rightArm, state)) {
                ci.cancel();
            }
        }
    }

    @Inject(
            method = "positionLeftArm",
            at = @At(
                    value = "HEAD"
            ),
            cancellable = true)
    private void pre$update(T state, BipedEntityModel.ArmPose armPose, CallbackInfo ci) {
        BipedEntityRenderStateDuck duck = (BipedEntityRenderStateDuck) state;
        HandTransformation handTransformation = duck.getHandTransformation();
        if (!handTransformation.isEmpty()) {
            if (handTransformation.getTransform().transform(Arm.LEFT, leftArm, state)) {
                ci.cancel();
            }
        }
    }
}
