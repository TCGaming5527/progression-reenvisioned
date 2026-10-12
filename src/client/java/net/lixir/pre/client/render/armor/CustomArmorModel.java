package net.lixir.pre.client.render.armor;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.state.BipedEntityRenderState;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

public abstract class CustomArmorModel extends BipedEntityModel<BipedEntityRenderState> {

    abstract Identifier getTexture();

    protected final ModelPart head;
    protected final ModelPart hat;
    protected final ModelPart body;
    protected final ModelPart chestplate;
    protected final ModelPart belt;
    protected final ModelPart right_arm;
    protected final ModelPart left_arm;
    protected final ModelPart left_leg;
    protected final ModelPart left_pant;
    protected final ModelPart left_boot;
    protected final ModelPart right_leg;
    protected final ModelPart right_boot;
    protected final ModelPart right_pant;

    public CustomArmorModel(ModelPart root) {
        super(root);
        this.head = root.getChild("head");
        this.hat = this.head.getChild("hat");
        this.body = root.getChild("body");
        this.chestplate = this.body.getChild("chestplate");
        this.belt = this.body.getChild("belt");
        this.right_arm = root.getChild("right_arm");
        this.left_arm = root.getChild("left_arm");
        this.left_leg = root.getChild("left_leg");
        this.left_pant = this.left_leg.getChild("left_pant");
        this.left_boot = this.left_leg.getChild("left_boot");
        this.right_leg = root.getChild("right_leg");
        this.right_boot = this.right_leg.getChild("right_boot");
        this.right_pant = this.right_leg.getChild("right_pant");
    }

    public void setVisibility(@NotNull EquipmentSlot slot) {
        this.setVisible(false);

        switch (slot) {
            case HEAD -> {
                this.head.visible = true;
                this.hat.visible = true;
            }
            case CHEST -> {
                this.body.visible = true;
                this.chestplate.visible = true;
                this.right_arm.visible = true;
                this.left_arm.visible = true;
                this.belt.visible = false;
            }
            case LEGS -> {
                this.body.visible = true;
                this.belt.visible = true;
                this.chestplate.visible = false;
                this.right_arm.visible = false;
                this.left_arm.visible = false;
                this.left_leg.visible = true;
                this.right_leg.visible = true;
                this.left_pant.visible = true;
                this.right_pant.visible = true;
                this.left_boot.visible = false;
                this.right_boot.visible = false;
            }
            case FEET -> {
                this.left_leg.visible = true;
                this.right_leg.visible = true;
                this.right_boot.visible = true;
                this.left_boot.visible = true;
                this.left_pant.visible = false;
                this.right_pant.visible = false;
            }
        }
    }
}
