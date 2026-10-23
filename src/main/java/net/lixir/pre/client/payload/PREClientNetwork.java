package net.lixir.pre.client.payload;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.lixir.pre.common.payload.*;

public interface PREClientNetwork {
    static void initialize() {
        ClientPlayNetworking.registerGlobalReceiver(PlayItemUseSoundPayload.ID, PlayItemUseSoundPayload::handle);
        ClientPlayNetworking.registerGlobalReceiver(AddVelocityPayload.ID, AddVelocityPayload::handle);
        ClientPlayNetworking.registerGlobalReceiver(BlockSpeechPayload.ID, BlockSpeechPayload::handle);
        ClientPlayNetworking.registerGlobalReceiver(ItemPickupNotifierPayload.ID, ItemPickupNotifierPayload::handle);
        ClientPlayNetworking.registerGlobalReceiver(EnderEyeSpeechPayload.ID, EnderEyeSpeechPayload::handle);
    }
}
