package net.lixir.pre.client.render;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.state.BipedEntityRenderState;
import net.minecraft.util.Arm;
import net.minecraft.util.Identifier;

public interface HandTransformation {
    Identifier getId();

    Transformation getTransform();

    default boolean isEmpty() {
        return this.equals(HandTransformations.EMPTY);
    }

    @FunctionalInterface
    interface Transformation {
        boolean transform(Arm arm, ModelPart armModel, BipedEntityRenderState state);
    }
}
