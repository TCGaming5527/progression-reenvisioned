package net.lixir.pre.mixin.client.gui.hud;

import lombok.val;
import net.lixir.pre.common.init.PREEntityComponents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin {

    @Shadow
    @Final
    private MinecraftClient client;

    @ModifyArg(method = "renderVignetteOverlay",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawTexture(Ljava/util/function/Function;Lnet/minecraft/util/Identifier;IIFFIIIII)V"),
            index = 10
    )
    private int detour$colorVignette(int x) {
        ClientPlayerEntity player = client.player;
        if (player == null)
            return x;

        val rewindData = PREEntityComponents.REWIND.get(player);
        val isRewinding = rewindData.isRewinding();
        if (isRewinding) {
            return 0xff0066ff;
        }
        return x;
    }
}



