package net.lixir.pre.common.payload;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.lixir.pre.client.gui.hud.ItemPickupNotifierHud;
import net.lixir.pre.common.ProgressionReEnvisioned;
import net.minecraft.item.Item;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

public record ItemPickupNotifierPayload(Item item, int count) implements CustomPayload {
    private static final Identifier RAW_ID = ProgressionReEnvisioned.id("clientbound_send_item_pickup_notifier");
    public static final Id<ItemPickupNotifierPayload> ID = new Id<>(RAW_ID);
    public static final PacketCodec<RegistryByteBuf, ItemPickupNotifierPayload> CODEC = PacketCodec.tuple(
            PacketCodecs.registryCodec(Registries.ITEM.getCodec()), ItemPickupNotifierPayload::item,
            PacketCodecs.INTEGER, ItemPickupNotifierPayload::count,
            ItemPickupNotifierPayload::new
    );

    public static void handle(ItemPickupNotifierPayload payload, ClientPlayNetworking.Context context) {
        ItemPickupNotifierHud.INSTANCE.onPayloadReceived(payload, context);
    }

    @Override
    public @NotNull Id<? extends CustomPayload> getId() {
        return ID;
    }
}
