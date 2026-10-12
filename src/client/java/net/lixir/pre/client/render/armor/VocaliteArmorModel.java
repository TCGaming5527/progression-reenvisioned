package net.lixir.pre.client.render.armor;


import net.lixir.pre.ProgressionReEnvisioned;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

public class VocaliteArmorModel extends CustomArmorModel {
    public static final EntityModelLayer LAYER_LOCATION = new EntityModelLayer(ProgressionReEnvisioned.id("vocalite_armor"), "main");

    @Override
    public Identifier getTexture() {
        return ProgressionReEnvisioned.id("textures/models/armor/vocalite.png");
    }

    public VocaliteArmorModel(ModelPart root) {
        super(root);
    }
    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData head = modelPartData.addChild("head", ModelPartBuilder.create().uv(0, 46).cuboid(-4.0F, -8.5F, -4.0F, 8.0F, 8.0F, 8.0F, new Dilation(1.0F)), ModelTransform.origin(0.0F, -0.5F, 0.0F));

        ModelPartData hat = head.addChild("hat", ModelPartBuilder.create().uv(32, 46).cuboid(-4.0F, -33.0F, -4.0F, 8.0F, 8.0F, 8.0F, new Dilation(1.5F)), ModelTransform.origin(0.0F, 24.5F, 0.0F));

        ModelPartData body = modelPartData.addChild("body", ModelPartBuilder.create(), ModelTransform.origin(0.0F, -1.0F, 0.0F));

        ModelPartData chestplate = body.addChild("chestplate", ModelPartBuilder.create().uv(50, 0).cuboid(-4.0F, -24.0F, -2.0F, 8.0F, 12.0F, 4.0F, new Dilation(1.01F)), ModelTransform.origin(0.0F, 24.0F, 0.0F));

        ModelPartData chest_r1 = chestplate.addChild("chest_r1", ModelPartBuilder.create().uv(50, 32).cuboid(-4.0F, -2.0F, -3.0F, 8.0F, 8.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -22.0F, -3.1F, 0.5934F, 0.0F, 0.0F));

        ModelPartData wing_left_r1 = chestplate.addChild("wing_left_r1", ModelPartBuilder.create().uv(0, 0).cuboid(0.0F, -18.0F, 0.0F, 25.0F, 23.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -16.0F, 3.25F, -0.4013F, -0.2013F, 0.0846F));

        ModelPartData wing_right_r1 = chestplate.addChild("wing_right_r1", ModelPartBuilder.create().uv(0, 23).cuboid(-25.0F, -18.0F, 0.0F, 25.0F, 23.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -16.0F, 3.25F, -0.4013F, 0.2013F, -0.0846F));

        ModelPartData belt = body.addChild("belt", ModelPartBuilder.create().uv(50, 16).cuboid(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new Dilation(0.51F)), ModelTransform.origin(0.0F, 1.0F, 0.0F));

        ModelPartData right_arm = modelPartData.addChild("right_arm", ModelPartBuilder.create().uv(0, 62).cuboid(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(1.0F)), ModelTransform.origin(-6.0F, 1.0F, 0.0F));

        ModelPartData left_arm = modelPartData.addChild("left_arm", ModelPartBuilder.create().uv(0, 62).mirrored().cuboid(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(1.0F)).mirrored(false), ModelTransform.origin(6.0F, 1.0F, 0.0F));

        ModelPartData left_leg = modelPartData.addChild("left_leg", ModelPartBuilder.create(), ModelTransform.origin(1.9F, 12.0F, 0.0F));

        ModelPartData left_boot = left_leg.addChild("left_boot", ModelPartBuilder.create().uv(16, 62).mirrored().cuboid(-0.1F, -14.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(1.0F)).mirrored(false), ModelTransform.origin(-1.9F, 14.0F, 0.0F));

        ModelPartData left_pant = left_leg.addChild("left_pant", ModelPartBuilder.create().uv(32, 62).mirrored().cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.5F)).mirrored(false), ModelTransform.origin(0.0F, 0.0F, 0.0F));

        ModelPartData skirt_left_r1 = left_pant.addChild("skirt_left_r1", ModelPartBuilder.create().uv(48, 62).cuboid(-1.0F, -2.0F, -3.0F, 1.0F, 8.0F, 5.0F, new Dilation(0.0F)), ModelTransform.of(3.1F, -1.0F, 0.5F, 0.0F, 0.0F, -0.3054F));

        ModelPartData right_leg = modelPartData.addChild("right_leg", ModelPartBuilder.create(), ModelTransform.origin(-1.9F, 12.0F, 0.0F));

        ModelPartData right_boot = right_leg.addChild("right_boot", ModelPartBuilder.create().uv(16, 62).cuboid(-3.9F, -14.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(1.0F)), ModelTransform.origin(1.9F, 14.0F, 0.0F));

        ModelPartData right_pant = right_leg.addChild("right_pant", ModelPartBuilder.create().uv(32, 62).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.5F)), ModelTransform.origin(0.0F, 0.0F, 0.0F));

        ModelPartData skirt_right_r1 = right_pant.addChild("skirt_right_r1", ModelPartBuilder.create().uv(48, 62).cuboid(-1.0F, -2.0F, -3.0F, 1.0F, 8.0F, 5.0F, new Dilation(0.0F)), ModelTransform.of(-2.1F, -1.0F, 0.5F, 0.0F, 0.0F, 0.3054F));
        return TexturedModelData.of(modelData, 128, 128);
    }
}