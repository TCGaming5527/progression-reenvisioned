package net.lixir.pre.common.init;

import net.lixir.pre.common.ProgressionReEnvisioned;
import net.lixir.pre.common.recipe.InductionSmeltingRecipe;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class PRERecipeSerializers {
    public static final RecipeSerializer<InductionSmeltingRecipe> INDUCTION_SMELTING = register("induction_smelting", new InductionSmeltingRecipe.Serializer());

    static <S extends RecipeSerializer<T>, T extends Recipe<?>> S register(String id, S serializer) {
        return Registry.register(Registries.RECIPE_SERIALIZER, ProgressionReEnvisioned.id(id), serializer);
    }

    public static void initialize() {

    }
}
