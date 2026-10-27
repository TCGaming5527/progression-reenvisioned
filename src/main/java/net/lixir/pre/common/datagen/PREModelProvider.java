package net.lixir.pre.common.datagen;

import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.lixir.pre.common.ProgressionReEnvisioned;
import net.lixir.pre.common.block.InductionFurnaceBlock;
import net.lixir.pre.common.init.PREBlocks;
import net.lixir.pre.common.block.PlatformBlock;
import net.lixir.pre.common.init.PREItems;
import net.minecraft.block.Block;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.client.data.*;
import net.minecraft.client.render.model.json.ModelVariant;
import net.minecraft.client.render.model.json.WeightedVariant;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.Pool;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

import static net.minecraft.client.data.BlockStateModelGenerator.*;

public class PREModelProvider extends FabricModelProvider {
	public static final Model SPEAR_IN_HAND = new Model(Optional.of(ProgressionReEnvisioned.id("item/spear_in_hand")), Optional.empty(), TextureKey.LAYER0);

	public PREModelProvider(FabricDataOutput output) {
		super(output);
	}

	@Override
	public void generateBlockStateModels(BlockStateModelGenerator generator) {
		generateInductionFurnace(generator, PREBlocks.INDUCTION_FURNACE);
		generatePlatform(generator, PREBlocks.PLATFORM);
		generator.registerItemModel(PREBlocks.INDUCTION_FURNACE.asItem(), ProgressionReEnvisioned.id("block/induction_furnace"));
		generator.registerItemModel(PREBlocks.RECYCLER.asItem(), ProgressionReEnvisioned.id("block/recycler"));
	}

	private void generatePlatform(@NotNull BlockStateModelGenerator generator, Block block) {
		var blockId = Registries.BLOCK.getId(block);
		var namespace = blockId.getNamespace();
		var path = blockId.getPath();

		var model = Models.CUBE_ALL.upload(
				block,
				TextureMap.all(block),
				generator.modelCollector
		);
		var blinkingId = Identifier.of(namespace, "block/" + path + "_blinking");
		var blinkingModel = Models.CUBE_ALL.upload(
				blinkingId,
				TextureMap.all(blinkingId),
				generator.modelCollector
		);

		ModelVariant variant = new ModelVariant(model);
		ModelVariant blinkingVariant = new ModelVariant(blinkingModel);

		WeightedVariant weighted = new WeightedVariant(Pool.of(variant));
		WeightedVariant blinkingWeighted = new WeightedVariant(Pool.of(blinkingVariant));

		generator.blockStateCollector.accept(
				VariantsBlockModelDefinitionCreator.of(block)
						.with(
								BlockStateVariantMap.models(PlatformBlock.BLINKING)
										.register(false, weighted)
										.register(true, blinkingWeighted)
						)

		);


	}
	private void generateInductionFurnace(@NotNull BlockStateModelGenerator generator, Block block) {
		String blockName = Registries.BLOCK.getId(block).getPath();

		TextureMap unlitTextures = new TextureMap()
				.put(TextureKey.UP, ProgressionReEnvisioned.id("block/" + blockName + "_top"))
				.put(TextureKey.DOWN, ProgressionReEnvisioned.id("block/" + blockName + "_bottom"))
				.put(TextureKey.NORTH, ProgressionReEnvisioned.id("block/" + blockName + "_front"))
				.put(TextureKey.SOUTH, ProgressionReEnvisioned.id("block/" + blockName + "_side"))
				.put(TextureKey.EAST, ProgressionReEnvisioned.id("block/" + blockName + "_side"))
				.put(TextureKey.WEST, ProgressionReEnvisioned.id("block/" + blockName + "_side"))
				.put(TextureKey.PARTICLE, ProgressionReEnvisioned.id("block/" + blockName + "_top"));

		var unlitModel = Models.CUBE.upload(block, unlitTextures, generator.modelCollector);

		TextureMap litTextures = new TextureMap()
				.put(TextureKey.UP, ProgressionReEnvisioned.id("block/" + blockName + "_top_lit"))
				.put(TextureKey.DOWN, ProgressionReEnvisioned.id("block/" + blockName + "_bottom_lit"))
				.put(TextureKey.NORTH, ProgressionReEnvisioned.id("block/" + blockName + "_front_lit"))
				.put(TextureKey.SOUTH, ProgressionReEnvisioned.id("block/" + blockName + "_side_lit"))
				.put(TextureKey.EAST, ProgressionReEnvisioned.id("block/" + blockName + "_side_lit"))
				.put(TextureKey.WEST, ProgressionReEnvisioned.id("block/" + blockName + "_side_lit"))
				.put(TextureKey.PARTICLE, ProgressionReEnvisioned.id("block/" + blockName + "_top_lit"));

		var litModel = Models.CUBE.upload(ProgressionReEnvisioned.id("block/" + blockName + "_lit"), litTextures, generator.modelCollector);

		ModelVariant litVariant = new ModelVariant(litModel);
		ModelVariant unlitVariant = new ModelVariant(unlitModel);

		WeightedVariant litWeighted = new WeightedVariant(Pool.of(litVariant));
		WeightedVariant unlitWeighted = new WeightedVariant(Pool.of(unlitVariant));

		generator.blockStateCollector.accept(
				VariantsBlockModelDefinitionCreator.of(block)
						.with(
								BlockStateVariantMap.models(HorizontalFacingBlock.FACING, InductionFurnaceBlock.LIT)
										.register(Direction.NORTH, false, unlitWeighted)
										.register(Direction.EAST, false, unlitWeighted.apply(ROTATE_Y_90))
										.register(Direction.SOUTH, false, unlitWeighted.apply(ROTATE_Y_180))
										.register(Direction.WEST, false, unlitWeighted.apply(ROTATE_Y_270))
										.register(Direction.NORTH, true, litWeighted)
										.register(Direction.EAST, true, litWeighted.apply(ROTATE_Y_90))
										.register(Direction.SOUTH, true, litWeighted.apply(ROTATE_Y_180))
										.register(Direction.WEST, true, litWeighted.apply(ROTATE_Y_270))
						)

		);


	}


