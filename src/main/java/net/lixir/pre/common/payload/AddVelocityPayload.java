package net.lixir.pre.common.payload;

import net.lixir.pre.common.ProgressionReEnvisioned;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.NotNull;

public record AddVelocityPayload(Vec3d velocity) implements CustomPayload {
    private static final Identifier RAW_ID = ProgressionReEnvisioned.id("clientbound_add_velocity");
    public static final Id<AddVelocityPayload> ID = new Id<>(RAW_ID);
    public static final PacketCodec<RegistryByteBuf, AddVelocityPayload> CODEC = PacketCodec.ofStatic(
            (buf, value) -> {
                buf.writeDouble(value.velocity.x);
                buf.writeDouble(value.velocity.y);
                buf.writeDouble(value.velocity.z);
            },
            buf -> {
                double x = buf.readDouble();
                double y = buf.readDouble();
                double z = buf.readDouble();
                Vec3d vel = new Vec3d(x, y, z);
                return new AddVelocityPayload(vel);
            });

    @Override
    public @NotNull Id<? extends CustomPayload> getId() {
        return ID;
    }
}
