package net.lixir.pre.common.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.lixir.pre.common.ProgressionReEnvisioned;
import net.lixir.pre.common.recipe.InductionSmeltingRecipeJsonBuilder;
import net.lixir.pre.common.init.PREItems;
import net.lixir.pre.common.init.tag.PREConventionalItemTags;
import net.lixir.pre.common.init.tag.PREItemTags;
import net.lixir.pre.common.util.RecipeUtil;
import net.minecraft.data.recipe.RecipeExporter;
import net.minecraft.data.recipe.RecipeGenerator;
import net.minecraft.data.recipe.SmithingTransformRecipeJsonBuilder;
import net.minecraft.data.tag.ItemTagProvider;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.recipe.CampfireCookingRecipe;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

import java.util.concurrent.CompletableFuture;
public class PRERecipeProvider extends FabricRecipeProvider {

    public PRERecipeProvider(
            FabricDataOutput output,
            CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture
    ) {
        super(output, registriesFuture);
    }

    private static RegistryKey<Recipe<?>> modRegistryKey(String str) {
        return RegistryKey.of(
                RegistryKeys.RECIPE,
                ProgressionReEnvisioned.id(str)
        );
    }

    @Override
    protected RecipeGenerator getRecipeGenerator(
            RegistryWrapper.WrapperLookup registries,
            RecipeExporter exporter
    ) {
        return new RecipeGenerator(registries, exporter) {
            private void offerCampfireRecipe(
                    int cookingTime,
                    Item input,
                    Item output
            ) {
                offerFoodCookingRecipe(
                        "campfire",
                        RecipeSerializer.CAMPFIRE_COOKING,
                        CampfireCookingRecipe::new,
                        cookingTime,
                        input,
                        output,
                        0
                );
            }

            private void offerSmithingTransformRecipe(
                    Item template,
                    Item base,
                    TagKey<Item> addition,
                    Item result,
                    RecipeCategory category
            ) {
                offerSmithingTransformRecipe(template, base, Ingredient.fromTag(RecipeUtil.getItemsFromTag(registries, addition)), result, category);
            }


            private void offerSmithingTransformRecipe(
                    Item template,
                    Item base,
                    Item addition,
                    Item result,
                    RecipeCategory category
            ) {
                offerSmithingTransformRecipe(template, base, Ingredient.ofItem(addition), result, category);
            }

            private void offerSmithingTransformRecipe(
                    Item template,
                    Item base,
                    Ingredient addition,
                    Item result,
                    RecipeCategory category
            ) {
                Identifier resultId = Registries.ITEM.getId(result);

                String recipeName =
                        resultId.getPath() + "_smithing";

                SmithingTransformRecipeJsonBuilder.create(
                                Ingredient.ofItems(template),
                                Ingredient.ofItems(base),
                                addition,
                                category,
                                result
                        )
                        .criterion("has_template", conditionsFromItem(template))
                        .criterion("has_base", conditionsFromItem(base))
                        .offerTo(
                                exporter,
                                modRegistryKey(recipeName)
                        );
            }
            private void offerInductionSmeltingRecipe(
                    Item upgradeTemplate,
                    Item base,
                    TagKey<Item> addition,
                    RecipeCategory category,
                    Item output
            ) {
                offerInductionSmeltingRecipe(upgradeTemplate, base, Ingredient.fromTag(RecipeUtil.getItemsFromTag(registries, addition)), 1, 300, category, output);
            }

            private void offerInductionSmeltingRecipe(
                    Item upgradeTemplate,
                    Item base,
                    Item addition,
                    RecipeCategory category,
                    Item output
            ) {
                offerInductionSmeltingRecipe(upgradeTemplate, base, Ingredient.ofItem(addition), 1, 300, category, output);
            }
            private void offerInductionSmeltingRecipe(
                    Item upgradeTemplate,
                    Item base,
                    Ingredient addition,
                    int additionCount,
                    int smeltTime,
                    RecipeCategory category,
                    Item output
            ) {
                Identifier baseId = Registries.ITEM.getId(base);
                Identifier outputId = Registries.ITEM.getId(output);
                String name = outputId.getPath() + "_induction_smelting";
                InductionSmeltingRecipeJsonBuilder.create(
                        Ingredient.ofItem(upgradeTemplate),
                        Ingredient.ofItem(base),
                        addition,
                        additionCount,
                        smeltTime,
                        category,
                        output
                        ).criterion("has_template", conditionsFromItem(upgradeTemplate))
                        .criterion("has_base", conditionsFromItem(base))
                        .offerTo(exporter, ProgressionReEnvisioned.id(name));
            }

            //region Vanilla-shaped crafting helpers

            private void createSwordRecipe(RecipeCategory category, TagKey<Item> material, Item result) {
                createShaped(category, result)
                        .pattern(" M ")
                        .pattern(" M ")
                        .pattern(" S ")
                        .input('M', material)
                        .input('S', Items.STICK)
                        .criterion("has_material", conditionsFromTag(material))
                        .offerTo(exporter, modRegistryKey(Registries.ITEM.getId(result).getPath()));
            }

            private void createPickaxeRecipe(RecipeCategory category, TagKey<Item> material, Item result) {
                createShaped(category, result)
                        .pattern("MMM")
                        .pattern(" S ")
                        .pattern(" S ")
                        .input('M', material)
                        .input('S', Items.STICK)
                        .criterion("has_material", conditionsFromTag(material))
                        .offerTo(exporter, modRegistryKey(Registries.ITEM.getId(result).getPath()));
            }

            private void createAxeRecipe(RecipeCategory category, TagKey<Item> material, Item result) {
                createShaped(category, result)
                        .pattern("MM ")
                        .pattern("MS ")
                        .pattern(" S ")
                        .input('M', material)
                        .input('S', Items.STICK)
                        .criterion("has_material", conditionsFromTag(material))
                        .offerTo(exporter, modRegistryKey(Registries.ITEM.getId(result).getPath()));
            }

            private void createShovelRecipe(RecipeCategory category, TagKey<Item> material, Item result) {
                createShaped(category, result)
                        .pattern(" M ")
                        .pattern(" S ")
                        .pattern(" S ")
                        .input('M', material)
                        .input('S', Items.STICK)
                        .criterion("has_material", conditionsFromTag(material))
                        .offerTo(exporter, modRegistryKey(Registries.ITEM.getId(result).getPath()));
            }

            private void createHoeRecipe(RecipeCategory category, TagKey<Item> material, Item result) {
                createShaped(category, result)
                        .pattern("MM ")
                        .pattern(" S ")
                        .pattern(" S ")
                        .input('M', material)
                        .input('S', Items.STICK)
                        .criterion("has_material", conditionsFromTag(material))
                        .offerTo(exporter, modRegistryKey(Registries.ITEM.getId(result).getPath()));

            }

            // Armor

            private void createHelmetRecipe(RecipeCategory category, TagKey<Item> material, Item result) {
                createShaped(category, result)
                        .pattern("MMM")
                        .pattern("M M")
                        .input('M', material)
                        .criterion("has_material", conditionsFromTag(material))
                        .offerTo(exporter, modRegistryKey(Registries.ITEM.getId(result).getPath()));
            }

            private void createChestplateRecipe(RecipeCategory category, TagKey<Item> material, Item result) {
                createShaped(category, result)
                        .pattern("M M")
                        .pattern("MMM")
                        .pattern("MMM")
                        .input('M', material)
                        .criterion("has_material", conditionsFromTag(material))
                        .offerTo(exporter, modRegistryKey(Registries.ITEM.getId(result).getPath()));
            }

            private void createLeggingsRecipe(RecipeCategory category, TagKey<Item> material, Item result) {
                createShaped(category, result)
                        .pattern("MMM")
                        .pattern("M M")
                        .pattern("M M")
                        .input('M', material)
                        .criterion("has_material", conditionsFromTag(material))
                        .offerTo(exporter, modRegistryKey(Registries.ITEM.getId(result).getPath()));
            }

            private void createBootsRecipe(RecipeCategory category, TagKey<Item> material, Item result) {
                createShaped(category, result)
                        .pattern("M M")
                        .pattern("M M")
                        .input('M', material)
                        .criterion("has_material", conditionsFromTag(material))
                        .offerTo(exporter, modRegistryKey(Registries.ITEM.getId(result).getPath()));
            }

            //endregion

            @Override
            public void generate() {
                offerInductionSmeltingRecipe(
                        Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE,
                        Items.TRIDENT,
                        PREConventionalItemTags.ELECTRUM_INGOTS,
                        RecipeCategory.TOOLS,
                        PREItems.ELECTRUM_SPEAR
                );
                offerInductionSmeltingRecipe(
                        Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE,
                        Items.HEAVY_CORE,
                        ConventionalItemTags.NETHERITE_INGOTS,
                        RecipeCategory.TOOLS,
                        Items.MACE
                );
                offerInductionSmeltingRecipe(
                        Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE,
                        Items.TRIDENT,
                        PREConventionalItemTags.ALUMNABRASS_INGOTS,
                        RecipeCategory.TOOLS,
                        PREItems.ALUMNABRASS_SPEAR
                );
                offerInductionSmeltingRecipe(
                        Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE,
                        Items.TRIDENT,
                        PREConventionalItemTags.VOCALITE_INGOTS,
                        RecipeCategory.TOOLS,
                        PREItems.VOCALITE_SPEAR
                );
                offerInductionSmeltingRecipe(
                        Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE,
                        Items.TRIDENT,
                        ConventionalItemTags.NETHERITE_INGOTS,
                        RecipeCategory.TOOLS,
                        PREItems.NETHERITE_SPEAR
                );

                //region Netherite Induction Smelting
                offerInductionSmeltingRecipe(
                        Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE,
                        Items.DIAMOND_HELMET,
                        ConventionalItemTags.NETHERITE_INGOTS,
                        RecipeCategory.TOOLS,
                        Items.NETHERITE_HELMET
                );
                offerInductionSmeltingRecipe(
                        Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE,
                        Items.DIAMOND_CHESTPLATE,
                        ConventionalItemTags.NETHERITE_INGOTS,
                        RecipeCategory.TOOLS,
                        Items.NETHERITE_CHESTPLATE
                );
                offerInductionSmeltingRecipe(
                        Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE,
                        Items.DIAMOND_LEGGINGS,
                        ConventionalItemTags.NETHERITE_INGOTS,
                        RecipeCategory.TOOLS,
                        Items.NETHERITE_LEGGINGS
                );
                offerInductionSmeltingRecipe(
                        Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE,
                        Items.DIAMOND_BOOTS,
                        ConventionalItemTags.NETHERITE_INGOTS,
                        RecipeCategory.TOOLS,
                        Items.NETHERITE_BOOTS
                );
                offerInductionSmeltingRecipe(
                        Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE,
                        Items.DIAMOND_SWORD,
                        ConventionalItemTags.NETHERITE_INGOTS,
                        RecipeCategory.TOOLS,
                        Items.NETHERITE_SWORD
                );
                offerInductionSmeltingRecipe(
                        Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE,
                        Items.DIAMOND_PICKAXE,
                        ConventionalItemTags.NETHERITE_INGOTS,
                        RecipeCategory.TOOLS,
                        Items.NETHERITE_PICKAXE
                );
                offerInductionSmeltingRecipe(
                        Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE,
                        Items.DIAMOND_AXE,
                        ConventionalItemTags.NETHERITE_INGOTS,
                        RecipeCategory.TOOLS,
                        Items.NETHERITE_AXE
                );
                offerInductionSmeltingRecipe(
                        Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE,
                        Items.DIAMOND_SHOVEL,
                        ConventionalItemTags.NETHERITE_INGOTS,
                        RecipeCategory.TOOLS,
                        Items.NETHERITE_SHOVEL
                );
                offerInductionSmeltingRecipe(
                        Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE,
                        Items.DIAMOND_HOE,
                        ConventionalItemTags.NETHERITE_INGOTS,
                        RecipeCategory.TOOLS,
                        Items.NETHERITE_HOE
                );
                //endregion
                //region Vocalite Induction Smelting
                offerInductionSmeltingRecipe(
                        Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE,
                        Items.DIAMOND_HELMET,
                        PREConventionalItemTags.VOCALITE_INGOTS,
                        RecipeCategory.TOOLS,
                        PREItems.VOCALITE_HELMET
                );
                offerInductionSmeltingRecipe(
                        Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE,
                        Items.DIAMOND_CHESTPLATE,
                        PREConventionalItemTags.VOCALITE_INGOTS,
                        RecipeCategory.TOOLS,
                        PREItems.VOCALITE_CHESTPLATE
                );
                offerInductionSmeltingRecipe(
                        Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE,
                        Items.DIAMOND_LEGGINGS,
                        PREConventionalItemTags.VOCALITE_INGOTS,
                        RecipeCategory.TOOLS,
                        PREItems.VOCALITE_LEGGINGS
                );
                offerInductionSmeltingRecipe(
                        Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE,
                        Items.DIAMOND_BOOTS,
                        PREConventionalItemTags.VOCALITE_INGOTS,
                        RecipeCategory.TOOLS,
                        PREItems.VOCALITE_BOOTS
                );
                offerInductionSmeltingRecipe(
                        Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE,
                        Items.DIAMOND_SWORD,
                        PREConventionalItemTags.VOCALITE_INGOTS,
                        RecipeCategory.TOOLS,
                        PREItems.VOCALITE_SWORD
                );
                offerInductionSmeltingRecipe(
                        Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE,
                        Items.DIAMOND_PICKAXE,
                        PREConventionalItemTags.VOCALITE_INGOTS,
                        RecipeCategory.TOOLS,
                        PREItems.VOCALITE_PICKAXE
                );
                offerInductionSmeltingRecipe(
                        Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE,
                        Items.DIAMOND_AXE,
                        PREConventionalItemTags.VOCALITE_INGOTS,
                        RecipeCategory.TOOLS,
                        PREItems.VOCALITE_AXE
                );
                offerInductionSmeltingRecipe(
                        Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE,
                        Items.DIAMOND_SHOVEL,
                        PREConventionalItemTags.VOCALITE_INGOTS,
                        RecipeCategory.TOOLS,
                        PREItems.VOCALITE_SHOVEL
                );
                offerInductionSmeltingRecipe(
                        Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE,
                        Items.DIAMOND_HOE,
                        PREConventionalItemTags.VOCALITE_INGOTS,
                        RecipeCategory.TOOLS,
                        PREItems.VOCALITE_HOE
                );
                //endregion
                //region Alumnabrass Induction Smelting
                offerInductionSmeltingRecipe(
                        Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE,
                        PREItems.BRASS_SWORD,
                        PREConventionalItemTags.ALUMNABRASS_INGOTS,
                        RecipeCategory.TOOLS,
                        PREItems.ALUMNABRASS_SWORD
                );
                offerInductionSmeltingRecipe(
                        Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE,
                        PREItems.BRASS_PICKAXE,
                        PREConventionalItemTags.ALUMNABRASS_INGOTS,
                        RecipeCategory.TOOLS,
                        PREItems.ALUMNABRASS_PICKAXE
                );
                offerInductionSmeltingRecipe(
                        Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE,
                        PREItems.BRASS_AXE,
                        PREConventionalItemTags.ALUMNABRASS_INGOTS,
                        RecipeCategory.TOOLS,
                        PREItems.ALUMNABRASS_AXE
                );
                offerInductionSmeltingRecipe(
                        Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE,
                        PREItems.BRASS_SHOVEL,
                        PREConventionalItemTags.ALUMNABRASS_INGOTS,
                        RecipeCategory.TOOLS,
                        PREItems.ALUMNABRASS_SHOVEL
                );
                offerInductionSmeltingRecipe(
                        Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE,
                        PREItems.BRASS_HOE,
                        PREConventionalItemTags.ALUMNABRASS_INGOTS,
                        RecipeCategory.TOOLS,
                        PREItems.ALUMNABRASS_HOE
                );
                offerInductionSmeltingRecipe(
                        Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE,
                        PREItems.BRASS_HELMET,
                        PREConventionalItemTags.ALUMNABRASS_INGOTS,
                        RecipeCategory.TOOLS,
                        PREItems.ALUMNABRASS_HELMET
                );
                offerInductionSmeltingRecipe(
                        Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE,
                        PREItems.BRASS_CHESTPLATE,
                        PREConventionalItemTags.ALUMNABRASS_INGOTS,
                        RecipeCategory.TOOLS,
                        PREItems.ALUMNABRASS_CHESTPLATE
                );
                offerInductionSmeltingRecipe(
                        Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE,
                        PREItems.BRASS_LEGGINGS,
                        PREConventionalItemTags.ALUMNABRASS_INGOTS,
                        RecipeCategory.TOOLS,
                        PREItems.ALUMNABRASS_LEGGINGS
                );
                offerInductionSmeltingRecipe(
                        Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE,
                        PREItems.BRASS_BOOTS,
                        PREConventionalItemTags.ALUMNABRASS_INGOTS,
                        RecipeCategory.TOOLS,
                        PREItems.ALUMNABRASS_BOOTS
                );
                //endregion
                //region Electrum Induction Smelting
                offerInductionSmeltingRecipe(
                        Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE,
                        PREItems.ROSE_GOLD_SWORD,
                        PREConventionalItemTags.ELECTRUM_INGOTS,
                        RecipeCategory.TOOLS,
                        PREItems.ELECTRUM_SWORD
                );
                offerInductionSmeltingRecipe(
                        Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE,
                        PREItems.ROSE_GOLD_PICKAXE,
                        PREConventionalItemTags.ELECTRUM_INGOTS,
                        RecipeCategory.TOOLS,
                        PREItems.ELECTRUM_PICKAXE
                );
                offerInductionSmeltingRecipe(
                        Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE,
                        PREItems.ROSE_GOLD_AXE,
                        PREConventionalItemTags.ELECTRUM_INGOTS,
                        RecipeCategory.TOOLS,
                        PREItems.ELECTRUM_AXE
                );
                offerInductionSmeltingRecipe(
                        Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE,
                        PREItems.ROSE_GOLD_SHOVEL,
                        PREConventionalItemTags.ELECTRUM_INGOTS,
                        RecipeCategory.TOOLS,
                        PREItems.ELECTRUM_SHOVEL
                );
                offerInductionSmeltingRecipe(
                        Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE,
                        PREItems.ROSE_GOLD_HOE,
                        PREConventionalItemTags.ELECTRUM_INGOTS,
                        RecipeCategory.TOOLS,
                        PREItems.ELECTRUM_HOE
                );
                offerInductionSmeltingRecipe(
                        Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE,
                        PREItems.ROSE_GOLD_HELMET,
                        PREConventionalItemTags.ELECTRUM_INGOTS,
                        RecipeCategory.TOOLS,
                        PREItems.ELECTRUM_HELMET
                );
                offerInductionSmeltingRecipe(
                        Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE,
                        PREItems.ROSE_GOLD_CHESTPLATE,
                        PREConventionalItemTags.ELECTRUM_INGOTS,
                        RecipeCategory.TOOLS,
                        PREItems.ELECTRUM_CHESTPLATE
                );
                offerInductionSmeltingRecipe(
                        Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE,
                        PREItems.ROSE_GOLD_LEGGINGS,
                        PREConventionalItemTags.ELECTRUM_INGOTS,
                        RecipeCategory.TOOLS,
                        PREItems.ELECTRUM_LEGGINGS
                );
                offerInductionSmeltingRecipe(
                        Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE,
                        PREItems.ROSE_GOLD_BOOTS,
                        PREConventionalItemTags.ELECTRUM_INGOTS,
                        RecipeCategory.TOOLS,
                        PREItems.ELECTRUM_BOOTS
                );
                //endregion
                //region Diamond Smithing
                offerSmithingTransformRecipe(
                        PREItems.BASIC_UPGRADE_SMITHING_TEMPLATE,
                        Items.IRON_LEGGINGS,
                        Items.DIAMOND,
                        Items.DIAMOND_LEGGINGS,
                        RecipeCategory.TOOLS
                );
                offerSmithingTransformRecipe(
                        PREItems.BASIC_UPGRADE_SMITHING_TEMPLATE,
                        Items.IRON_HELMET,
                        Items.DIAMOND,
                        Items.DIAMOND_HELMET,
                        RecipeCategory.TOOLS
                );
                offerSmithingTransformRecipe(
                        PREItems.BASIC_UPGRADE_SMITHING_TEMPLATE,
                        Items.IRON_CHESTPLATE,
                        Items.DIAMOND,
                        Items.DIAMOND_CHESTPLATE,
                        RecipeCategory.TOOLS
                );
                offerSmithingTransformRecipe(
                        PREItems.BASIC_UPGRADE_SMITHING_TEMPLATE,
                        Items.IRON_BOOTS,
                        Items.DIAMOND,
                        Items.DIAMOND_BOOTS,
                        RecipeCategory.TOOLS
                );
                offerSmithingTransformRecipe(
                        PREItems.BASIC_UPGRADE_SMITHING_TEMPLATE,
                        Items.IRON_SWORD,
                        Items.DIAMOND,
                        Items.DIAMOND_SWORD,
                        RecipeCategory.TOOLS
                );
                offerSmithingTransformRecipe(
                        PREItems.BASIC_UPGRADE_SMITHING_TEMPLATE,
                        Items.IRON_PICKAXE,
                        Items.DIAMOND,
                        Items.DIAMOND_PICKAXE,
                        RecipeCategory.TOOLS
                );
                offerSmithingTransformRecipe(
                        PREItems.BASIC_UPGRADE_SMITHING_TEMPLATE,
                        Items.IRON_SHOVEL,
                        Items.DIAMOND,
                        Items.DIAMOND_SHOVEL,
                        RecipeCategory.TOOLS
                );
                offerSmithingTransformRecipe(
                        PREItems.BASIC_UPGRADE_SMITHING_TEMPLATE,
                        Items.IRON_AXE,
                        Items.DIAMOND,
                        Items.DIAMOND_AXE,
                        RecipeCategory.TOOLS
                );
                offerSmithingTransformRecipe(
                        PREItems.BASIC_UPGRADE_SMITHING_TEMPLATE,
                        Items.IRON_HOE,
                        Items.DIAMOND,
                        Items.DIAMOND_HOE,
                        RecipeCategory.TOOLS
                );
                //endregion
                //region Rose Gold Smithing
                offerSmithingTransformRecipe(
                        PREItems.BASIC_UPGRADE_SMITHING_TEMPLATE,
                        Items.GOLDEN_HELMET,
                        PREConventionalItemTags.ROSE_GOLD_INGOTS,
                        PREItems.ROSE_GOLD_HELMET,
                        RecipeCategory.TOOLS
                );
                offerSmithingTransformRecipe(
                        PREItems.BASIC_UPGRADE_SMITHING_TEMPLATE,
                        Items.GOLDEN_CHESTPLATE,
                        PREConventionalItemTags.ROSE_GOLD_INGOTS,
                        PREItems.ROSE_GOLD_CHESTPLATE,
                        RecipeCategory.TOOLS
                );
                offerSmithingTransformRecipe(
                        PREItems.BASIC_UPGRADE_SMITHING_TEMPLATE,
                        Items.GOLDEN_LEGGINGS,
                        PREConventionalItemTags.ROSE_GOLD_INGOTS,
                        PREItems.ROSE_GOLD_LEGGINGS,
                        RecipeCategory.TOOLS
                );
                offerSmithingTransformRecipe(
                        PREItems.BASIC_UPGRADE_SMITHING_TEMPLATE,
                        Items.GOLDEN_BOOTS,
                        PREConventionalItemTags.ROSE_GOLD_INGOTS,
                        PREItems.ROSE_GOLD_BOOTS,
                        RecipeCategory.TOOLS
                );
                offerSmithingTransformRecipe(
                        PREItems.BASIC_UPGRADE_SMITHING_TEMPLATE,
                        Items.GOLDEN_SWORD,
                        PREConventionalItemTags.ROSE_GOLD_INGOTS,
                        PREItems.ROSE_GOLD_SWORD,
                        RecipeCategory.TOOLS
                );
                offerSmithingTransformRecipe(
                        PREItems.BASIC_UPGRADE_SMITHING_TEMPLATE,
                        Items.GOLDEN_PICKAXE,
                        PREConventionalItemTags.ROSE_GOLD_INGOTS,
                        PREItems.ROSE_GOLD_PICKAXE,
                        RecipeCategory.TOOLS
                );
                offerSmithingTransformRecipe(
                        PREItems.BASIC_UPGRADE_SMITHING_TEMPLATE,
                        Items.GOLDEN_SHOVEL,
                        PREConventionalItemTags.ROSE_GOLD_INGOTS,
                        PREItems.ROSE_GOLD_SHOVEL,
                        RecipeCategory.TOOLS
                );
                offerSmithingTransformRecipe(
                        PREItems.BASIC_UPGRADE_SMITHING_TEMPLATE,
                        Items.GOLDEN_AXE,
                        PREConventionalItemTags.ROSE_GOLD_INGOTS,
                        PREItems.ROSE_GOLD_AXE,
                        RecipeCategory.TOOLS
                );
                offerSmithingTransformRecipe(
                        PREItems.BASIC_UPGRADE_SMITHING_TEMPLATE,
                        Items.GOLDEN_HOE,
                        PREConventionalItemTags.ROSE_GOLD_INGOTS,
                        PREItems.ROSE_GOLD_HOE,
                        RecipeCategory.TOOLS
                );
                //endregion
                //region Brass Smithing
                offerSmithingTransformRecipe(
                        PREItems.BASIC_UPGRADE_SMITHING_TEMPLATE,
                        Items.IRON_HELMET,
                        PREConventionalItemTags.BRASS_INGOTS,
                        PREItems.BRASS_HELMET,
                        RecipeCategory.TOOLS
                );
                offerSmithingTransformRecipe(
                        PREItems.BASIC_UPGRADE_SMITHING_TEMPLATE,
                        Items.IRON_CHESTPLATE,
                        PREConventionalItemTags.BRASS_INGOTS,
                        PREItems.BRASS_CHESTPLATE,
                        RecipeCategory.TOOLS
                );
                offerSmithingTransformRecipe(
                        PREItems.BASIC_UPGRADE_SMITHING_TEMPLATE,
                        Items.IRON_LEGGINGS,
                        PREConventionalItemTags.BRASS_INGOTS,
                        PREItems.BRASS_LEGGINGS,
                        RecipeCategory.TOOLS
                );
                offerSmithingTransformRecipe(
                        PREItems.BASIC_UPGRADE_SMITHING_TEMPLATE,
                        Items.IRON_BOOTS,
                        PREConventionalItemTags.BRASS_INGOTS,
                        PREItems.BRASS_BOOTS,
                        RecipeCategory.TOOLS
                );
                offerSmithingTransformRecipe(
                        PREItems.BASIC_UPGRADE_SMITHING_TEMPLATE,
                        Items.IRON_SWORD,
                        PREConventionalItemTags.BRASS_INGOTS,
                        PREItems.BRASS_SWORD,
                        RecipeCategory.TOOLS
                );
                offerSmithingTransformRecipe(
                        PREItems.BASIC_UPGRADE_SMITHING_TEMPLATE,
                        Items.IRON_PICKAXE,
                        PREConventionalItemTags.BRASS_INGOTS,
                        PREItems.BRASS_PICKAXE,
                        RecipeCategory.TOOLS
                );
                offerSmithingTransformRecipe(
                        PREItems.BASIC_UPGRADE_SMITHING_TEMPLATE,
                        Items.IRON_SHOVEL,
                        PREConventionalItemTags.BRASS_INGOTS,
                        PREItems.BRASS_SHOVEL,
                        RecipeCategory.TOOLS
                );
                offerSmithingTransformRecipe(
                        PREItems.BASIC_UPGRADE_SMITHING_TEMPLATE,
                        Items.IRON_AXE,
                        PREConventionalItemTags.BRASS_INGOTS,
                        PREItems.BRASS_AXE,
                        RecipeCategory.TOOLS
                );
                offerSmithingTransformRecipe(
                        PREItems.BASIC_UPGRADE_SMITHING_TEMPLATE,
                        Items.IRON_HOE,
                        PREConventionalItemTags.BRASS_INGOTS,
                        PREItems.BRASS_HOE,
                        RecipeCategory.TOOLS
                );
                //endregion
                //region Misc Smithing
                offerSmithingTransformRecipe(
                        PREItems.BASIC_UPGRADE_SMITHING_TEMPLATE,
                        PREItems.COPPER_HELMET,
                        Items.TURTLE_SCUTE,
                        Items.TURTLE_HELMET,
                        RecipeCategory.TOOLS
                );
                offerSmithingTransformRecipe(
                        PREItems.BASIC_UPGRADE_SMITHING_TEMPLATE,
                        Items.COMPASS,
                        Items.ECHO_SHARD,
                        Items.RECOVERY_COMPASS,
                        RecipeCategory.TOOLS
                );
                offerSmithingTransformRecipe(
                        Items.RESIN_BRICK,
                        Items.IRON_INGOT,
                        ConventionalItemTags.RESIN_BRICKS,
                        PREItems.BRASS_INGOT,
                        RecipeCategory.MISC
                );

                //region Copper Crafting
                createHelmetRecipe(RecipeCategory.TOOLS, ConventionalItemTags.COPPER_INGOTS, PREItems.COPPER_HELMET);
                createChestplateRecipe(RecipeCategory.TOOLS, ConventionalItemTags.COPPER_INGOTS, PREItems.COPPER_CHESTPLATE);
                createLeggingsRecipe(RecipeCategory.TOOLS, ConventionalItemTags.COPPER_INGOTS, PREItems.COPPER_LEGGINGS);
                createBootsRecipe(RecipeCategory.TOOLS, ConventionalItemTags.COPPER_INGOTS, PREItems.COPPER_BOOTS);
                createSwordRecipe(RecipeCategory.TOOLS, ConventionalItemTags.COPPER_INGOTS, PREItems.COPPER_SWORD);
                createPickaxeRecipe(RecipeCategory.TOOLS, ConventionalItemTags.COPPER_INGOTS, PREItems.COPPER_PICKAXE);
                createAxeRecipe(RecipeCategory.TOOLS, ConventionalItemTags.COPPER_INGOTS, PREItems.COPPER_AXE);
                createShovelRecipe(RecipeCategory.TOOLS, ConventionalItemTags.COPPER_INGOTS, PREItems.COPPER_SHOVEL);
                createHoeRecipe(RecipeCategory.TOOLS, ConventionalItemTags.COPPER_INGOTS, PREItems.COPPER_HOE);
                //endregion
                offerCampfireRecipe(
                        1200,
                        Items.COBBLED_DEEPSLATE,
                        Items.DEEPSLATE
                );
                offerCampfireRecipe(
                        1200,
                        Items.COBBLESTONE,
                        Items.STONE
                );
                offerCampfireRecipe(
                        600,
                        Items.ROTTEN_FLESH,
                        Items.LEATHER
                );
                offerCampfireRecipe(
                        600,
                        Items.OAK_LOG,
                        Items.CHARCOAL
                );
                createShapeless(RecipeCategory.BUILDING_BLOCKS, Items.FLINT, 4)
                        .input(Items.GRAVEL, 2)
                        .criterion("has_gravel", conditionsFromItem(Items.GRAVEL))
                        .offerTo(this.exporter, modRegistryKey("flint"));

                createShapeless(RecipeCategory.MISC, PREItems.ROSE_GOLD_INGOT)
                        .input(Ingredient.fromTag(RecipeUtil.getItemsFromTag(registries, ConventionalItemTags.COPPER_INGOTS)), 4)
                        .input(Ingredient.fromTag(RecipeUtil.getItemsFromTag(registries, ConventionalItemTags.GOLD_INGOTS)), 4)
                        .criterion("has_copper", conditionsFromItem(Items.COPPER_INGOT))
                        .criterion("has_gold", conditionsFromItem(Items.GOLD_INGOT))
                        .offerTo(this.exporter, modRegistryKey("rose_gold_ingot"));

                createShapeless(RecipeCategory.MISC, PREItems.ALUMNABRASS_INGOT)
                        .input(Ingredient.fromTag(RecipeUtil.getItemsFromTag(registries, PREConventionalItemTags.BRASS_INGOTS)), 4)
                        .input(Items.NETHERITE_SCRAP, 4)
                        .criterion("has_brass", conditionsFromItem(PREItems.BRASS_INGOT))
                        .criterion("has_scrap", conditionsFromItem(Items.NETHERITE_SCRAP))
                        .offerTo(this.exporter, modRegistryKey("alumnabrass_ingot"));

                createShapeless(RecipeCategory.MISC, PREItems.ELECTRUM_INGOT)
                        .input(Ingredient.fromTag(RecipeUtil.getItemsFromTag(registries, PREConventionalItemTags.ROSE_GOLD_INGOTS)), 4)
                        .input(Items.NETHERITE_SCRAP, 4)
                        .criterion("has_rose_gold", conditionsFromItem(PREItems.ROSE_GOLD_INGOT))
                        .criterion("has_scrap", conditionsFromItem(Items.NETHERITE_SCRAP))
                        .offerTo(this.exporter, modRegistryKey("electrum_ingot"));

                createShapeless(RecipeCategory.MISC, PREItems.VOCALITE_INGOT)
                        .input(Ingredient.fromTag(RecipeUtil.getItemsFromTag(registries, ConventionalItemTags.DIAMOND_GEMS)), 4)
                        .input(Items.NETHERITE_SCRAP, 4)
                        .criterion("has_diamond", conditionsFromItem(Items.DIAMOND))
                        .criterion("has_scrap", conditionsFromItem(Items.NETHERITE_SCRAP))
                        .offerTo(this.exporter, modRegistryKey("vocalite_ingot"));

                createShaped(
                        RecipeCategory.BUILDING_BLOCKS,
                        PREItems.RECYCLER
                )
                        .pattern("AHA")
                        .pattern("PCP")
                        .pattern("ASA")
                        .input('C', Items.CRAFTER)
                        .input('H', Items.HOPPER)
                        .input('P', Items.PAPER)
                        .input('S', Items.SMOOTH_STONE)
                        .input('A', PREItems.ALUMNABRASS_INGOT)
                        .criterion("has_alumnabrass", conditionsFromItem(PREItems.ALUMNABRASS_INGOT))
                        .criterion("has_paper", conditionsFromItem(Items.PAPER))
                        .offerTo(
                                this.exporter,
                                modRegistryKey("recycler")
                        );
                createShaped(
                        RecipeCategory.MISC,
                        PREItems.BASIC_UPGRADE_SMITHING_TEMPLATE,
                        2
                )
                        .pattern("GBG")
                        .pattern("GDG")
                        .pattern("GGG")
                        .input('B', PREItems.BASIC_UPGRADE_SMITHING_TEMPLATE)
                        .input('G', Items.GOLD_INGOT)
                        .input('D', PREItemTags.BASIC_UPGRADE_SMITHING_TEMPLATE_BLOCKS)
                        .criterion("has_gold_ingot", conditionsFromItem(Items.GOLD_INGOT))
                        .criterion("has_basic_upgrade_template", conditionsFromItem(PREItems.BASIC_UPGRADE_SMITHING_TEMPLATE))
                        .offerTo(
                                this.exporter,
                                modRegistryKey("basic_upgrade_smithing_template")
                        );
                createShaped(
                        RecipeCategory.BUILDING_BLOCKS,
                        PREItems.INDUCTION_FURNACE
                )
                        .pattern("ITI")
                        .pattern("NBN")
                        .pattern("ILI")
                        .input('I', Items.IRON_BLOCK)
                        .input('B', Items.BLAST_FURNACE)
                        .input('N', Items.NETHERITE_SCRAP)
                        .input('L', Items.LAVA_BUCKET)
                        .input('T', Items.IRON_TRAPDOOR)
                        .criterion("has_iron", conditionsFromItem(Items.IRON_INGOT))
                        .criterion("has_netherite_scrap", conditionsFromItem(Items.NETHERITE_SCRAP))
                        .offerTo(
                                this.exporter,
                                modRegistryKey("induction_furnace")
                        );
            }
        };
    }



    @Override
    public String getName() {
        return "ProgressionRe-EnvisionedRecipeProvider";
    }
}
