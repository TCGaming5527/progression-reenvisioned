package net.lixir.pre.client.mixin.render.entity.model;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(BipedEntityModel.class)
public interface BipedEntityModelAccessor {


    // Setters
    @Accessor("head")
    void setHead(ModelPart head);

    @Accessor("hat")
    void setHat(ModelPart hat);


    @Accessor("body")
    void setBody(ModelPart body);

    @Accessor("rightArm")
    void setRightArm(ModelPart rightArm);

    @Accessor("leftArm")
    void setLeftArm(ModelPart leftArm);

    @Accessor("rightLeg")
    void setRightLeg(ModelPart rightLeg);

    @Accessor("leftLeg")
    void setLeftLeg(ModelPart leftLeg);
}
