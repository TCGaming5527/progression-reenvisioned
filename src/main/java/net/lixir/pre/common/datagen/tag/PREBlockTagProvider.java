package net.lixir.pre.common.datagen.tag;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBlockTags;
import net.lixir.pre.common.init.PREBlocks;
import net.lixir.pre.common.init.tag.PREBlockTags;
import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;

import java.util.concurrent.CompletableFuture;

public class PREBlockTagProvider extends FabricTagProvider<Block> {
	public PREBlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
		super(output, RegistryKeys.BLOCK, registriesFuture);
	}

	@Override
	protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
		getOrCreateTagBuilder(PREBlockTags.MINEABLE_ANY_PICKAXE)
				.addOptionalTags(
						BlockTags.GOLD_ORES,
						ConventionalBlockTags.GOLD_ORES,
						ConventionalBlockTags.STORAGE_BLOCKS_GOLD,
						ConventionalBlockTags.STORAGE_BLOCKS_RAW_GOLD
				);
		getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE)
				.add(
						PREBlocks.INDUCTION_FURNACE,
						PREBlocks.RECYCLER
				);
	}
}