package net.lixir.pre.network.payload;

import net.lixir.pre.ProgressionReEnvisioned;
import net.lixir.pre.block.SpeechHudType;
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

    @Override
    public @NotNull Id<? extends CustomPayload> getId() {
        return ID;
    }
}
