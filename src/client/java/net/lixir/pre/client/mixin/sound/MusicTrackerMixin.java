package net.lixir.pre.client.mixin.sound;

import net.lixir.pre.client.gui.hud.MusicNotifierHud;
import net.lixir.pre.item.SpearItem;
import net.minecraft.client.sound.MusicInstance;
import net.minecraft.client.sound.MusicTracker;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MusicTracker.class)
public abstract class MusicTrackerMixin {

    @Shadow
    private @Nullable SoundInstance current;

    @Inject(
            method = "play",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/sound/SoundManager;play(Lnet/minecraft/client/sound/SoundInstance;)V",
                    shift = At.Shift.AFTER
            )
    )
    private void pre$musicPlayingNotification(MusicInstance music, CallbackInfo ci) {
        MusicNotifierHud.INSTANCE.addNotifier(current);
    }
}
