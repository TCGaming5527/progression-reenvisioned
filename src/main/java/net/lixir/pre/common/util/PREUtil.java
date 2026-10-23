package net.lixir.pre.common.util;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.function.ValueLists;
import net.minecraft.util.math.Vec3d;

import java.util.function.Consumer;
import java.util.function.IntFunction;

public interface PREUtil {
    IntFunction<SoundCategory> SOUND_CATEGORY_INDEX_TO_VALUE_FUNCTION = ValueLists.createIndexToValueFunction(
            SoundCategory::ordinal, SoundCategory.values(), ValueLists.OutOfBoundsHandling.WRAP
    );
    PacketCodec<ByteBuf, SoundCategory> SOUND_CATEGORY_PACKET_CODEC = PacketCodecs.indexed(SOUND_CATEGORY_INDEX_TO_VALUE_FUNCTION, SoundCategory::ordinal);

    static void forNearby(Vec3d origin, ServerWorld world, Consumer<ServerPlayerEntity> consumer) {
        forNearby(origin, 64, world, consumer);
    }

    static void forNearby(Vec3d origin, int range, ServerWorld world, Consumer<ServerPlayerEntity> consumer) {
        world.getPlayers(p -> Math.sqrt(p.squaredDistanceTo(origin)) < range).forEach(consumer);
    }
}
