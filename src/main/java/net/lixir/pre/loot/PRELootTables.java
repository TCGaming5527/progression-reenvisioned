package net.lixir.pre.loot;

import lombok.Getter;
import lombok.experimental.UtilityClass;
import net.lixir.pre.ProgressionReEnvisioned;
import net.minecraft.loot.LootTable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@UtilityClass
public class PRELootTables {
    private final Set<RegistryKey<LootTable>> LOOT_TABLES = new HashSet<>();
    @Getter
    private final Set<RegistryKey<LootTable>> LOOT_TABLES_READ_ONLY = Collections.unmodifiableSet(LOOT_TABLES);


    //region Fishing
    public final RegistryKey<LootTable> FISHING_WARM = of("fishing/warm");
    public final RegistryKey<LootTable> FISHING_WARM_FISH = of("fishing/warm/fish");
    public final RegistryKey<LootTable> FISHING_WARM_TREASURE = of("fishing/warm/treasure");
    public final RegistryKey<LootTable> FISHING_WARM_JUNK = of("fishing/warm/junk");
    public final RegistryKey<LootTable> FISHING_TEMPERATE = of("fishing/temperate");
    public final RegistryKey<LootTable> FISHING_TEMPERATE_FISH = of("fishing/temperate/fish");
    public final RegistryKey<LootTable> FISHING_TEMPERATE_TREASURE = of("fishing/temperate/treasure");
    public final RegistryKey<LootTable> FISHING_TEMPERATE_JUNK = of("fishing/temperate/junk");
    public final RegistryKey<LootTable> FISHING_COLD = of("fishing/cold");
    public final RegistryKey<LootTable> FISHING_COLD_FISH = of("fishing/cold/fish");
    public final RegistryKey<LootTable> FISHING_COLD_TREASURE = of("fishing/cold/treasure");
    public final RegistryKey<LootTable> FISHING_COLD_JUNK = of("fishing/cold/junk");
    //endregion
    //region Recycling
    public final RegistryKey<LootTable> RECYCLING_WOOD = of("recycling/wood");
    public final RegistryKey<LootTable> RECYCLING_STONE = of("recycling/stone");
    public final RegistryKey<LootTable> RECYCLING_DIRT = of("recycling/dirt");
    public final RegistryKey<LootTable> COMMON_RECYCLE = of("recycling/common");
    public final RegistryKey<LootTable> UNCOMMON_RECYCLE = of("recycling/uncommon");
    public final RegistryKey<LootTable> COMMON_LOOT = of("recycling/common_loot");
    public final RegistryKey<LootTable> RARE_RECYCLE_LOOT = of("recycling/rare_loot");
    public final RegistryKey<LootTable> RARE_RECYCLE = of("recycling/rare");
    public final RegistryKey<LootTable> EPIC_RECYCLE = of("recycling/epic");
    public final RegistryKey<LootTable> BRASS_EQUIPMENT = of("recycling/brass_equipment");
    public final RegistryKey<LootTable> DIAMOND_EQUIPMENT = of("recycling/diamond_equipment");
    public final RegistryKey<LootTable> ROSE_GOLD_EQUIPMENT = of("recycling/rose_gold_equipment");
    public final RegistryKey<LootTable> VOCALITE_EQUIPMENT = of("recycling/vocalite_equipment");
    public final RegistryKey<LootTable> FIREWORKS = of("recycling/fireworks");
    public final RegistryKey<LootTable> ENCHANTED_IRON_EQUIPMENT = of("recycling/enchanted_iron_equipment");
    public final RegistryKey<LootTable> ENCHANTED_GOLD_EQUIPMENT = of("recycling/enchanted_gold_equipment");
    public final RegistryKey<LootTable> ENCHANTED_COPPER_EQUIPMENT = of("recycling/enchanted_copper_equipment");
    //endregion

    private RegistryKey<LootTable> of(String name) {
        RegistryKey<LootTable> key = RegistryKey.of(RegistryKeys.LOOT_TABLE, ProgressionReEnvisioned.id(name));
        if (LOOT_TABLES.add(key)) {
            return key;
        } else {
            throw new IllegalArgumentException(key.getValue() + " is already a registered built-in loot table");
        }
    }
}
