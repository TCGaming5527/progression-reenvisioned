package net.lixir.pre.common.payload;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.lixir.pre.client.gui.hud.EnderEyeSpeechHud;
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
    public static final PacketCodec<RegistryByteBuf, EnderEyeSpeechPayload> CODEC = PacketCodec.tuple(
            BlockPos.PACKET_CODEC, EnderEyeSpeechPayload::strongholdPos, EnderEyeSpeechPayload::new
    );

    public static void handle(EnderEyeSpeechPayload payload, ClientPlayNetworking.Context context) {
        EnderEyeSpeechHud.INSTANCE.onPayloadReceived(payload, context);
    }

    @Override
    public @NotNull Id<? extends CustomPayload> getId() {
        return ID;
    }
}
