package net.lixir.pre.common.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableProvider;
import net.lixir.pre.common.init.PREItems;
import net.lixir.pre.common.init.PRELootTables;
import net.lixir.pre.common.init.PRELootContextTypes;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.FireworksComponent;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.AlternativeEntry;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.entry.LootTableEntry;
import net.minecraft.loot.function.*;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryWrapper;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.BiConsumer;

public class PRERecyclingLootTableProvider extends SimpleFabricLootTableProvider {
    private final CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup;

    public PRERecyclingLootTableProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(output, registryLookup, PRELootContextTypes.RECYCLING);
        this.registryLookup = registryLookup;
    }

    @Override
    public void accept(@NotNull BiConsumer<RegistryKey<LootTable>, LootTable.Builder> lootTableBiConsumer) {
        lootTableBiConsumer.accept(
                PRELootTables.RECYCLING_WOOD,
                LootTable.builder().pool(
                        LootPool.builder()
                                .rolls(ConstantLootNumberProvider.create(1))
                                // Oak
                                .with(ItemEntry.builder(Items.OAK_LOG))
                                .with(ItemEntry.builder(Items.OAK_PLANKS))

                                // Spruce
                                .with(ItemEntry.builder(Items.SPRUCE_LOG))
                                .with(ItemEntry.builder(Items.SPRUCE_PLANKS))

                                // Birch
                                .with(ItemEntry.builder(Items.BIRCH_LOG))
                                .with(ItemEntry.builder(Items.BIRCH_PLANKS))

                                // Jungle
                                .with(ItemEntry.builder(Items.JUNGLE_LOG))
                                .with(ItemEntry.builder(Items.JUNGLE_PLANKS))

                                // Acacia
                                .with(ItemEntry.builder(Items.ACACIA_LOG))
                                .with(ItemEntry.builder(Items.ACACIA_PLANKS))

                                // Dark Oak
                                .with(ItemEntry.builder(Items.DARK_OAK_LOG))
                                .with(ItemEntry.builder(Items.DARK_OAK_PLANKS))

                )
        );
        lootTableBiConsumer.accept(
                PRELootTables.RECYCLING_DIRT,
                LootTable.builder().pool(
                        LootPool.builder()
                                .rolls(ConstantLootNumberProvider.create(1))
                                .with(ItemEntry.builder(Items.DIRT))
                                .with(ItemEntry.builder(Items.COARSE_DIRT))
                                .with(ItemEntry.builder(Items.ROOTED_DIRT))
                )
        );
        lootTableBiConsumer.accept(
                PRELootTables.UNCOMMON_RECYCLE,
                LootTable.builder().pool(
                        LootPool.builder()
                                .rolls(ConstantLootNumberProvider.create(1))
                                .with(ItemEntry.builder(Items.IRON_INGOT))
                                .with(ItemEntry.builder(Items.GOLD_INGOT))
                                .with(ItemEntry.builder(Items.REDSTONE))
                                .with(ItemEntry.builder(Items.LAPIS_LAZULI))
                                .with(ItemEntry.builder(Items.COPPER_BLOCK))
                                .with(ItemEntry.builder(Items.COAL_BLOCK))
                                .with(ItemEntry.builder(Items.COOKED_BEEF))
                                .with(ItemEntry.builder(Items.GOLDEN_CARROT))
                )
        );
        lootTableBiConsumer.accept(
                PRELootTables.RECYCLING_STONE,
                LootTable.builder().pool(
                        LootPool.builder()
                                .rolls(ConstantLootNumberProvider.create(1))
                                .with(ItemEntry.builder(Items.STONE))
                                .with(ItemEntry.builder(Items.TUFF))
                                .with(ItemEntry.builder(Items.TUFF))
                                .with(ItemEntry.builder(Items.CALCITE))
                                .with(ItemEntry.builder(Items.GRANITE))
                                .with(ItemEntry.builder(Items.DEEPSLATE))
                )
        );
        lootTableBiConsumer.accept(
                PRELootTables.COMMON_RECYCLE,
                LootTable.builder().pool(
                        LootPool.builder()
                                .rolls(ConstantLootNumberProvider.create(1))
                                .with(
                                        AlternativeEntry.builder(
                                                LootTableEntry.builder(PRELootTables.UNCOMMON_RECYCLE).conditionally(RandomChanceLootCondition.builder(0.25f)),
                                                LootTableEntry.builder(PRELootTables.COMMON_LOOT)
                                        )
                                )
                )
        );
        lootTableBiConsumer.accept(
                PRELootTables.COMMON_LOOT,
                LootTable.builder().pool(
                        LootPool.builder()
                                .rolls(ConstantLootNumberProvider.create(1))
                                .with(LootTableEntry.builder(PRELootTables.RECYCLING_WOOD).weight(1))
                                .with(LootTableEntry.builder(PRELootTables.RECYCLING_DIRT).weight(2))
                                .with(LootTableEntry.builder(PRELootTables.RECYCLING_STONE).weight(2))
                                .with(ItemEntry.builder(Items.PRISMARINE).weight(2))
                                .with(ItemEntry.builder(Items.COAL).weight(2))
                                .with(ItemEntry.builder(Items.COPPER_INGOT).weight(2))
                                .with(ItemEntry.builder(Items.BAKED_POTATO).weight(3))
                                .with(ItemEntry.builder(Items.BREAD).weight(3))
                                .with(ItemEntry.builder(Items.APPLE).weight(3))
                )
        );
        lootTableBiConsumer.accept(
                PRELootTables.VOCALITE_EQUIPMENT,
                LootTable.builder().pool(
                        LootPool.builder()
                                .rolls(ConstantLootNumberProvider.create(1))
                                .with(ItemEntry.builder(PREItems.VOCALITE_SWORD))
                                .with(ItemEntry.builder(PREItems.VOCALITE_PICKAXE))
                                .with(ItemEntry.builder(PREItems.VOCALITE_AXE))
                                .with(ItemEntry.builder(PREItems.VOCALITE_SHOVEL))
                                .with(ItemEntry.builder(PREItems.VOCALITE_HOE))
                                .with(ItemEntry.builder(PREItems.VOCALITE_HELMET))
                                .with(ItemEntry.builder(PREItems.VOCALITE_CHESTPLATE))
                                .with(ItemEntry.builder(PREItems.VOCALITE_LEGGINGS))
                                .with(ItemEntry.builder(PREItems.VOCALITE_BOOTS))
                )
        );
        lootTableBiConsumer.accept(
                PRELootTables.ROSE_GOLD_EQUIPMENT,
                LootTable.builder().pool(
                        LootPool.builder()
                                .rolls(ConstantLootNumberProvider.create(1))
                                .with(ItemEntry.builder(PREItems.ROSE_GOLD_SWORD))
                                .with(ItemEntry.builder(PREItems.ROSE_GOLD_PICKAXE))
                                .with(ItemEntry.builder(PREItems.ROSE_GOLD_AXE))
                                .with(ItemEntry.builder(PREItems.ROSE_GOLD_SHOVEL))
                                .with(ItemEntry.builder(PREItems.ROSE_GOLD_HOE))
                                .with(ItemEntry.builder(PREItems.ROSE_GOLD_HELMET))
                                .with(ItemEntry.builder(PREItems.ROSE_GOLD_CHESTPLATE))
                                .with(ItemEntry.builder(PREItems.ROSE_GOLD_LEGGINGS))
                                .with(ItemEntry.builder(PREItems.ROSE_GOLD_BOOTS))
                )
        );
        lootTableBiConsumer.accept(
                PRELootTables.BRASS_EQUIPMENT,
                LootTable.builder().pool(
                        LootPool.builder()
                                .rolls(ConstantLootNumberProvider.create(1))
                                .with(ItemEntry.builder(PREItems.BRASS_SWORD))
                                .with(ItemEntry.builder(PREItems.BRASS_PICKAXE))
                                .with(ItemEntry.builder(PREItems.BRASS_AXE))
                                .with(ItemEntry.builder(PREItems.BRASS_SHOVEL))
                                .with(ItemEntry.builder(PREItems.BRASS_HOE))
                                .with(ItemEntry.builder(PREItems.BRASS_HELMET))
                                .with(ItemEntry.builder(PREItems.BRASS_CHESTPLATE))
                                .with(ItemEntry.builder(PREItems.BRASS_LEGGINGS))
                                .with(ItemEntry.builder(PREItems.BRASS_BOOTS))
                )
        );

        lootTableBiConsumer.accept(
                PRELootTables.ENCHANTED_IRON_EQUIPMENT,
                LootTable.builder().pool(
                        LootPool.builder()
                                .rolls(ConstantLootNumberProvider.create(1))
                                .with(enchanted(Items.IRON_SWORD, 5, 10))
                                .with(enchanted(Items.IRON_PICKAXE, 5, 10))
                                .with(enchanted(Items.IRON_AXE, 5, 10))
                                .with(enchanted(Items.IRON_SHOVEL, 5, 10))
                                .with(enchanted(Items.IRON_HOE, 5, 10))
                                .with(enchanted(Items.IRON_HELMET, 5, 10))
                                .with(enchanted(Items.IRON_CHESTPLATE, 5, 10))
                                .with(enchanted(Items.IRON_LEGGINGS, 5, 10))
                                .with(enchanted(Items.IRON_BOOTS, 5, 10))
                )
        );

        lootTableBiConsumer.accept(
                PRELootTables.ENCHANTED_COPPER_EQUIPMENT,
                LootTable.builder().pool(
                        LootPool.builder()
                                .rolls(ConstantLootNumberProvider.create(1))
                                .with(enchanted(PREItems.COPPER_SWORD, 5, 10))
                                .with(enchanted(PREItems.COPPER_PICKAXE, 5, 10))
                                .with(enchanted(PREItems.COPPER_AXE, 5, 10))
                                .with(enchanted(PREItems.COPPER_SHOVEL, 5, 10))
                                .with(enchanted(PREItems.COPPER_HOE, 5, 10))
                                .with(enchanted(PREItems.COPPER_HELMET, 5, 10))
                                .with(enchanted(PREItems.COPPER_CHESTPLATE, 5, 10))
                                .with(enchanted(PREItems.COPPER_LEGGINGS, 5, 10))
                                .with(enchanted(PREItems.COPPER_BOOTS, 5, 10))
                )
        );

        lootTableBiConsumer.accept(
                PRELootTables.ENCHANTED_GOLD_EQUIPMENT,
                LootTable.builder().pool(
                        LootPool.builder()
                                .rolls(ConstantLootNumberProvider.create(1))
                                .with(enchanted(Items.GOLDEN_SWORD, 5, 10))
                                .with(enchanted(Items.GOLDEN_PICKAXE, 5, 10))
                                .with(enchanted(Items.GOLDEN_AXE, 5, 10))
                                .with(enchanted(Items.GOLDEN_SHOVEL, 5, 10))
                                .with(enchanted(Items.GOLDEN_HOE, 5, 10))
                                .with(enchanted(Items.GOLDEN_HELMET, 5, 10))
                                .with(enchanted(Items.GOLDEN_CHESTPLATE, 5, 10))
                                .with(enchanted(Items.GOLDEN_LEGGINGS, 5, 10))
                                .with(enchanted(Items.GOLDEN_BOOTS, 5, 10))
                )
        );
        lootTableBiConsumer.accept(
                PRELootTables.DIAMOND_EQUIPMENT,
                LootTable.builder().pool(
                        LootPool.builder()
                                .rolls(ConstantLootNumberProvider.create(1))
                                .with(ItemEntry.builder(Items.DIAMOND_SWORD))
                                .with(ItemEntry.builder(Items.DIAMOND_PICKAXE))
                                .with(ItemEntry.builder(Items.DIAMOND_AXE))
                                .with(ItemEntry.builder(Items.DIAMOND_SHOVEL))
                                .with(ItemEntry.builder(Items.DIAMOND_HOE))
                                .with(ItemEntry.builder(Items.DIAMOND_HELMET))
                                .with(ItemEntry.builder(Items.DIAMOND_CHESTPLATE))
                                .with(ItemEntry.builder(Items.DIAMOND_LEGGINGS))
                                .with(ItemEntry.builder(Items.DIAMOND_BOOTS))
                )
        );

        lootTableBiConsumer.accept(
                PRELootTables.FIREWORKS,
                LootTable.builder().pool(
                        LootPool.builder()
                                .rolls(ConstantLootNumberProvider.create(1))
                                .with(ItemEntry.builder(Items.FIREWORK_ROCKET)
                                        .apply(SetComponentsLootFunction.builder(DataComponentTypes.FIREWORKS, new FireworksComponent(1, List.of()))))
                                .with(ItemEntry.builder(Items.FIREWORK_ROCKET)
                                        .apply(SetComponentsLootFunction.builder(DataComponentTypes.FIREWORKS, new FireworksComponent(2, List.of()))))
                                .with(ItemEntry.builder(Items.FIREWORK_ROCKET)
                                        .apply(SetComponentsLootFunction.builder(DataComponentTypes.FIREWORKS, new FireworksComponent(3, List.of()))))
                )
        );

        lootTableBiConsumer.accept(
                PRELootTables.EPIC_RECYCLE,
                LootTable.builder().pool(
                        LootPool.builder()
                                .rolls(ConstantLootNumberProvider.create(1))
                                .with(ItemEntry.builder(Items.DIAMOND_BLOCK))
                                .with(ItemEntry.builder(Items.NETHERITE_SCRAP))
                                .with(ItemEntry.builder(PREItems.BRASS_INGOT))
                                .with(ItemEntry.builder(PREItems.ROSE_GOLD_INGOT))
                                .with(LootTableEntry.builder(PRELootTables.VOCALITE_EQUIPMENT))
                                .with(LootTableEntry.builder(PRELootTables.ROSE_GOLD_EQUIPMENT))
                                .with(LootTableEntry.builder(PRELootTables.BRASS_EQUIPMENT))
                                .with(LootTableEntry.builder(PRELootTables.DIAMOND_EQUIPMENT))
                                .with(ItemEntry.builder(PREItems.TRANSENDER))
                )
        );

        lootTableBiConsumer.accept(
                PRELootTables.RARE_RECYCLE_LOOT,
                LootTable.builder().pool(
                        LootPool.builder()
                                .rolls(ConstantLootNumberProvider.create(1))
                                .with(ItemEntry.builder(Items.DIAMOND))
                                .with(ItemEntry.builder(Items.NAME_TAG))
                                .with(ItemEntry.builder(Items.WIND_CHARGE))
                                .with(ItemEntry.builder(Items.IRON_BLOCK))
                                .with(ItemEntry.builder(Items.SUSPICIOUS_STEW).apply(
                                        SetStewEffectLootFunction.builder()
                                                .withEffect(StatusEffects.NIGHT_VISION, UniformLootNumberProvider.create(7, 10))
                                                .withEffect(StatusEffects.JUMP_BOOST, UniformLootNumberProvider.create(7, 10))
                                                .withEffect(StatusEffects.BLINDNESS, UniformLootNumberProvider.create(5, 8))
                                                .withEffect(StatusEffects.WEAKNESS, UniformLootNumberProvider.create(6, 8))
                                                .withEffect(StatusEffects.POISON, UniformLootNumberProvider.create(10, 20))
                                                .withEffect(StatusEffects.SATURATION, UniformLootNumberProvider.create(7, 10))
                                                .withEffect(StatusEffects.FIRE_RESISTANCE, UniformLootNumberProvider.create(7, 10))
                                                .withEffect(StatusEffects.REGENERATION, UniformLootNumberProvider.create(7, 10))
                                ))
                                .with(LootTableEntry.builder(PRELootTables.ENCHANTED_GOLD_EQUIPMENT))
                                .with(LootTableEntry.builder(PRELootTables.ENCHANTED_COPPER_EQUIPMENT))
                                .with(LootTableEntry.builder(PRELootTables.ENCHANTED_IRON_EQUIPMENT))
                                .with(LootTableEntry.builder(PRELootTables.FIREWORKS))
                                .with(randomlyEnchanted(Items.BOOK))
                                .with(ItemEntry.builder(Items.GOLDEN_APPLE))
                )
        );

        lootTableBiConsumer.accept(
                PRELootTables.RARE_RECYCLE,
                LootTable.builder().pool(
                        LootPool.builder()
                                .rolls(ConstantLootNumberProvider.create(1))
                                .with(
                                        AlternativeEntry.builder(
                                                LootTableEntry.builder(PRELootTables.EPIC_RECYCLE).conditionally(RandomChanceLootCondition.builder(0.15f)),
                                                LootTableEntry.builder(PRELootTables.RARE_RECYCLE_LOOT)
                                        )
                                )
                )
        );
        lootTableBiConsumer.accept(
                PRELootTables.RELIC_RECYCLE,
                LootTable.builder().pool(
                        LootPool.builder()
                                .rolls(ConstantLootNumberProvider.create(1))
                                .with(ItemEntry.builder(PREItems.BASIC_UPGRADE_SMITHING_TEMPLATE))
                                .with(ItemEntry.builder(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE))
                                .with(randomlyEnchanted(Items.BOOK))
                                .with(ItemEntry.builder(Items.NETHERITE_SCRAP).apply(
                                        SetCountLootFunction.builder(UniformLootNumberProvider.create(1, 2))
                                ))
                                .with(ItemEntry.builder(Items.ECHO_SHARD).apply(
                                        SetCountLootFunction.builder(UniformLootNumberProvider.create(1, 3))
                                ))
                                .with(ItemEntry.builder(Items.DIAMOND).apply(
                                        SetCountLootFunction.builder(UniformLootNumberProvider.create(1, 4))
                                ))
                )
        );

    }
    private ItemEntry.Builder randomlyEnchanted(Item item) {
        try {
            return ItemEntry.builder(item)
                    .apply(
                            EnchantRandomlyLootFunction.builder(
                                    this.registryLookup.get()
                            )
                    );
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    private ItemEntry.Builder enchanted(Item item, float min, float max) {
        try {
            return ItemEntry.builder(item)
                    .apply(
                            EnchantWithLevelsLootFunction.builder(
                                    this.registryLookup.get(),
                                    UniformLootNumberProvider.create(min, max)
                            )
                    );
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }


}
