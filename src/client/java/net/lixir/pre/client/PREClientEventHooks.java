package net.lixir.pre.client;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudLayerRegistrationCallback;
import net.fabricmc.fabric.api.client.rendering.v1.IdentifiedLayer;
import net.lixir.pre.client.gui.hud.*;

public final class PREClientEventHooks {
    private PREClientEventHooks() {

    }

    public static void register() {
        HudLayerRegistrationCallback.EVENT.register(layeredDrawer -> {
                layeredDrawer.attachLayerBefore(IdentifiedLayer.CROSSHAIR, MusicNotifierHud.LAYER, MusicNotifierHud.INSTANCE::onHudRender);
                layeredDrawer.attachLayerBefore(IdentifiedLayer.CROSSHAIR, ItemPickupNotifierHud.LAYER, ItemPickupNotifierHud.INSTANCE::onHudRender);
                layeredDrawer.attachLayerBefore(IdentifiedLayer.CROSSHAIR, EnderEyeSpeechHud.LAYER, EnderEyeSpeechHud.INSTANCE::onHudRender);
                layeredDrawer.attachLayerBefore(IdentifiedLayer.CROSSHAIR, RecyclerSpeechHud.LAYER, RecyclerSpeechHud.INSTANCE::onHudRender);
                layeredDrawer.attachLayerBefore(IdentifiedLayer.CROSSHAIR, CompassHud.LAYER, CompassHud.INSTANCE::onHudRender);
        });

        ClientTickEvents.END_CLIENT_TICK.register(minecraftClient -> {
            if (PREClient.CURRENT_SPEECH != null) {
                PREClient.CURRENT_SPEECH.tick(minecraftClient);
            }
            MusicNotifierHud.INSTANCE.tick(minecraftClient);
            ItemPickupNotifierHud.INSTANCE.tick(minecraftClient);
        });
    }
}
