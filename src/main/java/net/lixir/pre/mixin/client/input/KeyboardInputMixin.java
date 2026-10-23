package net.lixir.pre.mixin.client.input;

import net.lixir.pre.common.init.PREEntityComponents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.input.Input;
import net.minecraft.client.input.KeyboardInput;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.PlayerInput;
import net.minecraft.util.math.Vec2f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(KeyboardInput.class)
public abstract class KeyboardInputMixin extends Input {

    @Inject(
            method = "tick",
            at = @At(
                    value = "HEAD"
            ),
            cancellable = true)
    private void pre$tick(CallbackInfo ci) {
        MinecraftClient client = MinecraftClient.getInstance();
        ClientPlayerEntity player = client.player;
        if (player == null)
            return;

        var rewindData = PREEntityComponents.REWIND.get(player);
        var isRewinding = rewindData.isRewinding();
        if (isRewinding) {
            this.playerInput = PlayerInput.DEFAULT;
            this.movementVector = Vec2f.ZERO;
            ci.cancel();
        }
    }
}
