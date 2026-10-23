package net.lixir.pre.common.payload;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.lixir.pre.common.ProgressionReEnvisioned;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.NotNull;

public record AddVelocityPayload(Vec3d velocity) implements CustomPayload {
    private static final Identifier RAW_ID = ProgressionReEnvisioned.id("clientbound_add_velocity");
    public static final Id<AddVelocityPayload> ID = new Id<>(RAW_ID);
    public static final PacketCodec<RegistryByteBuf, AddVelocityPayload> CODEC = PacketCodec.tuple(
            Vec3d.PACKET_CODEC, AddVelocityPayload::velocity, AddVelocityPayload::new
    );

    public static void handle(AddVelocityPayload payload, ClientPlayNetworking.Context context) {
        ClientPlayerEntity player = context.player();

        if (player != null) {
            player.addVelocity(payload.velocity);
        }
    }

    @Override
    public @NotNull Id<? extends CustomPayload> getId() {
        return ID;
    }
}
