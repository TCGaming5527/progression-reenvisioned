package net.lixir.pre.client.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.lixir.pre.item.PREItems;
import net.lixir.pre.loot.PRELootTables;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryWrapper;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class PRERecyclableItemProvider extends RecyclableItemProvider {
    public PRERecyclableItemProvider(@NotNull FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(output, registryLookup);
    }

    @Override
    protected void addRecyclableItems() {
        add(Items.ROTTEN_FLESH, PRELootTables.COMMON_RECYCLE);
        add(Items.SPIDER_EYE, PRELootTables.COMMON_RECYCLE);
        add(Items.STRING, PRELootTables.COMMON_RECYCLE);
        add(Items.BONE, PRELootTables.COMMON_RECYCLE);
        add(Items.SLIME_BALL, PRELootTables.COMMON_RECYCLE);
        add(Items.ARMADILLO_SCUTE, PRELootTables.COMMON_RECYCLE);
        add(Items.ENDER_PEARL, PRELootTables.COMMON_RECYCLE);
        add(Items.BLAZE_ROD, PRELootTables.COMMON_RECYCLE);
        add(Items.BLAZE_POWDER, PRELootTables.COMMON_RECYCLE);
        add(Items.POISONOUS_POTATO, PRELootTables.COMMON_RECYCLE);
        add(Items.GUNPOWDER, PRELootTables.COMMON_RECYCLE);
        add(Items.POINTED_DRIPSTONE, PRELootTables.COMMON_RECYCLE);
        add(Items.DRIPSTONE_BLOCK, PRELootTables.COMMON_RECYCLE);
        add(Items.STICK, PRELootTables.COMMON_RECYCLE);
        add(Items.OAK_SAPLING, PRELootTables.COMMON_RECYCLE);
        add(Items.SPRUCE_SAPLING, PRELootTables.COMMON_RECYCLE);
        add(Items.ACACIA_SAPLING, PRELootTables.COMMON_RECYCLE);
        add(Items.BIRCH_SAPLING, PRELootTables.COMMON_RECYCLE);
        add(Items.CHERRY_SAPLING, PRELootTables.COMMON_RECYCLE);
        add(Items.DARK_OAK_SAPLING, PRELootTables.COMMON_RECYCLE);
        add(Items.JUNGLE_SAPLING, PRELootTables.COMMON_RECYCLE);
        add(Items.PALE_OAK_SAPLING, PRELootTables.COMMON_RECYCLE);
        add(Items.RESIN_CLUMP, PRELootTables.COMMON_RECYCLE);
        add(Items.RESIN_BRICK, PRELootTables.COMMON_RECYCLE);
        add(Items.NETHER_BRICK, PRELootTables.COMMON_RECYCLE);
        add(Items.BRICKS, PRELootTables.COMMON_RECYCLE);
        add(Items.DISC_FRAGMENT_5, PRELootTables.COMMON_RECYCLE);

        add(Items.CREEPER_HEAD, PRELootTables.RARE_RECYCLE);
        add(Items.ZOMBIE_HEAD, PRELootTables.RARE_RECYCLE);
        add(Items.PIGLIN_HEAD, PRELootTables.RARE_RECYCLE);
        add(Items.SKELETON_SKULL, PRELootTables.RARE_RECYCLE);
        add(Items.WITHER_SKELETON_SKULL, PRELootTables.RARE_RECYCLE);
        add(Items.PLAYER_HEAD, PRELootTables.RARE_RECYCLE);
        add(Items.NAUTILUS_SHELL, PRELootTables.RARE_RECYCLE);
        add(Items.HEART_OF_THE_SEA, PRELootTables.RARE_RECYCLE);
        add(Items.TURTLE_SCUTE, PRELootTables.RARE_RECYCLE);
        add(Items.PIGLIN_BANNER_PATTERN, PRELootTables.RARE_RECYCLE);
        add(Items.CREEPER_BANNER_PATTERN, PRELootTables.RARE_RECYCLE);
        add(Items.FLOW_BANNER_PATTERN, PRELootTables.RARE_RECYCLE);
        add(Items.FLOWER_BANNER_PATTERN, PRELootTables.RARE_RECYCLE);
        add(Items.GLOBE_BANNER_PATTERN, PRELootTables.RARE_RECYCLE);
        add(Items.CREEPER_BANNER_PATTERN, PRELootTables.RARE_RECYCLE);
        add(Items.FIELD_MASONED_BANNER_PATTERN, PRELootTables.RARE_RECYCLE);
        add(Items.MOJANG_BANNER_PATTERN, PRELootTables.RARE_RECYCLE);
        add(Items.GUSTER_BANNER_PATTERN, PRELootTables.RARE_RECYCLE);
        add(Items.SKULL_BANNER_PATTERN, PRELootTables.RARE_RECYCLE);
        add(Items.MUSIC_DISC_13, PRELootTables.RARE_RECYCLE);
        add(Items.MUSIC_DISC_CAT, PRELootTables.RARE_RECYCLE);
        add(Items.MUSIC_DISC_BLOCKS, PRELootTables.RARE_RECYCLE);
        add(Items.MUSIC_DISC_CHIRP, PRELootTables.RARE_RECYCLE);
        add(Items.MUSIC_DISC_FAR, PRELootTables.RARE_RECYCLE);
        add(Items.MUSIC_DISC_MALL, PRELootTables.RARE_RECYCLE);
        add(Items.MUSIC_DISC_MELLOHI, PRELootTables.RARE_RECYCLE);
        add(Items.MUSIC_DISC_STAL, PRELootTables.RARE_RECYCLE);
        add(Items.MUSIC_DISC_STRAD, PRELootTables.RARE_RECYCLE);
        add(Items.MUSIC_DISC_WARD, PRELootTables.RARE_RECYCLE);
        add(Items.MUSIC_DISC_11, PRELootTables.RARE_RECYCLE);
        add(Items.MUSIC_DISC_WAIT, PRELootTables.RARE_RECYCLE);
        add(Items.MUSIC_DISC_CREATOR, PRELootTables.RARE_RECYCLE);
        add(Items.MUSIC_DISC_CREATOR_MUSIC_BOX, PRELootTables.RARE_RECYCLE);
        add(Items.MUSIC_DISC_OTHERSIDE, PRELootTables.RARE_RECYCLE);
        add(Items.MUSIC_DISC_PRECIPICE, PRELootTables.RARE_RECYCLE);
        add(Items.MUSIC_DISC_RELIC, PRELootTables.RARE_RECYCLE);
        add(Items.MUSIC_DISC_5, PRELootTables.RARE_RECYCLE);
        add(Items.BOLT_ARMOR_TRIM_SMITHING_TEMPLATE, PRELootTables.RARE_RECYCLE);
        add(Items.COAST_ARMOR_TRIM_SMITHING_TEMPLATE, PRELootTables.RARE_RECYCLE);
        add(Items.EYE_ARMOR_TRIM_SMITHING_TEMPLATE, PRELootTables.RARE_RECYCLE);
        add(Items.FLOW_ARMOR_TRIM_SMITHING_TEMPLATE, PRELootTables.RARE_RECYCLE);
        add(Items.DUNE_ARMOR_TRIM_SMITHING_TEMPLATE, PRELootTables.RARE_RECYCLE);
        add(Items.HOST_ARMOR_TRIM_SMITHING_TEMPLATE, PRELootTables.RARE_RECYCLE);
        add(Items.RAISER_ARMOR_TRIM_SMITHING_TEMPLATE, PRELootTables.RARE_RECYCLE);
        add(Items.RIB_ARMOR_TRIM_SMITHING_TEMPLATE, PRELootTables.RARE_RECYCLE);
        add(Items.SENTRY_ARMOR_TRIM_SMITHING_TEMPLATE, PRELootTables.RARE_RECYCLE);
        add(Items.SHAPER_ARMOR_TRIM_SMITHING_TEMPLATE, PRELootTables.RARE_RECYCLE);
        add(Items.SILENCE_ARMOR_TRIM_SMITHING_TEMPLATE, PRELootTables.RARE_RECYCLE);
        add(Items.SNOUT_ARMOR_TRIM_SMITHING_TEMPLATE, PRELootTables.RARE_RECYCLE);
        add(Items.SPIRE_ARMOR_TRIM_SMITHING_TEMPLATE, PRELootTables.RARE_RECYCLE);
        add(Items.WILD_ARMOR_TRIM_SMITHING_TEMPLATE, PRELootTables.RARE_RECYCLE);
        add(Items.WAYFINDER_ARMOR_TRIM_SMITHING_TEMPLATE, PRELootTables.RARE_RECYCLE);
        add(Items.VEX_ARMOR_TRIM_SMITHING_TEMPLATE, PRELootTables.RARE_RECYCLE);
        add(Items.TIDE_ARMOR_TRIM_SMITHING_TEMPLATE, PRELootTables.RARE_RECYCLE);
        add(PREItems.BASIC_UPGRADE_SMITHING_TEMPLATE, PRELootTables.RARE_RECYCLE);
        add(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE, PRELootTables.RARE_RECYCLE);
    }
}
