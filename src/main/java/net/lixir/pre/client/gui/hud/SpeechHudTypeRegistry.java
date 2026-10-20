package net.lixir.pre.client.gui.hud;

import net.lixir.pre.common.block.SpeechHudTypes;
import net.minecraft.util.Identifier;

import java.util.concurrent.ConcurrentHashMap;

public class SpeechHudTypeRegistry {
    private static final ConcurrentHashMap<Identifier, SpeechHud> MAP = new ConcurrentHashMap<>();

    public static void initialize() {
        MAP.put(SpeechHudTypes.RECYCLER.getId(), RecyclerSpeechHud.INSTANCE);
    }

    public static boolean anyActive() {
        for (SpeechHud hud : MAP.values()) {
            if (hud.isActive()) {
                return true;
            }
        }

        return false;

    }

    public static SpeechHud getHud(Identifier id) {
        return MAP.get(id);
    }
}
