package net.lixir.pre.common.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.lixir.pre.common.init.PRERecipeSerializers;
import net.lixir.pre.common.init.PRERecipeTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.recipe.*;
import net.minecraft.recipe.book.RecipeBookCategories;
import net.minecraft.recipe.book.RecipeBookCategory;
import net.minecraft.recipe.display.RecipeDisplay;
import net.minecraft.recipe.display.SlotDisplay;
import net.minecraft.recipe.display.SmithingRecipeDisplay;
import net.minecraft.recipe.input.SmithingRecipeInput;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class InductionSmeltingRecipe implements Recipe<SmithingRecipeInput> {
    final Optional<Ingredient> template;
    final Ingredient base;
    final Optional<Ingredient> addition;
    final int additionCount;
    final int smeltTicks;
    final TransmuteRecipeResult result;

    @Nullable
    private IngredientPlacement ingredientPlacement;

    public InductionSmeltingRecipe(
            Optional<Ingredient> template,
            Ingredient base,
            Optional<Ingredient> addition,
            int additionCount,
            int smeltTicks,
            TransmuteRecipeResult result
    ) {
        this.template = template;
        this.base = base;
        this.addition = addition;
        this.additionCount = additionCount;
        this.smeltTicks = smeltTicks;
        this.result = result;
    }

    public ItemStack craft(@NotNull SmithingRecipeInput smithingRecipeInput, RegistryWrapper.WrapperLookup wrapperLookup) {
        return this.result.apply(smithingRecipeInput.base());
    }

    public Optional<Ingredient> getTemplate() {
        return this.template;
    }

    public Ingredient getBase() {
        return this.base;
    }

    public Optional<Ingredient> getAddition() {
        return this.addition;
    }

    public int additionCount() {
        return additionCount;
    }

    public int smeltTicks() {
        return smeltTicks;
    }

    @Override
    public RecipeSerializer<InductionSmeltingRecipe> getSerializer() {
        return PRERecipeSerializers.INDUCTION_SMELTING;
    }

    @Override
    public RecipeType<? extends Recipe<SmithingRecipeInput>> getType() {
        return PRERecipeTypes.INDUCTION_SMELTING;
    }

    @Override
    public IngredientPlacement getIngredientPlacement() {
        if (this.ingredientPlacement == null) {
            this.ingredientPlacement = IngredientPlacement.forMultipleSlots(List.of(this.template, Optional.of(this.base), this.addition));
        }

        return this.ingredientPlacement;
    }

    @Override
    public List<RecipeDisplay> getDisplays() {
        return List.of(
                new SmithingRecipeDisplay(
                        Ingredient.toDisplay(this.template),
                        this.base.toDisplay(),
                        Ingredient.toDisplay(this.addition),
                        this.result.createSlotDisplay(),
                        new SlotDisplay.ItemSlotDisplay(Items.SMITHING_TABLE)
                )
        );
    }

    public static class Serializer
            implements RecipeSerializer<InductionSmeltingRecipe> {

        public static final PacketCodec<RegistryByteBuf, InductionSmeltingRecipe> PACKET_CODEC =
                PacketCodec.tuple(
                        Ingredient.OPTIONAL_PACKET_CODEC,
                        r -> r.template,

                        Ingredient.PACKET_CODEC,
                        r -> r.base,

                        Ingredient.OPTIONAL_PACKET_CODEC,
                        r -> r.addition,

                        PacketCodecs.INTEGER,
                        r -> r.additionCount,

                        PacketCodecs.INTEGER,
                        r -> r.smeltTicks,

                        TransmuteRecipeResult.PACKET_CODEC,
                        r -> r.result,

                        InductionSmeltingRecipe::new
                );
        private static final MapCodec<InductionSmeltingRecipe> CODEC =
                RecordCodecBuilder.mapCodec(instance -> instance.group(
                        Ingredient.CODEC.optionalFieldOf("template")
                                .forGetter(r -> r.template),

                        Ingredient.CODEC.fieldOf("base")
                                .forGetter(r -> r.base),

                        Ingredient.CODEC.optionalFieldOf("addition")
                                .forGetter(r -> r.addition),

                        Codec.INT.optionalFieldOf("addition_count", 1)
                                .forGetter(r -> r.additionCount),

                        Codec.INT.fieldOf("smelt_ticks")
                                .forGetter(r -> r.smeltTicks),

                        TransmuteRecipeResult.CODEC.fieldOf("result")
                                .forGetter(r -> r.result)
                ).apply(instance, InductionSmeltingRecipe::new));

        @Override
        public MapCodec<InductionSmeltingRecipe> codec() {
            return CODEC;
        }

        @Override
        public PacketCodec<RegistryByteBuf, InductionSmeltingRecipe> packetCodec() {
            return PACKET_CODEC;
        }
    }
    @Override
    public boolean matches(@NotNull SmithingRecipeInput input, World world) {
        boolean matches = template.isEmpty() || template.get().test(input.template());
        if (!base.test(input.base())) {
            matches = false;
        }

        if (addition.isPresent()) {
            ItemStack additionStack = input.addition();
            if (!addition.get().test(additionStack)) {
                matches = false;
            } else if (additionStack.getCount() < additionCount) {
                matches = false;
            }
        }

        return matches;
    }

    @Override
    public RecipeBookCategory getRecipeBookCategory() {
        return RecipeBookCategories.BLAST_FURNACE_BLOCKS;
    }
}
