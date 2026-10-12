package net.lixir.pre.client.network;

import lombok.experimental.UtilityClass;
import lombok.val;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.lixir.pre.client.PREClient;
import net.lixir.pre.client.gui.hud.EnderEyeSpeechHud;
import net.lixir.pre.client.gui.hud.ItemPickupNotifierHud;
import net.lixir.pre.client.gui.hud.SpeechHud;
import net.lixir.pre.client.gui.hud.SpeechHudTypeRegistry;
import net.lixir.pre.network.payload.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;

@UtilityClass
public class PREClientNetwork {
    public void initialize() {
        ClientPlayNetworking.registerGlobalReceiver(SetPositionPayload.ID, (payload, context) ->  {
            val player = context.player();
            Vec3d position = payload.position();
            val yaw = payload.yaw();
            val pitch = payload.pitch();
            player.setPosition(position);
            player.setYaw(yaw);
            player.setPitch(pitch);
        });
        ClientPlayNetworking.registerGlobalReceiver(SetVelocityPayload.ID, (payload, context) ->  {
            Vec3d velocity = payload.velocity();
            context.player().setVelocity(velocity);
        });
        ClientPlayNetworking.registerGlobalReceiver(AddVelocityPayload.ID, (payload, context) ->  {
            Vec3d velocity = payload.velocity();
            context.player().addVelocity(velocity);
        });
        ClientPlayNetworking.registerGlobalReceiver(BlockSpeechPayload.ID, (payload, context) ->  {

            Identifier identifier = payload.speechHudType().getId();
            SpeechHud speechHud = SpeechHudTypeRegistry.getHud(identifier);
            if (PREClient.CURRENT_SPEECH != null)
                PREClient.CURRENT_SPEECH.reset();

            if (speechHud != null)
                speechHud.activate();
        });
        ClientPlayNetworking.registerGlobalReceiver(
                ItemPickupNotifierPayload.ID,
                ItemPickupNotifierHud.INSTANCE::onPayloadReceived
        );
        ClientPlayNetworking.registerGlobalReceiver(
                EnderEyeSpeechPayload.ID,
                EnderEyeSpeechHud.INSTANCE::onPayloadReceived
        );
    }
}
