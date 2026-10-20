package net.lixir.pre.common.payload;

import net.lixir.pre.common.ProgressionReEnvisioned;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.NotNull;

public record EnderEyeSpeechPayload(BlockPos strongholdPos) implements CustomPayload {
    private static final Identifier RAW_ID = ProgressionReEnvisioned.id("clientbound_ender_eye_speech");
    public static final CustomPayload.Id<EnderEyeSpeechPayload> ID = new CustomPayload.Id<>(RAW_ID);
    public static final PacketCodec<RegistryByteBuf, EnderEyeSpeechPayload> CODEC = PacketCodec.ofStatic(
            (buf, value) -> {
                BlockPos pos = value.strongholdPos;
                buf.writeInt(pos.getX());
                buf.writeInt(pos.getY());
                buf.writeInt(pos.getZ());
            },
            buf -> {
                int x = buf.readInt();
                int y = buf.readInt();
                int z = buf.readInt();
                BlockPos pos = new BlockPos(x,y,z);
                return new EnderEyeSpeechPayload(pos);
            });

    @Override
    public @NotNull Id<? extends CustomPayload> getId() {
        return ID;
    }
}
