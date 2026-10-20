package net.lixir.pre.common.payload;

import net.lixir.pre.common.ProgressionReEnvisioned;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.NotNull;

public record SetPositionPayload(Vec3d position, float yaw, float pitch) implements CustomPayload {
    private static final Identifier RAW_ID = ProgressionReEnvisioned.id("clientbound_set_position");
    public static final Id<SetPositionPayload> ID = new Id<>(RAW_ID);
    public static final PacketCodec<RegistryByteBuf, SetPositionPayload> CODEC = PacketCodec.ofStatic(
            (buf, value) -> {
                buf.writeDouble(value.position.x);
                buf.writeDouble(value.position.y);
                buf.writeDouble(value.position.z);
                buf.writeFloat(value.yaw);
                buf.writeFloat(value.pitch);
            },
            buf -> {
                double x = buf.readDouble();
                double y = buf.readDouble();
                double z = buf.readDouble();
                Vec3d vel = new Vec3d(x, y, z);
                float yaw = buf.readFloat();
                float pitch = buf.readFloat();
                return new SetPositionPayload(vel, yaw, pitch);
            });

    @Override
    public @NotNull Id<? extends CustomPayload> getId() {
        return ID;
    }
}
