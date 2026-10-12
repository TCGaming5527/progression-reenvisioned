package net.lixir.pre.network;

import net.lixir.pre.network.payload.*;

import static net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry.playS2C;

public final class PRENetwork {
    private PRENetwork() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static void initialize() {
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
        playS2C().register(AddVelocityPayload.ID, AddVelocityPayload.CODEC);
        playS2C().register(SetVelocityPayload.ID, SetVelocityPayload.CODEC);
        playS2C().register(ItemPickupNotifierPayload.ID, ItemPickupNotifierPayload.CODEC);
        playS2C().register(SetPositionPayload.ID, SetPositionPayload.CODEC);
        playS2C().register(EnderEyeSpeechPayload.ID, EnderEyeSpeechPayload.CODEC);
        playS2C().register(BlockSpeechPayload.ID, BlockSpeechPayload.CODEC);
        //endregion
    }
    //endregion
}
