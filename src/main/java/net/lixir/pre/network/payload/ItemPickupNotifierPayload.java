package net.lixir.pre.network.payload;

import net.lixir.pre.ProgressionReEnvisioned;
import net.minecraft.item.Item;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

public record ItemPickupNotifierPayload(Item item, int count) implements CustomPayload {
    private static final Identifier RAW_ID = ProgressionReEnvisioned.id("clientbound_send_item_pickup_notifier");
    public static final Id<ItemPickupNotifierPayload> ID = new Id<>(RAW_ID);
    public static final PacketCodec<RegistryByteBuf, ItemPickupNotifierPayload> CODEC = PacketCodec.ofStatic(
            (buf, value) -> {
                buf.writeIdentifier(Registries.ITEM.getId(value.item));
                buf.writeVarInt(value.count);
            },
            buf -> {
                Identifier id = buf.readIdentifier();
                Item item = Registries.ITEM.get(id);
                int count = buf.readVarInt();
                return new ItemPickupNotifierPayload(item, count);
            });

    @Override
    public @NotNull Id<? extends CustomPayload> getId() {
        return ID;
    }
}
