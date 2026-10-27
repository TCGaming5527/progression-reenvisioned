package net.lixir.pre.common.init;

import net.lixir.pre.common.ProgressionReEnvisioned;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public interface PRESoundEvents {
    SoundEvent BLOCK_RECYCLER_EMPTY = register("block.recycler.empty");
    SoundEvent BLOCK_RECYCLER_FAIL = register("block.recycler.fail");
    SoundEvent BLOCK_RECYCLER_INSERT = register("block.recycler.insert");
    SoundEvent ITEM_ENDER_EYE_TELEPORT = register("item.ender_eye.teleport");
    SoundEvent ITEM_ENDER_EYE_TELEPORT_START = register("item.ender_eye.teleport_start");
    SoundEvent ITEM_ENDER_EYE_TELEPORT_AMBIENT = register("item.ender_eye.teleport_ambient");
    SoundEvent ITEM_TRANSENDER_TELEPORT_START = register("item.transender.teleport_start");
    SoundEvent ITEM_RECOVERY_COMPASS_RESONANCE = register("item.recovery_compass.resonance");
    SoundEvent ITEM_TRANSENDER_AMBIENT = register("item.transender.teleport_ambient");
    SoundEvent ITEM_TRANSENDER_TELEPORT = register("item.transender.teleport");
    SoundEvent ITEM_TRANSENDER_CHARGED = register("item.transender.charged");
    SoundEvent ITEM_SPEAR_JAB = register("item.spear.jab");
    SoundEvent ITEM_SPEAR_USE = register("item.spear.use");
    SoundEvent ITEM_SPEAR_LUNGE = register("item.spear.lunge");
    SoundEvent MISC_TYPE = register("misc.type");

    private static SoundEvent register(String id) {
        return register(ProgressionReEnvisioned.id(id));
    }

    private static SoundEvent register(Identifier id) {
        return register(id, id);
    }

    private static SoundEvent register(Identifier id, Identifier soundId) {
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(soundId));
    }

    static void initialize() {

    }
}
