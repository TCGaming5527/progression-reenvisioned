package net.lixir.pre.common.init;

import net.lixir.pre.common.payload.*;

import static net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry.playS2C;

public interface PRENetwork {

    static void initialize() {
        registerPayloads();
        registerHandlers();
    }

    //region Handlers
    private static void registerHandlers() {

    }
    //endregion

    //region Payloads
    private static void registerPayloads() {
        //region Clientbound
        playS2C().register(PlayItemUseSoundPayload.ID, PlayItemUseSoundPayload.CODEC);
        playS2C().register(AddVelocityPayload.ID, AddVelocityPayload.CODEC);
        playS2C().register(ItemPickupNotifierPayload.ID, ItemPickupNotifierPayload.CODEC);
        playS2C().register(EnderEyeSpeechPayload.ID, EnderEyeSpeechPayload.CODEC);
        playS2C().register(BlockSpeechPayload.ID, BlockSpeechPayload.CODEC);
        //endregion
    }
    //endregion
}
