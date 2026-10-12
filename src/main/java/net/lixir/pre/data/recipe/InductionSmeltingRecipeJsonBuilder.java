package net.lixir.pre.data.recipe;

import net.lixir.pre.recipe.InductionSmeltingRecipe;
import net.minecraft.advancement.*;
import net.minecraft.advancement.criterion.RecipeUnlockedCriterion;
import net.minecraft.data.recipe.RecipeExporter;
import net.minecraft.item.Item;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.TransmuteRecipeResult;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public class InductionSmeltingRecipeJsonBuilder {
    private final Optional<Ingredient> template;
    private final Ingredient base;
    private final Optional<Ingredient> addition;
    private final int additionCount;
    private final int smeltTicks;
    private final Item result;
    private final RecipeCategory category;

    private final Map<String, AdvancementCriterion<?>> criteria = new LinkedHashMap<>();

    private InductionSmeltingRecipeJsonBuilder(
            Optional<Ingredient> template,
            Ingredient base,
            Optional<Ingredient> addition,
            int additionCount,
            int smeltTicks,
            RecipeCategory category,
            Item result
    ) {
        this.template = template;
        this.base = base;
        this.addition = addition;
        this.additionCount = additionCount;
        this.smeltTicks = smeltTicks;
        this.category = category;
        this.result = result;
    }

    public static InductionSmeltingRecipeJsonBuilder create(
            Ingredient template,
            Ingredient base,
            Ingredient addition,
            int additionCount,
            int smeltTicks,
            RecipeCategory category,
            Item result
    ) {
        return new InductionSmeltingRecipeJsonBuilder(
                Optional.of(template),
                base,
                Optional.of(addition),
                additionCount,
                smeltTicks,
                category,
                result
        );
    }

    public static InductionSmeltingRecipeJsonBuilder create(
            Ingredient base,
            int smeltTicks,
            RecipeCategory category,
            Item result
    ) {
        return new InductionSmeltingRecipeJsonBuilder(
                Optional.empty(),
                base,
                Optional.empty(),
                1,
                smeltTicks,
                category,
                result
        );
    }

    public InductionSmeltingRecipeJsonBuilder criterion(String name, AdvancementCriterion<?> criterion) {
        this.criteria.put(name, criterion);
        return this;
    }

    public void offerTo(RecipeExporter exporter, Identifier id) {
        this.offerTo(exporter, RegistryKey.of(RegistryKeys.RECIPE, id));
    }

    public void offerTo(RecipeExporter exporter, RegistryKey<Recipe<?>> recipeKey) {
        validate(recipeKey);

        Advancement.Builder advancement = exporter.getAdvancementBuilder()
                .criterion("has_the_recipe", RecipeUnlockedCriterion.create(recipeKey))
                .rewards(AdvancementRewards.Builder.recipe(recipeKey))
                .criteriaMerger(AdvancementRequirements.CriterionMerger.OR);

        this.criteria.forEach(advancement::criterion);

        InductionSmeltingRecipe recipe = new InductionSmeltingRecipe(
                template,
                base,
                addition,
                additionCount,
                smeltTicks,
                new TransmuteRecipeResult(result)
        );

        exporter.accept(
                recipeKey,
                recipe,
                advancement.build(
                        recipeKey.getValue().withPrefixedPath("recipes/" + category.getName() + "/")
                )
        );
    }

    private void validate(RegistryKey<Recipe<?>> recipeKey) {
        if (this.criteria.isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + recipeKey.getValue());
        }
    }
}
