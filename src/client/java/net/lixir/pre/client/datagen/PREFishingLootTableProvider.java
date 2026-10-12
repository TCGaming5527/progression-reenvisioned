package net.lixir.pre.client.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableProvider;
import net.lixir.pre.item.PREItems;
import net.lixir.pre.loot.PRELootTables;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.entry.LootTableEntry;
import net.minecraft.loot.function.EnchantRandomlyLootFunction;
import net.minecraft.loot.function.EnchantWithLevelsLootFunction;
import net.minecraft.loot.function.SetComponentsLootFunction;
import net.minecraft.loot.function.SetDamageLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.LootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.potion.Potions;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.EnchantmentTags;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.BiConsumer;

public class PREFishingLootTableProvider extends SimpleFabricLootTableProvider {
    private final CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup;
    private final RegistryWrapper.WrapperLookup registries;

    public PREFishingLootTableProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(output, registryLookup, LootContextTypes.FISHING);
        this.registryLookup = registryLookup;
        try {
            this.registries = registryLookup.get();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void accept(@NotNull BiConsumer<RegistryKey<LootTable>, LootTable.Builder> lootTableBiConsumer) {
        lootTableBiConsumer.accept(
                PRELootTables.FISHING_TEMPERATE,
                LootTable.builder().pool(
                        LootPool.builder()
                                .rolls(ConstantLootNumberProvider.create(1))
                                .with(LootTableEntry.builder(PRELootTables.FISHING_TEMPERATE_JUNK).weight(10).quality(-2))
                                .with(LootTableEntry.builder(PRELootTables.FISHING_TEMPERATE_TREASURE).weight(5).quality(2))
                                .with(LootTableEntry.builder(PRELootTables.FISHING_TEMPERATE_FISH).weight(85).quality(-1))
                )
        );
        lootTableBiConsumer.accept(
                PRELootTables.FISHING_WARM,
                LootTable.builder().pool(
                        LootPool.builder()
                                .rolls(ConstantLootNumberProvider.create(1))
                                .with(LootTableEntry.builder(PRELootTables.FISHING_WARM_JUNK).weight(10).quality(-2))
                                .with(LootTableEntry.builder(PRELootTables.FISHING_WARM_TREASURE).weight(5).quality(2))
                                .with(LootTableEntry.builder(PRELootTables.FISHING_WARM_FISH).weight(85).quality(-1))
                )
        );
        lootTableBiConsumer.accept(
                PRELootTables.FISHING_COLD,
                LootTable.builder().pool(
                        LootPool.builder()
                                .rolls(ConstantLootNumberProvider.create(1))
                                .with(LootTableEntry.builder(PRELootTables.FISHING_COLD_JUNK).weight(10).quality(-2))
                                .with(LootTableEntry.builder(PRELootTables.FISHING_COLD_TREASURE).weight(5).quality(2))
                                .with(LootTableEntry.builder(PRELootTables.FISHING_COLD_FISH).weight(85).quality(-1))
                )
        );
        lootTableBiConsumer.accept(
                PRELootTables.FISHING_WARM_FISH,
                LootTable.builder().pool(
                        LootPool.builder()
                                .rolls(ConstantLootNumberProvider.create(1))
                                .with(ItemEntry.builder(Items.TROPICAL_FISH).weight(3))
                                .with(ItemEntry.builder(Items.PUFFERFISH).weight(1))
                )
        );
        lootTableBiConsumer.accept(
                PRELootTables.FISHING_WARM_TREASURE,
                LootTable.builder().pool(
                        LootPool.builder()
                                .rolls(ConstantLootNumberProvider.create(1))
                                .with(randomlyEnchanted(Items.BOOK))
                                .with(ItemEntry.builder(PREItems.OLD_CLOCK).weight(1))
                                .with(ItemEntry.builder(Items.POTION).apply(
                                        SetComponentsLootFunction.builder(
                                                DataComponentTypes.POTION_CONTENTS,
                                                new PotionContentsComponent(Potions.WATER_BREATHING)
                                        )).weight(1))
                                .with(ItemEntry.builder(Items.SHAPER_ARMOR_TRIM_SMITHING_TEMPLATE).weight(1))
                                .with(ItemEntry.builder(Items.FISHING_ROD)
                                        .weight(1)
                                        .apply(SetDamageLootFunction.builder(UniformLootNumberProvider.create(0,0.25f)))
                                        .apply(EnchantWithLevelsLootFunction.builder(this.registries, ConstantLootNumberProvider.create(30.0F)))
                                )
                                .with(ItemEntry.builder(Items.NAUTILUS_SHELL).weight(1))
                )
        );
        lootTableBiConsumer.accept(
                PRELootTables.FISHING_WARM_JUNK,
                LootTable.builder().pool(
                        LootPool.builder()
                                .rolls(ConstantLootNumberProvider.create(1))
                                .with(ItemEntry.builder(Items.COCOA_BEANS).weight(17))
                                .with(ItemEntry.builder(Items.BAMBOO).weight(10))
                                .with(ItemEntry.builder(Items.LEATHER).weight(10))
                                .with(ItemEntry.builder(Items.ROTTEN_FLESH).weight(10))
                                .with(ItemEntry.builder(Items.STICK).weight(5))
                                .with(ItemEntry.builder(Items.STRING).weight(5))
                                .with(ItemEntry.builder(Items.GUNPOWDER).weight(10))
                                .with(ItemEntry.builder(Items.TURTLE_SCUTE).weight(1))
                                .with(ItemEntry.builder(Items.TRIPWIRE_HOOK).weight(10))
                                .with(ItemEntry.builder(Items.POTION).apply(
                                        SetComponentsLootFunction.builder(
                                                DataComponentTypes.POTION_CONTENTS,
                                                new PotionContentsComponent(Potions.MUNDANE)
                                        )).weight(10))
                                .with(ItemEntry.builder(Items.SPONGE).weight(2))
                                .with(ItemEntry.builder(Items.LEATHER_HELMET).apply(
                                        SetDamageLootFunction.builder(UniformLootNumberProvider.create(0,0.25f))).weight(10))
                )
        );
        lootTableBiConsumer.accept(
                PRELootTables.FISHING_TEMPERATE_FISH,
                LootTable.builder().pool(
                        LootPool.builder()
                                .rolls(ConstantLootNumberProvider.create(1))
                                .with(ItemEntry.builder(Items.COD).weight(3))
                                .with(ItemEntry.builder(Items.PUFFERFISH).weight(1))
                )
        );
        lootTableBiConsumer.accept(
                PRELootTables.FISHING_TEMPERATE_TREASURE,
                LootTable.builder().pool(
                        LootPool.builder()
                                .rolls(ConstantLootNumberProvider.create(1))
                                .with(randomlyEnchanted(Items.BOOK))
                                .with(ItemEntry.builder(PREItems.OLD_CLOCK).weight(1))
                                .with(ItemEntry.builder(Items.HOST_ARMOR_TRIM_SMITHING_TEMPLATE).weight(1))
                                .with(ItemEntry.builder(Items.FISHING_ROD)
                                        .weight(1)
                                        .apply(SetDamageLootFunction.builder(UniformLootNumberProvider.create(0,0.25f)))
                                        .apply(EnchantWithLevelsLootFunction.builder(this.registries, ConstantLootNumberProvider.create(30.0F)))
                                )
                                .with(ItemEntry.builder(Items.SADDLE).weight(1))
                                .with(ItemEntry.builder(Items.EXPERIENCE_BOTTLE).weight(1))
                )
        );
        lootTableBiConsumer.accept(
                PRELootTables.FISHING_TEMPERATE_JUNK,
                LootTable.builder().pool(
                        LootPool.builder()
                                .rolls(ConstantLootNumberProvider.create(1))
                                .with(ItemEntry.builder(Items.LILY_PAD).weight(17))
                                .with(ItemEntry.builder(Items.BOWL).weight(10))
                                .with(ItemEntry.builder(Items.LEATHER).weight(10))
                                .with(ItemEntry.builder(Items.STICK).weight(5))
                                .with(ItemEntry.builder(Items.STRING).weight(5))
                                .with(ItemEntry.builder(Items.BONE).weight(10))
                                .with(ItemEntry.builder(Items.INK_SAC).weight(1))
                                .with(ItemEntry.builder(Items.TARGET).weight(10))
                                .with(ItemEntry.builder(Items.POTION).apply(
                                        SetComponentsLootFunction.builder(
                                                DataComponentTypes.POTION_CONTENTS,
                                                new PotionContentsComponent(Potions.AWKWARD)
                                        )).weight(10))
                                .with(ItemEntry.builder(Items.SPONGE).weight(2))
                                .with(ItemEntry.builder(Items.LEATHER_CHESTPLATE)
                                        .apply(SetDamageLootFunction.builder(UniformLootNumberProvider.create(0,0.25f)))
                                        .weight(10)
                                )
                                .with(ItemEntry.builder(Items.ROTTEN_FLESH).weight(10))
                )
        );
        lootTableBiConsumer.accept(
                PRELootTables.FISHING_COLD_FISH,
                LootTable.builder().pool(
                        LootPool.builder()
                                .rolls(ConstantLootNumberProvider.create(1))
                                .with(ItemEntry.builder(Items.SALMON).weight(3))
                                .with(ItemEntry.builder(Items.PUFFERFISH).weight(1))
                )
        );
        lootTableBiConsumer.accept(
                PRELootTables.FISHING_COLD_TREASURE,
                LootTable.builder().pool(
                        LootPool.builder()
                                .rolls(ConstantLootNumberProvider.create(1))
                                .with(randomlyEnchanted(Items.BOOK))
                                .with(ItemEntry.builder(PREItems.OLD_CLOCK).weight(1))
                                .with(ItemEntry.builder(Items.RAISER_ARMOR_TRIM_SMITHING_TEMPLATE).weight(1))
                                .with(ItemEntry.builder(Items.FISHING_ROD)
                                        .weight(1)
                                        .apply(SetDamageLootFunction.builder(UniformLootNumberProvider.create(0,0.25f)))
                                        .apply(EnchantWithLevelsLootFunction.builder(this.registries, ConstantLootNumberProvider.create(30.0F)))
                                )
                                .with(ItemEntry.builder(Items.NAME_TAG).weight(1))
                                .with(ItemEntry.builder(Items.PHANTOM_MEMBRANE).weight(1))
                )
        );
        lootTableBiConsumer.accept(
                PRELootTables.FISHING_COLD_JUNK,
                LootTable.builder().pool(
                        LootPool.builder()
                                .rolls(ConstantLootNumberProvider.create(1))
                                .with(ItemEntry.builder(Items.SWEET_BERRIES).weight(17))
                                .with(ItemEntry.builder(Items.SNOWBALL).weight(10))
                                .with(ItemEntry.builder(Items.LEATHER).weight(10))
                                .with(ItemEntry.builder(Items.ROTTEN_FLESH).weight(10))
                                .with(ItemEntry.builder(Items.STICK).weight(5))
                                .with(ItemEntry.builder(Items.WHITE_WOOL).weight(5))
                                .with(ItemEntry.builder(Items.NOTE_BLOCK).weight(10))
                                .with(ItemEntry.builder(Items.GLOW_INK_SAC).weight(1))
                                .with(ItemEntry.builder(Items.ICE).weight(10))
                                .with(ItemEntry.builder(Items.POTION).apply(
                                        SetComponentsLootFunction.builder(
                                                DataComponentTypes.POTION_CONTENTS,
                                                new PotionContentsComponent(Potions.THICK)
                                        )).weight(10))
                                .with(ItemEntry.builder(Items.SPONGE).weight(2))
                                .with(ItemEntry.builder(Items.LEATHER_BOOTS)
                                        .apply(SetDamageLootFunction.builder(UniformLootNumberProvider.create(0,0.25f)))
                                        .weight(10)
                                )
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

}
