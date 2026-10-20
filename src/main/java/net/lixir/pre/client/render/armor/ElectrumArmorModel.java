package net.lixir.pre.client.render.armor;


import net.lixir.pre.common.ProgressionReEnvisioned;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

public class ElectrumArmorModel extends CustomArmorModel {
    public static final EntityModelLayer LAYER_LOCATION = new EntityModelLayer(ProgressionReEnvisioned.id("electrum_armor"), "main");

    @Override
    public Identifier getTexture() {
        return ProgressionReEnvisioned.id("textures/models/armor/electrum.png");
    }

    public ElectrumArmorModel(ModelPart root) {
        super(root);
    }

    public static @NotNull TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData head = modelPartData.addChild("head", ModelPartBuilder.create().uv(0, 4).cuboid(-1.0F, -9.5F, 5.0F, 2.0F, 7.0F, 4.0F, new Dilation(0.0F))
                .uv(1, 1).cuboid(-1.0F, -17.5F, -5.0F, 2.0F, 8.0F, 14.0F, new Dilation(0.0F))
                .uv(0, 30).cuboid(-4.0F, -8.5F, -4.0F, 8.0F, 8.0F, 8.0F, new Dilation(1.0F)), ModelTransform.origin(0.0F, -0.5F, 0.0F));

        ModelPartData hat = head.addChild("hat", ModelPartBuilder.create().uv(16, 47).cuboid(-4.0F, -33.0F, -4.0F, 8.0F, 8.0F, 8.0F, new Dilation(1.5F)), ModelTransform.origin(0.0F, 24.5F, 0.0F));

        ModelPartData body = modelPartData.addChild("body", ModelPartBuilder.create(), ModelTransform.origin(0.0F, -1.0F, 0.0F));

        ModelPartData chestplate = body.addChild("chestplate", ModelPartBuilder.create().uv(32, 30).cuboid(-4.0F, -24.0F, -2.0F, 8.0F, 12.0F, 4.0F, new Dilation(1.01F)), ModelTransform.origin(0.0F, 24.0F, 0.0F));

        ModelPartData belt = body.addChild("belt", ModelPartBuilder.create().uv(73, 48).cuboid(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new Dilation(0.51F)), ModelTransform.origin(0.0F, 1.0F, 0.0F));

        ModelPartData right_arm = modelPartData.addChild("right_arm", ModelPartBuilder.create().uv(34, 0).cuboid(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(1.0F)), ModelTransform.origin(-6.0F, 1.0F, 0.0F));

        ModelPartData left_arm = modelPartData.addChild("left_arm", ModelPartBuilder.create().uv(34, 0).mirrored().cuboid(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(1.0F)).mirrored(false), ModelTransform.origin(6.0F, 1.0F, 0.0F));

        ModelPartData left_leg = modelPartData.addChild("left_leg", ModelPartBuilder.create(), ModelTransform.origin(1.9F, 12.0F, 0.0F));

        ModelPartData left_boot = left_leg.addChild("left_boot", ModelPartBuilder.create().uv(0, 46).mirrored().cuboid(-0.1F, -14.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(1.0F)).mirrored(false), ModelTransform.origin(-1.9F, 14.0F, 0.0F));

        ModelPartData left_pant = left_leg.addChild("left_pant", ModelPartBuilder.create().uv(57, 48).mirrored().cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.5F)).mirrored(false), ModelTransform.origin(0.0F, 0.0F, 0.0F));

        ModelPartData right_leg = modelPartData.addChild("right_leg", ModelPartBuilder.create(), ModelTransform.origin(-1.9F, 12.0F, 0.0F));

        ModelPartData right_boot = right_leg.addChild("right_boot", ModelPartBuilder.create().uv(0, 46).cuboid(-3.9F, -14.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(1.0F)), ModelTransform.origin(1.9F, 14.0F, 0.0F));

        ModelPartData right_pant = right_leg.addChild("right_pant", ModelPartBuilder.create().uv(57, 48).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.5F)), ModelTransform.origin(0.0F, 0.0F, 0.0F));
        return TexturedModelData.of(modelData, 128, 64);
    }
}