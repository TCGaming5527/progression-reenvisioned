package net.lixir.pre.common.payload;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.lixir.pre.client.sound.ItemUseSoundInstance;
import net.lixir.pre.common.ProgressionReEnvisioned;
import net.lixir.pre.common.util.PREUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.registry.Registries;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public record PlayItemUseSoundPayload(int entityId, Item item, SoundEvent soundEvent, SoundCategory soundCategory) implements CustomPayload {
    private static final Identifier RAW_ID = ProgressionReEnvisioned.id("clientbound_play_item_use_sound");
    public static final Id<PlayItemUseSoundPayload> ID = new Id<>(RAW_ID);

    public static final PacketCodec<RegistryByteBuf, PlayItemUseSoundPayload> CODEC = PacketCodec.tuple(
            PacketCodecs.INTEGER, PlayItemUseSoundPayload::entityId,
            PacketCodecs.registryCodec(Registries.ITEM.getCodec()), PlayItemUseSoundPayload::item,
            SoundEvent.PACKET_CODEC, PlayItemUseSoundPayload::soundEvent,
            PREUtil.SOUND_CATEGORY_PACKET_CODEC, PlayItemUseSoundPayload::soundCategory,
            PlayItemUseSoundPayload::new
    );

    public static void send(ServerPlayerEntity player, LivingEntity holder, Item item, SoundEvent soundEvent, SoundCategory soundCategory) {
        ServerPlayNetworking.send(player, new PlayItemUseSoundPayload(holder.getId(), item, soundEvent, soundCategory));
    }

    public static void handle(PlayItemUseSoundPayload payload, ClientPlayNetworking.Context context) {
        MinecraftClient client = context.client();

        if (client != null && client.world != null) {
            Entity holder = client.world.getEntityById(payload.entityId);

            if (holder instanceof LivingEntity living) {
                client.getSoundManager().play(new ItemUseSoundInstance(
                        payload.soundEvent, payload.soundCategory,
                        living.getRandom(), living, payload.item
                ));
            }
        }
    }

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
