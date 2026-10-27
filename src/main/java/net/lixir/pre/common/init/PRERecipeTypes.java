package net.lixir.pre.common.init;

import net.lixir.pre.common.ProgressionReEnvisioned;
import net.lixir.pre.common.recipe.InductionSmeltingRecipe;
import net.minecraft.recipe.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public interface PRERecipeTypes {
    RecipeType<InductionSmeltingRecipe> INDUCTION_SMELTING = register("induction_smelting");

    static <T extends Recipe<?>> RecipeType<T> register(String id) {
        return Registry.register(Registries.RECIPE_TYPE, ProgressionReEnvisioned.id(id), new RecipeType<T>() {
            public String toString() {
                return id;
            }
        });
    }

    static void initialize() {

    }
}
