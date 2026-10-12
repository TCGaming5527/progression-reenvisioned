package net.lixir.pre.block;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.lixir.pre.network.payload.BlockSpeechPayload;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

public interface SpeechBlock {
    default boolean canSpeak(@NotNull PlayerEntity player, World world) {
        return !world.isClient && player.isSneaking();
    }

    default void speak(PlayerEntity player, BlockState state) {
        if (player instanceof ServerPlayerEntity serverPlayer)
            ServerPlayNetworking.send(serverPlayer, new BlockSpeechPayload(getSpeechHudType()));
    }

    default boolean trySpeak(PlayerEntity player, World world, BlockState state) {
        if (canSpeak(player, world)) {
            speak(player, state);
            player.swingHand(Hand.MAIN_HAND, true);
            return true;
        }
        return false;
    }

    SpeechHudType getSpeechHudType();
}
