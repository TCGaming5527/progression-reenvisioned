package net.lixir.pre.common.init;

import net.lixir.pre.common.ProgressionReEnvisioned;
import net.minecraft.loot.LootTable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public interface PRELootTables {
    Set<RegistryKey<LootTable>> LOOT_TABLES = new HashSet<>();
    Set<RegistryKey<LootTable>> LOOT_TABLES_READ_ONLY = Collections.unmodifiableSet(LOOT_TABLES);


    //region Fishing
    RegistryKey<LootTable> FISHING_WARM = of("fishing/warm");
    RegistryKey<LootTable> FISHING_WARM_FISH = of("fishing/warm/fish");
    RegistryKey<LootTable> FISHING_WARM_TREASURE = of("fishing/warm/treasure");
    RegistryKey<LootTable> FISHING_WARM_JUNK = of("fishing/warm/junk");
    RegistryKey<LootTable> FISHING_TEMPERATE = of("fishing/temperate");
    RegistryKey<LootTable> FISHING_TEMPERATE_FISH = of("fishing/temperate/fish");
    RegistryKey<LootTable> FISHING_TEMPERATE_TREASURE = of("fishing/temperate/treasure");
    RegistryKey<LootTable> FISHING_TEMPERATE_JUNK = of("fishing/temperate/junk");
    RegistryKey<LootTable> FISHING_COLD = of("fishing/cold");
    RegistryKey<LootTable> FISHING_COLD_FISH = of("fishing/cold/fish");
    RegistryKey<LootTable> FISHING_COLD_TREASURE = of("fishing/cold/treasure");
    RegistryKey<LootTable> FISHING_COLD_JUNK = of("fishing/cold/junk");
    //endregion
    //region Recycling
    RegistryKey<LootTable> RECYCLING_WOOD = of("recycling/wood");
    RegistryKey<LootTable> RECYCLING_STONE = of("recycling/stone");
    RegistryKey<LootTable> RECYCLING_DIRT = of("recycling/dirt");
    RegistryKey<LootTable> COMMON_RECYCLE = of("recycling/common");
    RegistryKey<LootTable> UNCOMMON_RECYCLE = of("recycling/uncommon");
    RegistryKey<LootTable> COMMON_LOOT = of("recycling/common_loot");
    RegistryKey<LootTable> RARE_RECYCLE_LOOT = of("recycling/rare_loot");
    RegistryKey<LootTable> RARE_RECYCLE = of("recycling/rare");
    RegistryKey<LootTable> EPIC_RECYCLE = of("recycling/epic");
    RegistryKey<LootTable> BRASS_EQUIPMENT = of("recycling/brass_equipment");
    RegistryKey<LootTable> DIAMOND_EQUIPMENT = of("recycling/diamond_equipment");
    RegistryKey<LootTable> ROSE_GOLD_EQUIPMENT = of("recycling/rose_gold_equipment");
    RegistryKey<LootTable> VOCALITE_EQUIPMENT = of("recycling/vocalite_equipment");
    RegistryKey<LootTable> FIREWORKS = of("recycling/fireworks");
    RegistryKey<LootTable> ENCHANTED_IRON_EQUIPMENT = of("recycling/enchanted_iron_equipment");
    RegistryKey<LootTable> ENCHANTED_GOLD_EQUIPMENT = of("recycling/enchanted_gold_equipment");
    RegistryKey<LootTable> ENCHANTED_COPPER_EQUIPMENT = of("recycling/enchanted_copper_equipment");
    //endregion

    static RegistryKey<LootTable> of(String name) {
        RegistryKey<LootTable> key = RegistryKey.of(RegistryKeys.LOOT_TABLE, ProgressionReEnvisioned.id(name));
        if (LOOT_TABLES.add(key)) {
            return key;
        } else {
            throw new IllegalArgumentException(key.getValue() + " is already a registered built-in loot table");
        }
    }
}
