package net.lixir.pre.common.datagen.tag;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.lixir.pre.common.init.tag.PREDamageTypeTags;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class PREDamageTypeTagProvider extends FabricTagProvider<DamageType> {
	public PREDamageTypeTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
		super(output, RegistryKeys.DAMAGE_TYPE, registriesFuture);
	}

	@Override
	protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
		getOrCreateTagBuilder(PREDamageTypeTags.MAGIC_PROTECTION_PROTECTS)
				.add(
						DamageTypes.MAGIC,
						DamageTypes.INDIRECT_MAGIC,
						DamageTypes.WITHER,
						DamageTypes.DRAGON_BREATH,
						DamageTypes.MAGIC
				);
		getOrCreateTagBuilder(PREDamageTypeTags.PHYSICAL_PROTECTION_PROTECTS)
				.add(
						DamageTypes.MACE_SMASH,
						DamageTypes.MOB_ATTACK,
						DamageTypes.PLAYER_ATTACK,
						DamageTypes.MOB_ATTACK_NO_AGGRO,
						DamageTypes.STING
				);
		getOrCreateTagBuilder(PREDamageTypeTags.EXPLOSION_PROTECTION_PROTECTS)
				.add(
						DamageTypes.EXPLOSION,
						DamageTypes.PLAYER_EXPLOSION,
						DamageTypes.FIREWORKS,
						DamageTypes.FIREBALL,
						DamageTypes.UNATTRIBUTED_FIREBALL,
						DamageTypes.SONIC_BOOM,
						DamageTypes.BAD_RESPAWN_POINT
				);
		getOrCreateTagBuilder(PREDamageTypeTags.FIRE_PROTECTION_PROTECTS)
				.add(
						DamageTypes.FIREBALL,
						DamageTypes.IN_FIRE,
						DamageTypes.ON_FIRE,
						DamageTypes.CAMPFIRE,
						DamageTypes.LAVA,
						DamageTypes.HOT_FLOOR
				);
		getOrCreateTagBuilder(PREDamageTypeTags.PROJECTILE_PROTECTION_PROTECTS)
				.add(
						DamageTypes.MOB_PROJECTILE,
						DamageTypes.WIND_CHARGE,
						DamageTypes.ARROW,
						DamageTypes.ENDER_PEARL,
						DamageTypes.TRIDENT,
						DamageTypes.WITHER_SKULL,
						DamageTypes.THROWN
				);

	}
}