package net.lixir.pre.mixin.item;

import net.minecraft.item.ToolMaterial;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ToolMaterial.class)
public abstract class ToolMaterialMixin {
    @Final
    @Mutable
    @Shadow
    public static ToolMaterial WOOD;

    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void init(CallbackInfo ci) {
        WOOD = new ToolMaterial(
                ToolMaterial.STONE.incorrectBlocksForDrops(),
                ToolMaterial.STONE.durability(),
                ToolMaterial.STONE.speed(),
                ToolMaterial.WOOD.attackDamageBonus(),
                ToolMaterial.STONE.enchantmentValue(),
                ToolMaterial.WOOD.repairItems()
        );
    }
}

