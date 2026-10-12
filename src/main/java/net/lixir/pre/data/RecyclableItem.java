package net.lixir.pre.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.loot.LootTable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public record RecyclableItem(
        Identifier item,
        RegistryKey<LootTable> lootTable
) {
    public static final Codec<RecyclableItem> CODEC =
            RecordCodecBuilder.create(instance -> instance.group(
                    Identifier.CODEC.fieldOf("item")
                            .forGetter(RecyclableItem::item),
                    RegistryKey.createCodec(RegistryKeys.LOOT_TABLE)
                            .fieldOf("loot_table")
                            .forGetter(RecyclableItem::lootTable)
            ).apply(instance, RecyclableItem::new));
}
