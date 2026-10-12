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
public abstract class GoldToolMaterialMixin {
    @Final
    @Mutable
    @Shadow
    public static ToolMaterial GOLD;

    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void init(CallbackInfo ci) {
        GOLD = new ToolMaterial(
                ToolMaterial.IRON.incorrectBlocksForDrops(),
                ToolMaterial.STONE.durability(),
                ToolMaterial.GOLD.speed(),
                ToolMaterial.GOLD.attackDamageBonus(),
                ToolMaterial.GOLD.enchantmentValue(),
                ToolMaterial.GOLD.repairItems()
        );
    }
}

