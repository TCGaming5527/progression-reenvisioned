package net.lixir.pre.common.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.data.recipe.RecipeExporter;
import net.minecraft.data.recipe.RecipeGenerator;
import net.minecraft.item.Items;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.Identifier;

import java.util.concurrent.CompletableFuture;

public class PREVanillaRecipeProvider extends FabricRecipeProvider {

    public PREVanillaRecipeProvider(
            FabricDataOutput output,
            CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture
    ) {
        super(output, registriesFuture);
    }

    @Override
    protected RecipeGenerator getRecipeGenerator(
            RegistryWrapper.WrapperLookup registries,
            RecipeExporter exporter
    ) {
        return new RecipeGenerator(registries, exporter) {


            @Override
            public void generate() {
                createShapeless(RecipeCategory.TOOLS, Items.ENDER_EYE, 3)
                        .input(Items.BLAZE_POWDER, 1)
                        .input(Items.ENDER_PEARL, 1)
                        .input(Items.ECHO_SHARD, 1)
                        .criterion("has_blaze_powder", conditionsFromItem(Items.BLAZE_POWDER))
                        .criterion("has_echo_shard", conditionsFromItem(Items.ECHO_SHARD))
                        .criterion("has_ender_pearl", conditionsFromItem(Items.ENDER_PEARL))
                        .offerTo(this.exporter, vanillaRegistryKey("ender_eye"));
                createShapeless(RecipeCategory.BUILDING_BLOCKS, Items.LANTERN, 4)
                        .input(Items.TORCH, 1)
                        .input(Items.IRON_INGOT, 1)
                        .criterion("has_torch", conditionsFromItem(Items.TORCH))
                        .criterion("has_iron_ingot", conditionsFromItem(Items.IRON_INGOT))
                        .offerTo(this.exporter, vanillaRegistryKey("lantern"));
                createShapeless(RecipeCategory.BUILDING_BLOCKS, Items.SOUL_LANTERN, 4)
                        .input(Items.SOUL_TORCH, 1)
                        .input(Items.IRON_INGOT, 1)
                        .criterion("has_soul_torch", conditionsFromItem(Items.SOUL_TORCH))
                        .criterion("has_iron_ingot", conditionsFromItem(Items.IRON_INGOT))
                        .offerTo(this.exporter, vanillaRegistryKey("soul_lantern"));
                createShapeless(RecipeCategory.BUILDING_BLOCKS, Items.MUD, 8)
                        .input(Items.DIRT, 8)
                        .input(Items.WATER_BUCKET, 1)
                        .criterion("has_dirt", conditionsFromItem(Items.DIRT))
                        .criterion("has_water_bucket", conditionsFromItem(Items.WATER_BUCKET))
                        .offerTo(this.exporter, vanillaRegistryKey("mud"));
                createShapeless(RecipeCategory.BUILDING_BLOCKS, Items.RED_CONCRETE, 8)
                        .input(Items.RED_CONCRETE_POWDER, 8)
                        .input(Items.WATER_BUCKET, 1)
                        .criterion("has_red_concrete_powder", conditionsFromItem(Items.RED_CONCRETE_POWDER))
                        .criterion("has_water_bucket", conditionsFromItem(Items.WATER_BUCKET))
                        .offerTo(this.exporter, vanillaRegistryKey("red_concrete"));
                createShapeless(RecipeCategory.BUILDING_BLOCKS, Items.ORANGE_CONCRETE, 8)
                        .input(Items.ORANGE_CONCRETE_POWDER, 8)
                        .input(Items.WATER_BUCKET, 1)
                        .criterion("has_orange_concrete_powder", conditionsFromItem(Items.ORANGE_CONCRETE_POWDER))
                        .criterion("has_water_bucket", conditionsFromItem(Items.WATER_BUCKET))
                        .offerTo(this.exporter, vanillaRegistryKey("orange_concrete"));
                createShapeless(RecipeCategory.BUILDING_BLOCKS, Items.YELLOW_CONCRETE, 8)
                        .input(Items.YELLOW_CONCRETE_POWDER, 8)
                        .input(Items.WATER_BUCKET, 1)
                        .criterion("has_yellow_concrete_powder", conditionsFromItem(Items.YELLOW_CONCRETE_POWDER))
                        .criterion("has_water_bucket", conditionsFromItem(Items.WATER_BUCKET))
                        .offerTo(this.exporter, vanillaRegistryKey("yellow_concrete"));
                createShapeless(RecipeCategory.BUILDING_BLOCKS, Items.LIME_CONCRETE, 8)
                        .input(Items.LIME_CONCRETE_POWDER, 8)
                        .input(Items.WATER_BUCKET, 1)
                        .criterion("has_lime_concrete_powder", conditionsFromItem(Items.LIME_CONCRETE_POWDER))
                        .criterion("has_water_bucket", conditionsFromItem(Items.WATER_BUCKET))
                        .offerTo(this.exporter, vanillaRegistryKey("lime_concrete"));
                createShapeless(RecipeCategory.BUILDING_BLOCKS, Items.GREEN_CONCRETE, 8)
                        .input(Items.GREEN_CONCRETE_POWDER, 8)
                        .input(Items.WATER_BUCKET, 1)
                        .criterion("has_green_concrete_powder", conditionsFromItem(Items.GREEN_CONCRETE_POWDER))
                        .criterion("has_water_bucket", conditionsFromItem(Items.WATER_BUCKET))
                        .offerTo(this.exporter, vanillaRegistryKey("green_concrete"));
                createShapeless(RecipeCategory.BUILDING_BLOCKS, Items.CYAN_CONCRETE, 8)
                        .input(Items.CYAN_CONCRETE_POWDER, 8)
                        .input(Items.WATER_BUCKET, 1)
                        .criterion("has_cyan_concrete_powder", conditionsFromItem(Items.CYAN_CONCRETE_POWDER))
                        .criterion("has_water_bucket", conditionsFromItem(Items.WATER_BUCKET))
                        .offerTo(this.exporter, vanillaRegistryKey("cyan_concrete"));
                createShapeless(RecipeCategory.BUILDING_BLOCKS, Items.LIGHT_BLUE_CONCRETE, 8)
                        .input(Items.LIGHT_BLUE_CONCRETE_POWDER, 8)
                        .input(Items.WATER_BUCKET, 1)
                        .criterion("has_light_blue_concrete_powder", conditionsFromItem(Items.LIGHT_BLUE_CONCRETE_POWDER))
                        .criterion("has_water_bucket", conditionsFromItem(Items.WATER_BUCKET))
                        .offerTo(this.exporter, vanillaRegistryKey("light_blue_concrete"));
                createShapeless(RecipeCategory.BUILDING_BLOCKS, Items.BLUE_CONCRETE, 8)
                        .input(Items.BLUE_CONCRETE_POWDER, 8)
                        .input(Items.WATER_BUCKET, 1)
                        .criterion("has_blue_concrete_powder", conditionsFromItem(Items.BLUE_CONCRETE_POWDER))
                        .criterion("has_water_bucket", conditionsFromItem(Items.WATER_BUCKET))
                        .offerTo(this.exporter, vanillaRegistryKey("blue_concrete"));
                createShapeless(RecipeCategory.BUILDING_BLOCKS, Items.PURPLE_CONCRETE, 8)
                        .input(Items.PURPLE_CONCRETE_POWDER, 8)
                        .input(Items.WATER_BUCKET, 1)
                        .criterion("has_purple_concrete_powder", conditionsFromItem(Items.RED_CONCRETE_POWDER))
                        .criterion("has_water_bucket", conditionsFromItem(Items.WATER_BUCKET))
                        .offerTo(this.exporter, vanillaRegistryKey("purple_concrete"));
                createShapeless(RecipeCategory.BUILDING_BLOCKS, Items.MAGENTA_CONCRETE, 8)
                        .input(Items.MAGENTA_CONCRETE_POWDER, 8)
                        .input(Items.WATER_BUCKET, 1)
                        .criterion("has_magenta_concrete_powder", conditionsFromItem(Items.MAGENTA_CONCRETE_POWDER))
                        .criterion("has_water_bucket", conditionsFromItem(Items.WATER_BUCKET))
                        .offerTo(this.exporter, vanillaRegistryKey("magenta_concrete"));
                createShapeless(RecipeCategory.BUILDING_BLOCKS, Items.PINK_CONCRETE, 8)
                        .input(Items.PINK_CONCRETE_POWDER, 8)
                        .input(Items.WATER_BUCKET, 1)
                        .criterion("has_pink_concrete_powder", conditionsFromItem(Items.PINK_CONCRETE_POWDER))
                        .criterion("has_water_bucket", conditionsFromItem(Items.WATER_BUCKET))
                        .offerTo(this.exporter, vanillaRegistryKey("pink_concrete"));
                createShapeless(RecipeCategory.BUILDING_BLOCKS, Items.WHITE_CONCRETE, 8)
                        .input(Items.WHITE_CONCRETE_POWDER, 8)
                        .input(Items.WATER_BUCKET, 1)
                        .criterion("has_white_concrete_powder", conditionsFromItem(Items.WHITE_CONCRETE_POWDER))
                        .criterion("has_water_bucket", conditionsFromItem(Items.WATER_BUCKET))
                        .offerTo(this.exporter, vanillaRegistryKey("white_concrete"));
                createShapeless(RecipeCategory.BUILDING_BLOCKS, Items.LIGHT_GRAY_CONCRETE, 8)
                        .input(Items.LIGHT_GRAY_CONCRETE_POWDER, 8)
                        .input(Items.WATER_BUCKET, 1)
                        .criterion("has_light_gray_concrete_powder", conditionsFromItem(Items.LIGHT_GRAY_CONCRETE_POWDER))
                        .criterion("has_water_bucket", conditionsFromItem(Items.WATER_BUCKET))
                        .offerTo(this.exporter, vanillaRegistryKey("light_gray_concrete"));
                createShapeless(RecipeCategory.BUILDING_BLOCKS, Items.GRAY_CONCRETE, 8)
                        .input(Items.GRAY_CONCRETE_POWDER, 8)
                        .input(Items.WATER_BUCKET, 1)
                        .criterion("has_gray_concrete_powder", conditionsFromItem(Items.GRAY_CONCRETE_POWDER))
                        .criterion("has_water_bucket", conditionsFromItem(Items.WATER_BUCKET))
                        .offerTo(this.exporter, vanillaRegistryKey("gray_concrete"));
                createShapeless(RecipeCategory.BUILDING_BLOCKS, Items.BLACK_CONCRETE, 8)
                        .input(Items.BLACK_CONCRETE_POWDER, 8)
                        .input(Items.WATER_BUCKET, 1)
                        .criterion("has_black_concrete_powder", conditionsFromItem(Items.BLACK_CONCRETE_POWDER))
                        .criterion("has_water_bucket", conditionsFromItem(Items.WATER_BUCKET))
                        .offerTo(this.exporter, vanillaRegistryKey("black_concrete"));
                createShapeless(RecipeCategory.BUILDING_BLOCKS, Items.BROWN_CONCRETE, 8)
                        .input(Items.RED_CONCRETE_POWDER, 8)
                        .input(Items.WATER_BUCKET, 1)
                        .criterion("has_brown_concrete_powder", conditionsFromItem(Items.BROWN_CONCRETE_POWDER))
                        .criterion("has_water_bucket", conditionsFromItem(Items.WATER_BUCKET))
                        .offerTo(this.exporter, vanillaRegistryKey("brown_concrete"));
                createShapeless(RecipeCategory.TOOLS, Items.FLINT_AND_STEEL)
                        .input(Items.COBBLESTONE, 1)
                        .input(Items.FLINT, 1)
                        .criterion("has_cobblestones", conditionsFromItem(Items.COBBLESTONE))
                        .criterion("has_flint", conditionsFromItem(Items.FLINT))
                        .offerTo(this.exporter, vanillaRegistryKey("flint_and_steel"));
                createShaped(
                            RecipeCategory.BUILDING_BLOCKS,
                            Items.CAMPFIRE
                        )
                        .pattern("SS")
                        .pattern("LL")
                        .input('S', Items.STICK)
                        .input('L', ItemTags.LOGS_THAT_BURN)
                        .criterion("has_stick", conditionsFromItem(Items.STICK))
                        .criterion("has_log", conditionsFromTag(ItemTags.LOGS_THAT_BURN))
                        .offerTo(
                                this.exporter,
                                vanillaRegistryKey("campfire")
                         );
                createShaped(
                        RecipeCategory.BUILDING_BLOCKS,
                        Items.SOUL_CAMPFIRE
                )
                        .pattern("SS")
                        .pattern("LL")
                        .input('S', ItemTags.SOUL_FIRE_BASE_BLOCKS)
                        .input('L', ItemTags.LOGS_THAT_BURN)
                        .criterion("has_soul_fire", conditionsFromTag(ItemTags.SOUL_FIRE_BASE_BLOCKS))
                        .criterion("has_log", conditionsFromTag(ItemTags.LOGS_THAT_BURN))
                        .offerTo(
                                this.exporter,
                                vanillaRegistryKey("soul_campfire")
                        );
                createShaped(
                        RecipeCategory.BUILDING_BLOCKS,
                        Items.CHAIN,
                        4
                )
                        .pattern("S")
                        .pattern("S")
                        .input('S', Items.IRON_INGOT)
                        .criterion("has_iron_ingot", conditionsFromItem(Items.IRON_INGOT))
                        .offerTo(
                                this.exporter,
                                vanillaRegistryKey("chain")
                        );
                createShaped(
                        RecipeCategory.BUILDING_BLOCKS,
                        Items.FURNACE
                )
                        .pattern("SSS")
                        .pattern("SCS")
                        .pattern("PPP")
                        .input('S', Items.STONE)
                        .input('C', ItemTags.COALS)
                        .input('P', Items.SMOOTH_STONE)
                        .criterion("has_stone", conditionsFromItem(Items.STONE))
                        .criterion("has_coals", conditionsFromTag(ItemTags.COALS))
                        .offerTo(
                                this.exporter,
                                vanillaRegistryKey("furnace")
                        );
                createShaped(
                        RecipeCategory.BUILDING_BLOCKS,
                        Items.BLAST_FURNACE
                )
                        .pattern("SSS")
                        .pattern("SCS")
                        .pattern("PPP")
                        .input('S', Items.POLISHED_DEEPSLATE)
                        .input('C', ItemTags.COALS)
                        .input('P', Items.SMOOTH_STONE)
                        .criterion("has_polished_deepslate", conditionsFromItem(Items.POLISHED_DEEPSLATE))
                        .criterion("has_coals", conditionsFromTag(ItemTags.COALS))
                        .offerTo(
                                this.exporter,
                                vanillaRegistryKey("blast_furnace")
                        );
                createShaped(
                        RecipeCategory.BUILDING_BLOCKS,
                        Items.SMOKER
                )
                        .pattern("PSP")
                        .pattern("SCS")
                        .pattern("PSP")
                        .input('S', Items.STONE)
                        .input('C', ItemTags.COALS)
                        .input('P', ItemTags.LOGS)
                        .criterion("has_stone", conditionsFromItem(Items.STONE))
                        .criterion("has_coals", conditionsFromTag(ItemTags.COALS))
                        .offerTo(
                                this.exporter,
                                vanillaRegistryKey("smoker")
                        );
                createShaped(
                        RecipeCategory.BUILDING_BLOCKS,
                        Items.FURNACE
                )
                        .pattern("CSC")
                        .pattern("P P")
                        .input('S', Items.STONE)
                        .input('C', ItemTags.PLANKS)
                        .input('P', ItemTags.LOGS)
                        .criterion("has_stone", conditionsFromItem(Items.STONE))
                        .criterion("has_planks", conditionsFromTag(ItemTags.PLANKS))
                        .offerTo(
                                this.exporter,
                                vanillaRegistryKey("grindstone")
                        );
                createShaped(
                        RecipeCategory.COMBAT,
                        Items.LEATHER_HELMET
                )
                        .pattern(" P ")
                        .pattern("P P")
                        .input('P', Items.LEATHER)
                        .criterion("has_leather", conditionsFromItem(Items.LEATHER))
                        .offerTo(
                                this.exporter,
                                vanillaRegistryKey("leather_helmet")
                        );
                createShaped(
                        RecipeCategory.COMBAT,
                        Items.LEATHER_CHESTPLATE
                )
                        .pattern("P P")
                        .pattern("PPP")
                        .pattern(" P ")
                        .input('P', Items.LEATHER)
                        .criterion("has_leather", conditionsFromItem(Items.LEATHER))
                        .offerTo(
                                this.exporter,
                                vanillaRegistryKey("leather_chestplate")
                        );
                createShaped(
                        RecipeCategory.COMBAT,
                        Items.LEATHER_LEGGINGS
                )
                        .pattern("PPP")
                        .pattern("P P")
                        .input('P', Items.LEATHER)
                        .criterion("has_leather", conditionsFromItem(Items.LEATHER))
                        .offerTo(
                                this.exporter,
                                vanillaRegistryKey("leather_leggings")
                        );
                createShaped(
                        RecipeCategory.COMBAT,
                        Items.LEATHER_BOOTS
                )
                        .pattern("P P")
                        .input('P', Items.LEATHER)
                        .criterion("has_leather", conditionsFromItem(Items.LEATHER))
                        .offerTo(
                                this.exporter,
                                vanillaRegistryKey("leather_boots")
                        );
            }
        };
    }

    private static RegistryKey<Recipe<?>> vanillaRegistryKey(String str) {
        return RegistryKey.of(
                RegistryKeys.RECIPE,
                Identifier.ofVanilla(str)
        );
    }

    @Override
    protected Identifier getRecipeIdentifier(Identifier identifier) {
        return super.getRecipeIdentifier(identifier);
    }

    @Override
    public String getName() {
        return "ProgressionRe-EnvisionedVanillaRecipeProvider";
    }
}
