package net.lixir.pre.mixin.client.model;

import net.lixir.pre.client.duck.model.BipedEntityRenderStateDuck;
import net.lixir.pre.client.render.HandTransformation;
import net.minecraft.client.render.entity.state.BipedEntityRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(BipedEntityRenderState.class)
public abstract class BipedEntityRenderStateMixin implements BipedEntityRenderStateDuck {
    @Unique
    private HandTransformation handTransformation;

    @Override
    public void setHandTransformation(HandTransformation handTransformation) {
        this.handTransformation = handTransformation;
    }

    @Override
    public HandTransformation getHandTransformation() {
        return handTransformation;
    }
}
