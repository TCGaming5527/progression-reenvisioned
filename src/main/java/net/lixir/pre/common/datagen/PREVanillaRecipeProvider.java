package net.lixir.pre.common.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
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
                        .criterion("has_stick", conditionsFromItem(ItemTags.SOUL_FIRE_BASE_BLOCKS))
                        .criterion("has_log", conditionsFromTag(ItemTags.LOGS_THAT_BURN))
                        .offerTo(
                                this.exporter,
                                vanillaRegistryKey("soul_campfire")
                        );
                createShaped(
                        RecipeCategory.BUILDING_BLOCKS,
                        Items.CHAIN
                )
                        .pattern("S")
                        .pattern("S")
                        .input('S', Items.IRON_INGOT)
                        .criterion("has_iron_ingot", conditionsFromItem(Items.IRON_INGOT))
                        .offerTo(
                                this.exporter,
                                vanillaRegistryKey("chain")
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
