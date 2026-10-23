package net.lixir.pre.client.payload;

import lombok.experimental.UtilityClass;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.lixir.pre.common.payload.*;

@UtilityClass
public class PREClientNetwork {
    public void initialize() {
        ClientPlayNetworking.registerGlobalReceiver(PlayItemUseSoundPayload.ID, PlayItemUseSoundPayload::handle);
        ClientPlayNetworking.registerGlobalReceiver(AddVelocityPayload.ID, AddVelocityPayload::handle);
        ClientPlayNetworking.registerGlobalReceiver(BlockSpeechPayload.ID, BlockSpeechPayload::handle);
        ClientPlayNetworking.registerGlobalReceiver(ItemPickupNotifierPayload.ID, ItemPickupNotifierPayload::handle);
        ClientPlayNetworking.registerGlobalReceiver(EnderEyeSpeechPayload.ID, EnderEyeSpeechPayload::handle);
    }
}
