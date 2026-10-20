package net.lixir.pre.common.init;

import com.mojang.serialization.Codec;
import net.lixir.pre.common.ProgressionReEnvisioned;
import net.lixir.pre.common.component.FortuneComponent;
import net.lixir.pre.common.component.SilkTouchComponent;
import net.minecraft.component.ComponentType;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.dynamic.Codecs;
import org.jetbrains.annotations.NotNull;

import java.util.function.UnaryOperator;

public final class PREDataComponentTypes {
    private PREDataComponentTypes() {

    }

    public static final ComponentType<FortuneComponent> BONUS_FORTUNE = register(
            "bonus_fortune", builder -> builder.codec(FortuneComponent.CODEC).packetCodec(FortuneComponent.PACKET_CODEC)
    );
    public static final ComponentType<SilkTouchComponent> SILK_TOUCH = register(
            "silk_touch", builder -> builder.codec(SilkTouchComponent.CODEC).packetCodec(SilkTouchComponent.PACKET_CODEC)
    );
    public static final ComponentType<Boolean> RECOVERS_INVENTORY = register(
            "recovers_inventory", builder -> builder.codec(Codec.BOOL).packetCodec(PacketCodecs.BOOLEAN)
    );
    public static final ComponentType<Integer> CHARGE = register(
            "charge", builder -> builder.codec(Codecs.NON_NEGATIVE_INT).packetCodec(PacketCodecs.VAR_INT)
    );
    public static final ComponentType<Integer> MAX_CHARGE = register(
            "max_charge", builder -> builder.codec(Codecs.NON_NEGATIVE_INT).packetCodec(PacketCodecs.VAR_INT)
    );
    public static final ComponentType<Integer> MAX_CHARGING_TICKS = register(
            "max_charging_ticks", builder -> builder.codec(Codecs.NON_NEGATIVE_INT).packetCodec(PacketCodecs.VAR_INT)
    );
    public static final ComponentType<Integer> CHARGING_TICKS = register(
            "charging_ticks", builder -> builder.codec(Codecs.NON_NEGATIVE_INT).packetCodec(PacketCodecs.VAR_INT)
    );
    public static final ComponentType<Boolean> PERSISTS_PLAYER_DEATH = register(
            "persists_player_death", builder -> builder.codec(Codec.BOOL).packetCodec(PacketCodecs.BOOLEAN)
    );

    @SuppressWarnings("unchecked")
    private static <T> ComponentType<T> register(String id, @NotNull UnaryOperator<ComponentType.Builder<T>> builderOperator) {
        return Registry.register(Registries.DATA_COMPONENT_TYPE, ProgressionReEnvisioned.id(id), ((ComponentType.Builder)builderOperator.apply(ComponentType.builder())).build());
    }

    public static void initialize() {

    }
}
