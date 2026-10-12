package net.lixir.pre.recipe;

import net.lixir.pre.ProgressionReEnvisioned;
import net.minecraft.recipe.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class PRERecipeTypes {
    public static final RecipeType<InductionSmeltingRecipe> INDUCTION_SMELTING = register("induction_smelting");

    static <T extends Recipe<?>> RecipeType<T> register(String id) {
        return Registry.register(Registries.RECIPE_TYPE, ProgressionReEnvisioned.id(id), new RecipeType<T>() {
            public String toString() {
                return id;
            }
        });
    }

    public static void initialize() {

    }
}
