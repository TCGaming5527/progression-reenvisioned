package net.lixir.pre.client.render.armor;

import net.minecraft.client.model.ModelPart;

@FunctionalInterface
public interface ArmorModelProvider {
    CustomArmorModel create(ModelPart root);
}
