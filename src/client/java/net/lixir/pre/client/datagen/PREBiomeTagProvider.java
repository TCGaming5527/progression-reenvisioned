package net.lixir.pre.client.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBiomeTags;
import net.lixir.pre.registry.tag.PREBiomeTags;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.world.biome.Biome;

import java.util.concurrent.CompletableFuture;

public class PREBiomeTagProvider extends FabricTagProvider<Biome> {
	public PREBiomeTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
		super(output, RegistryKeys.BIOME, registriesFuture);
	}

	@Override
	protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
		getOrCreateTagBuilder(PREBiomeTags.HAS_WARM_FISHING)
				.addOptionalTags(
						ConventionalBiomeTags.IS_HOT
				);
		getOrCreateTagBuilder(PREBiomeTags.HAS_COLD_FISHING)
				.addOptionalTags(
						ConventionalBiomeTags.IS_COLD
				);
	}
}