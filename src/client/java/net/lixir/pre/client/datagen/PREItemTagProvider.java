package net.lixir.pre.client.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.lixir.pre.item.PREItems;
import net.lixir.pre.registry.tag.PREConventionalItemTags;
import net.lixir.pre.registry.tag.PREItemTags;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.Identifier;

import java.util.concurrent.CompletableFuture;

public class PREItemTagProvider extends FabricTagProvider<Item> {
	public PREItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
		super(output, RegistryKeys.ITEM, registriesFuture);
	}

	@Override
	protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
		getOrCreateTagBuilder(PREConventionalItemTags.BRASS_INGOTS)
				.add(
						PREItems.BRASS_INGOT
				);
		getOrCreateTagBuilder(PREConventionalItemTags.ELECTRUM_INGOTS)
				.add(
						PREItems.ELECTRUM_INGOT
				);
		getOrCreateTagBuilder(PREConventionalItemTags.ROSE_GOLD_INGOTS)
				.add(
						PREItems.ROSE_GOLD_INGOT
				);
		getOrCreateTagBuilder(PREConventionalItemTags.VOCALITE_INGOTS)
				.add(
						PREItems.VOCALITE_INGOT
				);
		getOrCreateTagBuilder(PREConventionalItemTags.ALUMNABRASS_INGOTS)
				.add(
						PREItems.ALUMNABRASS_INGOT
				);
		getOrCreateTagBuilder(ItemTags.CHEST_ARMOR)
				.add(
						PREItems.ALUMNABRASS_CHESTPLATE,
						PREItems.VOCALITE_CHESTPLATE,
						PREItems.ROSE_GOLD_CHESTPLATE,
						PREItems.BRASS_CHESTPLATE,
						PREItems.ELECTRUM_CHESTPLATE,
						PREItems.VOCALITE_CHESTPLATE,
						PREItems.COPPER_CHESTPLATE
				);
		getOrCreateTagBuilder(ItemTags.FOOT_ARMOR)
				.add(
						PREItems.ALUMNABRASS_BOOTS,
						PREItems.VOCALITE_BOOTS,
						PREItems.BRASS_BOOTS,
						PREItems.ROSE_GOLD_BOOTS,
						PREItems.ELECTRUM_BOOTS,
						PREItems.VOCALITE_BOOTS,
						PREItems.COPPER_BOOTS
				);
		getOrCreateTagBuilder(ItemTags.LEG_ARMOR)
				.add(
						PREItems.ALUMNABRASS_LEGGINGS,
						PREItems.VOCALITE_LEGGINGS,
						PREItems.BRASS_LEGGINGS,
						PREItems.ELECTRUM_LEGGINGS,
						PREItems.ROSE_GOLD_LEGGINGS,
						PREItems.VOCALITE_LEGGINGS,
						PREItems.COPPER_LEGGINGS
				);
		getOrCreateTagBuilder(ItemTags.HEAD_ARMOR)
				.add(
						PREItems.ALUMNABRASS_HELMET,
						PREItems.VOCALITE_HELMET,
						PREItems.ROSE_GOLD_HELMET,
						PREItems.BRASS_HELMET,
						PREItems.ELECTRUM_HELMET,
						PREItems.VOCALITE_HELMET,
						PREItems.COPPER_HELMET
				);
		getOrCreateTagBuilder(ItemTags.PICKAXES)
				.add(
						PREItems.ALUMNABRASS_PICKAXE,
						PREItems.VOCALITE_PICKAXE,
						PREItems.ROSE_GOLD_PICKAXE,
						PREItems.BRASS_PICKAXE,
						PREItems.ELECTRUM_PICKAXE,
						PREItems.VOCALITE_PICKAXE,
						PREItems.COPPER_PICKAXE
				);
		getOrCreateTagBuilder(ItemTags.AXES)
				.add(
						PREItems.ALUMNABRASS_AXE,
						PREItems.VOCALITE_AXE,
						PREItems.BRASS_AXE,
						PREItems.ROSE_GOLD_AXE,
						PREItems.ELECTRUM_AXE,
						PREItems.VOCALITE_AXE,
						PREItems.COPPER_AXE
				);
		getOrCreateTagBuilder(ItemTags.SHOVELS)
				.add(
						PREItems.ALUMNABRASS_SHOVEL,
						PREItems.VOCALITE_SHOVEL,
						PREItems.BRASS_SHOVEL,
						PREItems.ROSE_GOLD_SHOVEL,
						PREItems.ELECTRUM_SHOVEL,
						PREItems.VOCALITE_SHOVEL,
						PREItems.COPPER_SHOVEL
				);
		getOrCreateTagBuilder(ItemTags.SWORDS)
				.add(
						PREItems.ALUMNABRASS_SWORD,
						PREItems.VOCALITE_SWORD,
						PREItems.ROSE_GOLD_SWORD,
						PREItems.BRASS_SWORD,
						PREItems.ELECTRUM_SWORD,
						PREItems.VOCALITE_SWORD,
						PREItems.COPPER_SWORD
				);
		getOrCreateTagBuilder(ItemTags.HOES)
				.add(
						PREItems.ALUMNABRASS_HOE,
						PREItems.VOCALITE_HOE,
						PREItems.BRASS_HOE,
						PREItems.ROSE_GOLD_HOE,
						PREItems.ELECTRUM_HOE,
						PREItems.VOCALITE_HOE,
						PREItems.COPPER_HOE
				);
		getOrCreateTagBuilder(PREItemTags.INDUCTION_FURNACE_UPGRADE)
				.add(
						Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE
				);
		getOrCreateTagBuilder(PREItemTags.REPAIRS_COPPER_ARMOR)
				.addOptionalTags(
						ConventionalItemTags.COPPER_INGOTS
				);
		getOrCreateTagBuilder(PREItemTags.REPAIRS_VOCALITE_ARMOR)
				.addOptionalTags(
						PREConventionalItemTags.VOCALITE_INGOTS
				);
		getOrCreateTagBuilder(PREItemTags.REPAIRS_ELECTRUM_ARMOR)
				.addOptionalTags(
						PREConventionalItemTags.ELECTRUM_INGOTS
				);
		getOrCreateTagBuilder(PREItemTags.REPAIRS_BRASS_ARMOR)
				.addOptionalTags(
						PREConventionalItemTags.BRASS_INGOTS
				);

		// Have to add this since they did not make a tag
		getOrCreateTagBuilder(PREConventionalItemTags.VOCALITE_INGOTS)
				.addOptional(
						Identifier.of("miku-discs", "vocalite_ingot")
				);
		getOrCreateTagBuilder(PREItemTags.VOCALITE_TOOL_MATERIALS)
				.addOptionalTags(
						PREConventionalItemTags.VOCALITE_INGOTS
				);
		getOrCreateTagBuilder(PREItemTags.REPAIRS_VOCALITE_ARMOR)
				.addOptionalTags(
						PREConventionalItemTags.VOCALITE_INGOTS
				);
		getOrCreateTagBuilder(PREItemTags.REPAIRS_ROSE_GOLD_ARMOR)
				.addOptionalTags(
						PREConventionalItemTags.ROSE_GOLD_INGOTS
				);
		getOrCreateTagBuilder(PREItemTags.REPAIRS_ALUMNABRASS_ARMOR)
				.addOptionalTags(
						PREConventionalItemTags.ALUMNABRASS_INGOTS
				);
		getOrCreateTagBuilder(PREItemTags.COPPER_TOOL_MATERIALS)
				.addOptionalTags(
						ConventionalItemTags.COPPER_INGOTS
				);
		getOrCreateTagBuilder(PREItemTags.ALUMNABRASS_TOOL_MATERIALS)
				.addOptionalTags(
						PREConventionalItemTags.ALUMNABRASS_INGOTS
				);
		getOrCreateTagBuilder(PREItemTags.INDUCTION_SMELTING_BASE)
				.add(
						Items.HEAVY_CORE
				);
		getOrCreateTagBuilder(PREItemTags.SPEARS)
				.add(
						PREItems.ELECTRUM_SPEAR,
						PREItems.NETHERITE_SPEAR,
						PREItems.ALUMNABRASS_SPEAR,
						PREItems.VOCALITE_SPEAR
				);
		getOrCreateTagBuilder(PREItemTags.SPEAR_ENCHANTABLE)
				.addOptionalTags(
						PREItemTags.SPEARS
				);
		getOrCreateTagBuilder(PREItemTags.BRASS_TOOL_MATERIALS)
				.addOptionalTags(
						PREConventionalItemTags.BRASS_INGOTS
				);
		getOrCreateTagBuilder(PREItemTags.ELECTRUM_TOOL_MATERIALS)
				.addOptionalTags(
						PREConventionalItemTags.ELECTRUM_INGOTS
				);
		getOrCreateTagBuilder(PREItemTags.ROSE_GOLD_TOOL_MATERIALS)
				.addOptionalTags(
						PREConventionalItemTags.ROSE_GOLD_INGOTS
				);
		getOrCreateTagBuilder(ItemTags.FIRE_ASPECT_ENCHANTABLE)
				.add(
						Items.FLINT_AND_STEEL
				);
		getOrCreateTagBuilder(PREItemTags.BASIC_UPGRADE_SMITHING_TEMPLATE_BLOCKS)
				.add(
						Items.DEEPSLATE,
						Items.COBBLED_DEEPSLATE
				);
		getOrCreateTagBuilder(PREItemTags.BANNED)
				.add(
						Items.STONE_PICKAXE,
						Items.STONE_SWORD,
						Items.STONE_AXE,
						Items.STONE_SHOVEL,
						Items.STONE_HOE
				);
	}
}