package net.lixir.pre.client.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.lixir.pre.ProgressionReEnvisioned;
import net.lixir.pre.block.PREBlocks;
import net.lixir.pre.entity.attribute.PREEntityAttributes;
import net.lixir.pre.item.PREItems;
import net.minecraft.registry.RegistryWrapper;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class PREEnglishLangProvider extends FabricLanguageProvider {
	public PREEnglishLangProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
		super(dataOutput, "en_us", registryLookup);
	}

	@Override
	public void generateTranslations(RegistryWrapper.WrapperLookup wrapperLookup, @NotNull TranslationBuilder translationBuilder) {
		translationBuilder.add(PREItems.BASIC_UPGRADE_SMITHING_TEMPLATE, "Basic Upgrade Template");
		translationBuilder.add(PREItems.ALUMNABRASS_BRUSH, "Alumnabrass Brush");
		translationBuilder.add(PREItems.TRANSENDER, "Transender");
		translationBuilder.add(PREItems.OLD_CLOCK, "Old Clock");
		translationBuilder.add(PREItems.NETHERITE_SPEAR, "Netherite Spear");
		//region Alumnabrass
		translationBuilder.add(PREItems.ALUMNABRASS_SPEAR, "Alumnabrass Spear");
		translationBuilder.add(PREItems.ALUMNABRASS_SWORD, "Alumnabrass Sword");
		translationBuilder.add(PREItems.ALUMNABRASS_PICKAXE, "Alumnabrass Pickaxe");
		translationBuilder.add(PREItems.ALUMNABRASS_AXE, "Alumnabrass Axe");
		translationBuilder.add(PREItems.ALUMNABRASS_SHOVEL, "Alumnabrass Shovel");
		translationBuilder.add(PREItems.ALUMNABRASS_HOE, "Alumnabrass Hoe");
		translationBuilder.add(PREItems.ALUMNABRASS_HELMET, "Alumnabrass Helmet");
		translationBuilder.add(PREItems.ALUMNABRASS_CHESTPLATE, "Alumnabrass Chestplate");
		translationBuilder.add(PREItems.ALUMNABRASS_LEGGINGS, "Alumnabrass Leggings");
		translationBuilder.add(PREItems.ALUMNABRASS_BOOTS, "Alumnabrass Boots");
		translationBuilder.add(PREItems.ALUMNABRASS_INGOT, "Alumnabrass Ingot");
		//endregion
		//region Copper
		translationBuilder.add(PREItems.COPPER_SWORD, "Copper Sword");
		translationBuilder.add(PREItems.COPPER_PICKAXE, "Copper Pickaxe");
		translationBuilder.add(PREItems.COPPER_AXE, "Copper Axe");
		translationBuilder.add(PREItems.COPPER_SHOVEL, "Copper Shovel");
		translationBuilder.add(PREItems.COPPER_HOE, "Copper Hoe");
		translationBuilder.add(PREItems.COPPER_HELMET, "Copper Helmet");
		translationBuilder.add(PREItems.COPPER_CHESTPLATE, "Copper Chestplate");
		translationBuilder.add(PREItems.COPPER_LEGGINGS, "Copper Leggings");
		translationBuilder.add(PREItems.COPPER_BOOTS, "Copper Boots");
		//endregion
		//region Rose Gold
		translationBuilder.add(PREItems.ROSE_GOLD_SWORD, "Rose Gold Sword");
		translationBuilder.add(PREItems.ROSE_GOLD_PICKAXE, "Rose Gold Pickaxe");
		translationBuilder.add(PREItems.ROSE_GOLD_AXE, "Rose Gold Axe");
		translationBuilder.add(PREItems.ROSE_GOLD_SHOVEL, "Rose Gold Shovel");
		translationBuilder.add(PREItems.ROSE_GOLD_HOE, "Rose Gold Hoe");
		translationBuilder.add(PREItems.ROSE_GOLD_HELMET, "Rose Gold Helmet");
		translationBuilder.add(PREItems.ROSE_GOLD_CHESTPLATE, "Rose Gold Chestplate");
		translationBuilder.add(PREItems.ROSE_GOLD_LEGGINGS, "Rose Gold Leggings");
		translationBuilder.add(PREItems.ROSE_GOLD_BOOTS, "Rose Gold Boots");
		translationBuilder.add(PREItems.ROSE_GOLD_INGOT, "Rose Gold Ingot");
		//endregion
		//region Brass
		translationBuilder.add(PREItems.BRASS_SWORD, "Brass Sword");
		translationBuilder.add(PREItems.BRASS_PICKAXE, "Brass Pickaxe");
		translationBuilder.add(PREItems.BRASS_AXE, "Brass Axe");
		translationBuilder.add(PREItems.BRASS_SHOVEL, "Brass Shovel");
		translationBuilder.add(PREItems.BRASS_HOE, "Brass Hoe");
		translationBuilder.add(PREItems.BRASS_HELMET, "Brass Helmet");
		translationBuilder.add(PREItems.BRASS_CHESTPLATE, "Brass Chestplate");
		translationBuilder.add(PREItems.BRASS_LEGGINGS, "Brass Leggings");
		translationBuilder.add(PREItems.BRASS_BOOTS, "Brass Boots");
		translationBuilder.add(PREItems.BRASS_INGOT, "Brass Ingot");
		//endregion
		//region Vocalite
		translationBuilder.add(PREItems.VOCALITE_SPEAR, "Vocalite Spear");
		translationBuilder.add(PREItems.VOCALITE_SWORD, "Vocalite Sword");
		translationBuilder.add(PREItems.VOCALITE_PICKAXE, "Vocalite Pickaxe");
		translationBuilder.add(PREItems.VOCALITE_AXE, "Vocalite Axe");
		translationBuilder.add(PREItems.VOCALITE_SHOVEL, "Vocalite Shovel");
		translationBuilder.add(PREItems.VOCALITE_HOE, "Vocalite Hoe");
		translationBuilder.add(PREItems.VOCALITE_HELMET, "Vocalite Helmet");
		translationBuilder.add(PREItems.VOCALITE_CHESTPLATE, "Vocalite Chestplate");
		translationBuilder.add(PREItems.VOCALITE_LEGGINGS, "Vocalite Leggings");
		translationBuilder.add(PREItems.VOCALITE_BOOTS, "Vocalite Boots");
		translationBuilder.add(PREItems.VOCALITE_INGOT, "Vocalite Ingot");
		//endregion
		//region Electrum
		translationBuilder.add(PREItems.ELECTRUM_SPEAR, "Electrum Spear");
		translationBuilder.add(PREItems.ELECTRUM_SWORD, "Electrum Sword");
		translationBuilder.add(PREItems.ELECTRUM_PICKAXE, "Electrum Pickaxe");
		translationBuilder.add(PREItems.ELECTRUM_AXE, "Electrum Axe");
		translationBuilder.add(PREItems.ELECTRUM_SHOVEL, "Electrum Shovel");
		translationBuilder.add(PREItems.ELECTRUM_HOE, "Electrum Hoe");
		translationBuilder.add(PREItems.ELECTRUM_HELMET, "Electrum Helmet");
		translationBuilder.add(PREItems.ELECTRUM_CHESTPLATE, "Electrum Chestplate");
		translationBuilder.add(PREItems.ELECTRUM_LEGGINGS, "Electrum Leggings");
		translationBuilder.add(PREItems.ELECTRUM_BOOTS, "Electrum Boots");
		translationBuilder.add(PREItems.ELECTRUM_INGOT, "Electrum Ingot");
		//endregion
		//region Blocks
		translationBuilder.add(PREBlocks.RECYCLER, "Recycler");
		translationBuilder.add(PREBlocks.INDUCTION_FURNACE, "Induction Furnace");
		//endregion
		translationBuilder.add("music.minecraft.game.water.shuniji", "Shuniji");
		translationBuilder.add("music.minecraft.game.water.shuniji.artist", "C418");
		translationBuilder.add("music.minecraft.game.water.dragon_fish", "Dragon Fish");
		translationBuilder.add("music.minecraft.game.water.dragon_fish.artist", "C418");
		translationBuilder.add("music.minecraft.game.water.axolotl", "Axolotl");
		translationBuilder.add("music.minecraft.game.water.axolotl.artist", "C418");
		translationBuilder.add("music.minecraft.game.an_ordinary_day", "An Ordinary Day");
		translationBuilder.add("music.minecraft.game.an_ordinary_day.artist", "Kumi Tanioka");
		translationBuilder.add("music.minecraft.game.stand_tall", "Stand Tall");
		translationBuilder.add("music.minecraft.game.stand_tall.artist", "Lena Raine");
		translationBuilder.add("music.minecraft.game.swamp.aerie", "Aerie");
		translationBuilder.add("music.minecraft.game.swamp.aerie.artist", "Lena Raine");
		translationBuilder.add("music.minecraft.game.swamp.labyrinthine", "Labyrinthine");
		translationBuilder.add("music.minecraft.game.swamp.labyrinthine.artist", "Lena Raine");
		translationBuilder.add("music.minecraft.game.swamp.firebugs", "Firebugs");
		translationBuilder.add("music.minecraft.game.swamp.firebugs.artist", "Lena Raine");
		translationBuilder.add("music.minecraft.game.wending", "Wending");
		translationBuilder.add("music.minecraft.game.wending.artist", "Lena Raine");
		translationBuilder.add("music.minecraft.game.infinite_amethyst", "Infinite Amethyst");
		translationBuilder.add("music.minecraft.game.infinite_amethyst.artist", "Lena Raine");
		translationBuilder.add("music.minecraft.game.ancestry", "Ancestry");
		translationBuilder.add("music.minecraft.game.ancestry.artist", "Lena Raine");
		translationBuilder.add("music.minecraft.game.bromeliad", "Bromeliad");
		translationBuilder.add("music.minecraft.game.bromeliad.artist", "Aaron Cherof");
		translationBuilder.add("music.minecraft.game.echo_in_the_wind", "Echo in the Wind");
		translationBuilder.add("music.minecraft.game.echo_in_the_wind.artist", "Aaron Cherof");
		translationBuilder.add("music.minecraft.game.crescent_dunes", "Crescent Dunes");
		translationBuilder.add("music.minecraft.game.crescent_dunes.artist", "Aaron Cherof");
		translationBuilder.add("music.minecraft.game.nether.nether_wastes.rubedo", "Rubedo");
		translationBuilder.add("music.minecraft.game.nether.nether_wastes.rubedo.artist", "Lena Raine");
		translationBuilder.add("music.minecraft.game.nether.crimson_forest.chrysopoeia", "Chrysopoeia");
		translationBuilder.add("music.minecraft.game.nether.crimson_forest.chrysopoeia.artist", "Lena Raine");
		translationBuilder.add("music.minecraft.game.nether.warmth", "Warmth");
		translationBuilder.add("music.minecraft.game.nether.warmth.artist", "C418");
		translationBuilder.add("music.minecraft.game.nether.soul_sand_valley.so_below", "So Below");
		translationBuilder.add("music.minecraft.game.nether.soul_sand_valley.so_below.artist", "Lena Raine");
		translationBuilder.add("music.minecraft.game.nether.dead_voxel", "Dead Voxel");
		translationBuilder.add("music.minecraft.game.nether.dead_voxel.artist", "C418");
		translationBuilder.add("music.minecraft.game.nether.concrete_halls", "Concrete Halls");
		translationBuilder.add("music.minecraft.game.nether.concrete_halls.artist", "C418");
		translationBuilder.add("music.minecraft.game.nether.ballad_of_the_cats", "Ballad of the Cats");
		translationBuilder.add("music.minecraft.game.nether.ballad_of_the_cats.artist", "C418");
		translationBuilder.add("music.minecraft.game.menu.mutation", "Mutation");
		translationBuilder.add("music.minecraft.game.menu.mutation.artist", "C418");
		translationBuilder.add("music.minecraft.game.moog_city_2", "Moog City 2");
		translationBuilder.add("music.minecraft.game.moog_city_2.artist", "C418");
		translationBuilder.add("music.minecraft.game.floating_trees", "Floating Trees");
		translationBuilder.add("music.minecraft.game.floating_trees.artist", "C418");
		translationBuilder.add("music.minecraft.game.beginning_2", "Beginning 2");
		translationBuilder.add("music.minecraft.game.beginning_2.artist", "C418");
		translationBuilder.add("music.minecraft.game.endless", "Endless");
		translationBuilder.add("music.minecraft.game.endless.artist", "Lena Raine");
		translationBuilder.add("music.minecraft.game.eld_unknown", "Eld Unknown");
		translationBuilder.add("music.minecraft.game.eld_unknown.artist", "Lena Raine");
		translationBuilder.add("music.minecraft.game.deeper", "Deeper");
		translationBuilder.add("music.minecraft.game.deeper.artist", "Lena Raine");
		translationBuilder.add("music.minecraft.game.end.the_end", "The End");
		translationBuilder.add("music.minecraft.game.end.the_end.artist", "C418");
		translationBuilder.add("music.minecraft.game.end.boss", "Boss");
		translationBuilder.add("music.minecraft.game.end.boss.artist", "C418");
		translationBuilder.add("music.minecraft.game.end.alpha", "Alpha");
		translationBuilder.add("music.minecraft.game.end.alpha.artist", "C418");
		translationBuilder.add("music.minecraft.game.yakusoku", "yakusoku");
		translationBuilder.add("music.minecraft.game.yakusoku.artist", "Kumi Tanioka");
		translationBuilder.add("music.minecraft.game.watcher", "Watcher");
		translationBuilder.add("music.minecraft.game.watcher.artist", "Aaron Cherof");
		translationBuilder.add("music.minecraft.game.wet_hands", "Wet Hands");
		translationBuilder.add("music.minecraft.game.wet_hands.artist", "C418");
		translationBuilder.add("music.minecraft.game.clark", "Clark");
		translationBuilder.add("music.minecraft.game.clark.artist", "C418");
		translationBuilder.add("music.minecraft.game.sweden", "Sweden");
		translationBuilder.add("music.minecraft.game.sweden.artist", "C418");
		translationBuilder.add("music.minecraft.game.subwoofer_lullaby", "Subwoofer Lullaby");
		translationBuilder.add("music.minecraft.game.subwoofer_lullaby.artist", "C418");
		translationBuilder.add("music.minecraft.game.puzzlebox", "Puzzlebox");
		translationBuilder.add("music.minecraft.game.puzzlebox.artist", "Aaron Cherof");
		translationBuilder.add("music.minecraft.game.oxygene", "Oxygene");
		translationBuilder.add("music.minecraft.game.oxygene.artist", "C418");
		translationBuilder.add("music.minecraft.game.one_more_day", "One More Day");
		translationBuilder.add("music.minecraft.game.one_more_day.artist", "Lena Raine");
		translationBuilder.add("music.minecraft.game.minecraft", "Minecraft");
		translationBuilder.add("music.minecraft.game.minecraft.artist", "C418");
		translationBuilder.add("music.minecraft.game.mice_on_venus", "Mice on Venus");
		translationBuilder.add("music.minecraft.game.mice_on_venus.artist", "C418");
		translationBuilder.add("music.minecraft.game.living_mice", "Living Mice");
		translationBuilder.add("music.minecraft.game.living_mice.artist", "C418");
		translationBuilder.add("music.minecraft.game.left_to_bloom", "Left to Bloom");
		translationBuilder.add("music.minecraft.game.left_to_bloom.artist", "Lena Raine");
		translationBuilder.add("music.minecraft.game.pokopoko", "pokopoko");
		translationBuilder.add("music.minecraft.game.pokopoko.artist", "Kumi Tanioka");
		translationBuilder.add("music.minecraft.game.komorebi", "komorebi");
		translationBuilder.add("music.minecraft.game.komorebi.artist", "Kumi Tanioka");
		translationBuilder.add("music.minecraft.game.key", "Key");
		translationBuilder.add("music.minecraft.game.key.artist", "C418");
		translationBuilder.add("music.minecraft.game.haggstrom", "Haggstrom");
		translationBuilder.add("music.minecraft.game.haggstrom.artist", "C418");
		translationBuilder.add("music.minecraft.game.floating_dream", "Floating Dream");
		translationBuilder.add("music.minecraft.game.floating_dream.artist", "Kumi Tanioka");
		translationBuilder.add("music.minecraft.game.featherfall", "Featherfall");
		translationBuilder.add("music.minecraft.game.featherfall.artist", "Aaron Cherof");
		translationBuilder.add("music.minecraft.game.comforting_memories", "Comforting Memories");
		translationBuilder.add("music.minecraft.game.comforting_memories.artist", "Kumi Tanioka");
		translationBuilder.add("music.minecraft.game.below_and_above", "Below and Above");
		translationBuilder.add("music.minecraft.game.below_and_above.artist", "Amos Roddy");
		translationBuilder.add("music.minecraft.game.dry_hands", "Dry Hands");
		translationBuilder.add("music.minecraft.game.dry_hands.artist", "C418");
		translationBuilder.add("music.minecraft.game.danny", "Danny");
		translationBuilder.add("music.minecraft.game.danny.artist", "C418");
		translationBuilder.add("music.minecraft.game.broken_clocks", "Broken Clocks");
		translationBuilder.add("music.minecraft.game.broken_clocks.artist", "Amos Roddy");
		translationBuilder.add("music.minecraft.game.a_familiar_room", "A Familiar Room");
		translationBuilder.add("music.minecraft.game.a_familiar_room.artist", "Aaron Cherof");
		translationBuilder.add("music.minecraft.game.creative.aria_math", "Aria Math");
		translationBuilder.add("music.minecraft.game.creative.aria_math.artist", "C418");
		translationBuilder.add("music.minecraft.game.creative.biome_fest", "Biome Fest");
		translationBuilder.add("music.minecraft.game.creative.biome_fest.artist", "C418");
		translationBuilder.add("music.minecraft.game.creative.blind_spots", "Blind Spots");
		translationBuilder.add("music.minecraft.game.creative.blind_spots.artist", "C418");
		translationBuilder.add("music.minecraft.game.creative.dreiton", "Dreiton");
		translationBuilder.add("music.minecraft.game.creative.dreiton.artist", "C418");
		translationBuilder.add("music.minecraft.game.creative.haunt_muskie", "Haunt Muskie");
		translationBuilder.add("music.minecraft.game.creative.haunt_muskie.artist", "C418");
		translationBuilder.add("music.minecraft.game.creative.taswell", "Taswell");
		translationBuilder.add("music.minecraft.game.creative.taswell.artist", "C418");

		translationBuilder.add("enchantment." + ProgressionReEnvisioned.ID + ".lunge", "Lunge");
		translationBuilder.add("container." + ProgressionReEnvisioned.ID + ".induction_furnace.missing_template_tooltip", "Add Advanced Upgrade Template");
		translationBuilder.add("container." + ProgressionReEnvisioned.ID + ".induction_furnace", "Induction Furnace");
		translationBuilder.add("item." + ProgressionReEnvisioned.ID + ".basic_upgrade_template.new", "Basic Upgrade Template");
		translationBuilder.add("item." + ProgressionReEnvisioned.ID + ".smithing_template.basic_upgrade.applies_to", "Tier 1 Equipment");
		translationBuilder.add("item." + ProgressionReEnvisioned.ID + ".smithing_template.basic_upgrade.additions_slot_description", "Add Tier 2 Materials");
		translationBuilder.add("item." + ProgressionReEnvisioned.ID + ".smithing_template.basic_upgrade.base_slot_description", "Add Tier 1 armor, weapon, or tool");
		translationBuilder.add("item." + ProgressionReEnvisioned.ID + ".smithing_template.basic_upgrade.ingredients", "Tier 2 Material");
		translationBuilder.add("item.minecraft.ender_eye.speech.0", "I’ve seen all and I am all");
		translationBuilder.add("item.minecraft.ender_eye.speech.1", "For you, your power stands tall");
		translationBuilder.add("item.minecraft.ender_eye.speech.2", "Now I shall entrust you with this");
		translationBuilder.add("item.minecraft.ender_eye.speech.3", "A way to a place that may be common to miss");
		translationBuilder.add("item.minecraft.ender_eye.speech.4", "One that holds treasures and rewards galore");
		translationBuilder.add("item.minecraft.ender_eye.speech.5", "And even a way to be able to soar");
		translationBuilder.add("item.minecraft.ender_eye.speech.6", "I shall speak it once and only once");
		translationBuilder.add("item.minecraft.ender_eye.speech.7", "And I shall leave you to your soaring stunts");
		translationBuilder.add("item.minecraft.ender_eye.speech.8", "The location you seek is deep below");
		translationBuilder.add("item.minecraft.ender_eye.speech.9", "But with these coordinates I shall show");
		translationBuilder.add("tooltip.silk_touch", "Silk Touch");
		translationBuilder.add("tooltip.fortune", "Fortune");
		translationBuilder.add("item.pre.recovers_inventory_using_amethyst", "Recovers 100 items per 1 Amethyst Shard in inventory on death");
		translationBuilder.add("item.pre.persists_player_death", "Persists Death");
		translationBuilder.add("subtitles.item.ender_eye.teleport_ambient", "Eye of ender hums");
		translationBuilder.add("subtitles.item.ender_eye.teleport", "Eye of ender teleports");
		translationBuilder.add("subtitles.item.ender_eye.teleport_start", "Eye of ender primed");
		translationBuilder.add("subtitles.item.transender.teleport", "Transender teleports");
		translationBuilder.add("subtitles.item.transender.teleport_start", "Transender primed");
		translationBuilder.add("subtitles.item.spear.use", "Spear engaged");
		translationBuilder.add("subtitles.item.spear.jab", "Spear jabs");
		translationBuilder.add("subtitles.item.spear.lunge", "Spear lunges");
		translationBuilder.add("subtitles.item.transender.charge", "Transender recharged");
		translationBuilder.add("subtitles.block.recycler.insert", "Item inserted into recycler");
		translationBuilder.add("subtitles.block.recycler.fail", "Recycler fails");
		translationBuilder.add("subtitles.block.recycler.empty", "Recycler recycles");
		translationBuilder.add("block." + ProgressionReEnvisioned.ID + ".recycler.speech.0", "The Recycler Instruction Manual");
		translationBuilder.add("block." + ProgressionReEnvisioned.ID + ".recycler.speech.1", "This page serves as a simple guide on how to use the recycler");
		translationBuilder.add("block." + ProgressionReEnvisioned.ID + ".recycler.speech.2",
				"Step 1: Insert any material into the top to get something out when powered" +
				"\\NOTE: It will only recycle specific items. See foot note 1");
		translationBuilder.add("block." + ProgressionReEnvisioned.ID + ".recycler.speech.3",
				"Step 2: Power the Recycler with Redstone. Lever is preferred");
		translationBuilder.add("block." + ProgressionReEnvisioned.ID + ".recycler.speech.4",
				"Step 3: Gamble whatever trash you have to your hearts content and repeat as much as you'd like!" +
				"\\(Results may vary. See foot note two)");
		translationBuilder.add("block." + ProgressionReEnvisioned.ID + ".recycler.speech.5",
				"Foot note 1: Rotten Flesh, Spider Eyes, String, Bones, Slime Balls, Armadillo scute, Ender Pearls, " +
						"Blaze Rods, Blaze Powder, Poisonous Potatoes, Gunpowder, Dripstone, Sticks, Saplings, Resin, " +
						"Resin Bricks, Nether Bricks, Bricks, and Disc Fragments");
		translationBuilder.add("block." + ProgressionReEnvisioned.ID + ".recycler.speech.6",
				"Foot note 2: ... This part of the page has been damaged beyond repair and scribbles cover whatever is left.");
		//region Attributes
		translationBuilder.add(PREEntityAttributes.FIRE_PROTECTION, "Fire Protection");
		translationBuilder.add(PREEntityAttributes.EXPLOSION_PROTECTION, "Explosion Protection");
		translationBuilder.add(PREEntityAttributes.MAGIC_PROTECTION, "Magic Protection");
		translationBuilder.add(PREEntityAttributes.PHYSICAL_PROTECTION, "Physical Protection");
		translationBuilder.add(PREEntityAttributes.PROJECTILE_PROTECTION, "Projectile Protection");
		translationBuilder.add(PREEntityAttributes.NETHER_DAMAGE, "Nether Damage");
		translationBuilder.add(PREEntityAttributes.ENDER_DAMAGE, "Ender Damage");
		translationBuilder.add(PREEntityAttributes.UNDEAD_DAMAGE, "Undead Damage");
		translationBuilder.add(PREEntityAttributes.WILD_DAMAGE, "Wild Damage");
		//endregion
	}
}