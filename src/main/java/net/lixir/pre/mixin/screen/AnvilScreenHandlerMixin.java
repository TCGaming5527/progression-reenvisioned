package net.lixir.pre.mixin.screen;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.screen.AnvilScreenHandler;
import net.minecraft.screen.Property;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(AnvilScreenHandler.class)
public class AnvilScreenHandlerMixin {

    @Shadow
    @Final
    private Property levelCost;

    @Definition(id = "levelCost", field = "Lnet/minecraft/screen/AnvilScreenHandler;levelCost:Lnet/minecraft/screen/Property;")
    @Definition(id = "get", method = "Lnet/minecraft/screen/Property;get()I")
    @Expression("this.levelCost.get() >= 40")
    @ModifyExpressionValue(method = "updateResult", at = @At("MIXINEXTRAS:EXPRESSION"))
    private boolean pre$noLevelLimit(boolean original) {
        if (this.levelCost.get() > 40) {
            this.levelCost.set(40);
        }
        return false;
    }
}
