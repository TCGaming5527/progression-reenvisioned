package net.lixir.pre.client.render;

import net.lixir.pre.ProgressionReEnvisioned;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.state.BipedEntityRenderState;
import net.minecraft.util.Arm;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

public enum HandTransformations implements HandTransformation {
    EMPTY(ProgressionReEnvisioned.id("empty"), (Arm arm, ModelPart armModel, BipedEntityRenderState state) -> false),
    SPEAR(ProgressionReEnvisioned.id("spear"), (Arm arm, ModelPart armModel, BipedEntityRenderState state) -> {
        if (state.isUsingItem) {
            if (arm == Arm.RIGHT) {
                armModel.yaw = -0.2f;
                armModel.roll = 0.4f;
                armModel.pitch = -0.2f;
            }
        }
        return true;
    });

    @NotNull
    private final Identifier id;
    private final Transformation transformation;

    HandTransformations(@NotNull Identifier id, Transformation transformation) {
        this.id = id;
        this.transformation = transformation;
    }

    @Override
    public @NotNull Identifier getId() {
        return id;
    }

    @Override
    public Transformation getTransform() {
        return transformation;
    }
}
