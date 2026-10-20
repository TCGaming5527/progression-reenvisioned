package net.lixir.pre.common.datagen.tag;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.lixir.pre.common.init.tag.PREEntityTypeTags;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.EntityTypeTags;

import java.util.concurrent.CompletableFuture;

public class PREEntityTypeTagProvider extends FabricTagProvider<EntityType<?>> {
	public PREEntityTypeTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
		super(output, RegistryKeys.ENTITY_TYPE, registriesFuture);
	}

	@Override
	protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
		getOrCreateTagBuilder(PREEntityTypeTags.SENSITIVE_TO_UNDEAD_DAMAGE)
				.addOptionalTags(
						EntityTypeTags.SENSITIVE_TO_SMITE
				);
		getOrCreateTagBuilder(PREEntityTypeTags.SENSITIVE_TO_NETHER_DAMAGE)
				.add(
						EntityType.BLAZE,
						EntityType.PIGLIN,
						EntityType.HOGLIN,
						EntityType.ZOGLIN,
						EntityType.ZOMBIFIED_PIGLIN,
						EntityType.MAGMA_CUBE,
						EntityType.GHAST,
						EntityType.WITHER,
						EntityType.WITHER_SKELETON,
						EntityType.STRIDER
				);
		getOrCreateTagBuilder(PREEntityTypeTags.SENSITIVE_TO_ENDER_DAMAGE)
				.add(
						EntityType.ENDER_DRAGON,
						EntityType.ENDERMAN,
						EntityType.ENDERMITE,
						EntityType.SHULKER,
						EntityType.SHULKER_BULLET
				);
		getOrCreateTagBuilder(PREEntityTypeTags.SENSITIVE_TO_WILD_DAMAGE)
				.add(
						EntityType.PANDA,
						EntityType.DOLPHIN,
						EntityType.BEE,
						EntityType.POLAR_BEAR,
						EntityType.GOAT
				)
				.addOptionalTags(
						EntityTypeTags.SENSITIVE_TO_BANE_OF_ARTHROPODS,
						EntityTypeTags.SENSITIVE_TO_IMPALING,
						EntityTypeTags.ILLAGER
				);

	}
}