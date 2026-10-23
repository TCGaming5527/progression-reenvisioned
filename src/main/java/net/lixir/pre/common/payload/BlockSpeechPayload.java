package net.lixir.pre.common.payload;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.lixir.pre.client.PREClient;
import net.lixir.pre.client.gui.hud.SpeechHud;
import net.lixir.pre.client.gui.hud.SpeechHudTypeRegistry;
import net.lixir.pre.common.ProgressionReEnvisioned;
import net.lixir.pre.common.block.SpeechHudType;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

public record BlockSpeechPayload(SpeechHudType speechHudType) implements CustomPayload {
    private static final Identifier RAW_ID = ProgressionReEnvisioned.id("clientbound_block_speech");
    public static final Id<BlockSpeechPayload> ID = new Id<>(RAW_ID);
    public static final PacketCodec<RegistryByteBuf, BlockSpeechPayload> CODEC = PacketCodec.ofStatic(
            (buf, value) -> {
                buf.writeString(value.speechHudType.getId().toString());
            },
            buf -> {
                String string = buf.readString();
                Identifier id = Identifier.tryParse(string);
                return new BlockSpeechPayload(() -> id);
            });

    public static void handle(BlockSpeechPayload payload, ClientPlayNetworking.Context context) {
        Identifier identifier = payload.speechHudType().getId();
        SpeechHud speechHud = SpeechHudTypeRegistry.getHud(identifier);
        if (PREClient.CURRENT_SPEECH != null) {
            PREClient.CURRENT_SPEECH.reset();
        }

        if (speechHud != null) {
            speechHud.activate();
        }
    }

    @Override
    public @NotNull Id<? extends CustomPayload> getId() {
        return ID;
    }
}
