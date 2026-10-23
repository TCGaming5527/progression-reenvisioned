package net.lixir.pre.mixin.screen;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.screen.EnchantmentScreenHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EnchantmentScreenHandler.class)
public class EnchantmentScreenHandlerMixin {

    @Definition(id = "enchantmentPower", field = "Lnet/minecraft/screen/EnchantmentScreenHandler;enchantmentPower:[I")
    @Expression("this.enchantmentPower[?] = @(?)")
    @ModifyExpressionValue(method = "method_17411", at = @At("MIXINEXTRAS:EXPRESSION"))
    private int pre$cheaperEnchanting(int original) {
        return Math.clamp((int) (original / 1.5), 1, 20);
    }

}
