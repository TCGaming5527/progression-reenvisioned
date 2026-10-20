package net.lixir.pre.mixin.client.input;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.lixir.pre.client.gui.hud.BlockSpeechHud;
import net.lixir.pre.client.gui.hud.EnderEyeSpeechHud;
import net.lixir.pre.client.gui.hud.SpeechHudTypeRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.KeyBinding;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {

    @Shadow
    @Final
    public GameOptions options;

    @WrapOperation(method = "handleInputEvents", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/option/KeyBinding;wasPressed()Z"))
    private boolean pre$disableInvKey(KeyBinding key, Operation<Boolean> original) {
        if (key == this.options.inventoryKey) {
            if (EnderEyeSpeechHud.INSTANCE.isActive() || SpeechHudTypeRegistry.anyActive()) {
                return false;
            }
        }
        return original.call(key);
    }
}