	@Override
	public void generateItemModels(ItemModelGenerator itemModelGenerator) {
		itemModelGenerator.register(PREItems.BASIC_UPGRADE_SMITHING_TEMPLATE, Models.GENERATED);

		//region Copper
		itemModelGenerator.register(PREItems.COPPER_SWORD, Models.HANDHELD);
		itemModelGenerator.register(PREItems.COPPER_PICKAXE, Models.HANDHELD);
		itemModelGenerator.register(PREItems.COPPER_AXE, Models.HANDHELD);
		itemModelGenerator.register(PREItems.COPPER_SHOVEL, Models.HANDHELD);
		itemModelGenerator.register(PREItems.COPPER_HOE, Models.HANDHELD);

		itemModelGenerator.register(PREItems.COPPER_HELMET, Models.GENERATED);
		itemModelGenerator.register(PREItems.COPPER_CHESTPLATE, Models.GENERATED);
		itemModelGenerator.register(PREItems.COPPER_LEGGINGS, Models.GENERATED);
		itemModelGenerator.register(PREItems.COPPER_BOOTS, Models.GENERATED);
		//region Electrum
		itemModelGenerator.register(PREItems.ELECTRUM_INGOT, Models.GENERATED);
		itemModelGenerator.register(PREItems.ELECTRUM_SWORD, Models.HANDHELD);
		itemModelGenerator.register(PREItems.ELECTRUM_PICKAXE, Models.HANDHELD);
		itemModelGenerator.register(PREItems.ELECTRUM_AXE, Models.HANDHELD);
		itemModelGenerator.register(PREItems.ELECTRUM_SHOVEL, Models.HANDHELD);
		itemModelGenerator.register(PREItems.ELECTRUM_HOE, Models.HANDHELD);

		itemModelGenerator.register(PREItems.ELECTRUM_HELMET, Models.GENERATED);
		itemModelGenerator.register(PREItems.ELECTRUM_CHESTPLATE, Models.GENERATED);
		itemModelGenerator.register(PREItems.ELECTRUM_LEGGINGS, Models.GENERATED);
		itemModelGenerator.register(PREItems.ELECTRUM_BOOTS, Models.GENERATED);

		registerSpear(itemModelGenerator, PREItems.ELECTRUM_SPEAR);
		//endregion
		//region Brass
		itemModelGenerator.register(PREItems.BRASS_INGOT, Models.GENERATED);
		itemModelGenerator.register(PREItems.BRASS_SWORD, Models.HANDHELD);
		itemModelGenerator.register(PREItems.BRASS_PICKAXE, Models.HANDHELD);
		itemModelGenerator.register(PREItems.BRASS_AXE, Models.HANDHELD);
		itemModelGenerator.register(PREItems.BRASS_SHOVEL, Models.HANDHELD);
		itemModelGenerator.register(PREItems.BRASS_HOE, Models.HANDHELD);

		itemModelGenerator.register(PREItems.BRASS_HELMET, Models.GENERATED);
		itemModelGenerator.register(PREItems.BRASS_CHESTPLATE, Models.GENERATED);
		itemModelGenerator.register(PREItems.BRASS_LEGGINGS, Models.GENERATED);
		itemModelGenerator.register(PREItems.BRASS_BOOTS, Models.GENERATED);
		//endregion
		//region Rose Gold
		itemModelGenerator.register(PREItems.ROSE_GOLD_INGOT, Models.GENERATED);
		itemModelGenerator.register(PREItems.ROSE_GOLD_SWORD, Models.HANDHELD);
		itemModelGenerator.register(PREItems.ROSE_GOLD_PICKAXE, Models.HANDHELD);
		itemModelGenerator.register(PREItems.ROSE_GOLD_AXE, Models.HANDHELD);
		itemModelGenerator.register(PREItems.ROSE_GOLD_SHOVEL, Models.HANDHELD);
		itemModelGenerator.register(PREItems.ROSE_GOLD_HOE, Models.HANDHELD);

		itemModelGenerator.register(PREItems.ROSE_GOLD_HELMET, Models.GENERATED);
		itemModelGenerator.register(PREItems.ROSE_GOLD_CHESTPLATE, Models.GENERATED);
		itemModelGenerator.register(PREItems.ROSE_GOLD_LEGGINGS, Models.GENERATED);
		itemModelGenerator.register(PREItems.ROSE_GOLD_BOOTS, Models.GENERATED);
		//endregion
		//region Alumnabrass
		itemModelGenerator.register(PREItems.ALUMNABRASS_INGOT, Models.GENERATED);
		itemModelGenerator.register(PREItems.ALUMNABRASS_SWORD, Models.HANDHELD);
		itemModelGenerator.register(PREItems.ALUMNABRASS_PICKAXE, Models.HANDHELD);
		itemModelGenerator.register(PREItems.ALUMNABRASS_AXE, Models.HANDHELD);
		itemModelGenerator.register(PREItems.ALUMNABRASS_SHOVEL, Models.HANDHELD);
		itemModelGenerator.register(PREItems.ALUMNABRASS_HOE, Models.HANDHELD);

		itemModelGenerator.register(PREItems.ALUMNABRASS_HELMET, Models.GENERATED);
		itemModelGenerator.register(PREItems.ALUMNABRASS_CHESTPLATE, Models.GENERATED);
		itemModelGenerator.register(PREItems.ALUMNABRASS_LEGGINGS, Models.GENERATED);
		itemModelGenerator.register(PREItems.ALUMNABRASS_BOOTS, Models.GENERATED);

		registerSpear(itemModelGenerator, PREItems.ALUMNABRASS_SPEAR);
		//endregion
		//region Vocalite
		itemModelGenerator.register(PREItems.VOCALITE_INGOT, Models.HANDHELD);
		itemModelGenerator.register(PREItems.VOCALITE_SWORD, Models.HANDHELD);
		itemModelGenerator.register(PREItems.VOCALITE_PICKAXE, Models.HANDHELD);
		itemModelGenerator.register(PREItems.VOCALITE_AXE, Models.HANDHELD);
		itemModelGenerator.register(PREItems.VOCALITE_SHOVEL, Models.HANDHELD);
		itemModelGenerator.register(PREItems.VOCALITE_HOE, Models.HANDHELD);

		itemModelGenerator.register(PREItems.VOCALITE_HELMET, Models.GENERATED);
		itemModelGenerator.register(PREItems.VOCALITE_CHESTPLATE, Models.GENERATED);
		itemModelGenerator.register(PREItems.VOCALITE_LEGGINGS, Models.GENERATED);
		itemModelGenerator.register(PREItems.VOCALITE_BOOTS, Models.GENERATED);

		registerSpear(itemModelGenerator, PREItems.VOCALITE_SPEAR);
		//endregion

		registerSpear(itemModelGenerator, PREItems.NETHERITE_SPEAR);
	}

	public static void registerSpear(ItemModelGenerator gen, Item item) {
		Identifier base = ModelIds.getItemModelId(item);
		Identifier inHand = base.withSuffixedPath("_in_hand");

		gen.output.accept(item, ItemModelGenerator.createModelWithInHandVariant(
				ItemModels.basic(Models.GENERATED.upload(base, TextureMap.layer0(base), gen.modelCollector)),
				ItemModels.basic(SPEAR_IN_HAND.upload(inHand, TextureMap.layer0(inHand), gen.modelCollector))
		));
	}

	public static void registerWithVanillaNamespace(ItemModelGenerator gen, Item item, Model model) {
		gen.output.accept(item, ItemModels.basic(model.upload(
				ModelIds.getItemModelId(item),
				TextureMap.layer0(Identifier.ofVanilla(ModelIds.getItemModelId(item).getPath())), gen.modelCollector
		)));
	}


}