package net.lixir.pre.common.init;

import net.lixir.pre.common.ProgressionReEnvisioned;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public final class PRESoundEvents {
    public static final SoundEvent BLOCK_RECYCLER_EMPTY = register("block.recycler.empty");
    public static final SoundEvent BLOCK_RECYCLER_FAIL = register("block.recycler.fail");
    public static final SoundEvent BLOCK_RECYCLER_INSERT = register("block.recycler.insert");
    public static final SoundEvent ITEM_ENDER_EYE_TELEPORT = register("item.ender_eye.teleport");
    public static final SoundEvent ITEM_ENDER_EYE_TELEPORT_START = register("item.ender_eye.teleport_start");
    public static final SoundEvent ITEM_ENDER_EYE_TELEPORT_AMBIENT = register("item.ender_eye.teleport_ambient");
    public static final SoundEvent ITEM_TRANSENDER_TELEPORT_START = register("item.transender.teleport_start");
    public static final SoundEvent ITEM_RECOVERY_COMPASS_RESONANCE = register("item.recovery_compass.resonance");
    public static final SoundEvent ITEM_TRANSENDER_AMBIENT = register("item.transender.teleport_ambient");
    public static final SoundEvent ITEM_TRANSENDER_TELEPORT = register("item.transender.teleport");
    public static final SoundEvent ITEM_TRANSENDER_CHARGED = register("item.transender.charged");
    public static final SoundEvent ITEM_SPEAR_JAB = register("item.spear.jab");
    public static final SoundEvent ITEM_SPEAR_USE = register("item.spear.use");
    public static final SoundEvent ITEM_SPEAR_LUNGE = register("item.spear.lunge");
    public static final SoundEvent MISC_TYPE = register("misc.type");

    private PRESoundEvents() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    private static SoundEvent register(String id) {
        return register(ProgressionReEnvisioned.id(id));
    }

    private static SoundEvent register(Identifier id) {
        return register(id, id);
    }

    private static SoundEvent register(Identifier id, Identifier soundId) {
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(soundId));
    }

    public static void initialize() {

    }
}
