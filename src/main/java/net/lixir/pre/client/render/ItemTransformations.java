package net.lixir.pre.client.render;

import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Arm;
import net.minecraft.util.math.RotationAxis;

public final class ItemTransformations {
    private ItemTransformations() {

    }
    public static void applySpearTransformation(MatrixStack matrices, AbstractClientPlayerEntity player, Arm arm,
                                                ItemStack stack, float tickDelta, float equipProgress, float swingProgress) {
        int side = arm == Arm.RIGHT ? 1 : -1;
        matrices.translate(-0.9f, 1.25f, -0.2f);
        matrices.multiply(RotationAxis.NEGATIVE_X.rotationDegrees(side * 75));
    }
}
