package net.lixir.pre.common.init;

import net.fabricmc.fabric.api.item.v1.DefaultItemComponentEvents;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.lixir.pre.common.ProgressionReEnvisioned;
import net.lixir.pre.common.component.FortuneComponent;
import net.lixir.pre.common.component.SilkTouchComponent;
import net.lixir.pre.common.item.*;
import net.lixir.pre.common.util.SmithingTemplateUtil;
import net.minecraft.block.Block;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.component.type.EnchantableComponent;
import net.minecraft.component.type.RepairableComponent;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.*;
import net.minecraft.item.equipment.EquipmentType;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.BiFunction;
import java.util.function.Function;

public final class PREItems {
    //region Items

    public static final Item ELECTRUM_INGOT = register("electrum_ingot");
    public static final Item ALUMNABRASS_INGOT = register("alumnabrass_ingot");
    public static final Item VOCALITE_INGOT = register("vocalite_ingot");
    public static final Item BRASS_INGOT = register("brass_ingot");
    public static final Item ROSE_GOLD_INGOT = register("rose_gold_ingot");
    public static final Item OLD_CLOCK = register("old_clock", OldClockItem::new, new Item.Settings().maxCount(1).maxDamage(30)
            .rarity(Rarity.PROGRESSION_REENVISIONED_RELIC).repairable(Items.CLOCK));
    public static final Item TRANSENDER = register("transender", TransenderItem::new, new Item.Settings().maxCount(1)
            .rarity(Rarity.PROGRESSION_REENVISIONED_RELIC));
    public static final Item ALUMNABRASS_BRUSH = register(
            "alumnabrass_brush",
            AlumnabrassBrushItem::new,
            new Item.Settings()
                    .maxCount(1)
                    .rarity(Rarity.PROGRESSION_REENVISIONED_RELIC)
                    .component(PREDataComponentTypes.MAX_CHARGE, 4)
                    .component(PREDataComponentTypes.CHARGING_TICKS, 0)
                    .component(PREDataComponentTypes.MAX_CHARGING_TICKS, 150)
    );
    public static final Item ELECTRUM_SPEAR = register("electrum_spear", SpearItem::new,
            new Item.Settings()
                    .rarity(Rarity.PROGRESSION_REENVISIONED_RELIC)
                    .maxCount(1)
                    .enchantable(15)
                    .maxDamage(2031)
                    .repairable(PREItems.ELECTRUM_INGOT)
                    .attributeModifiers(createElectrumSpearAttributes())
    );
    public static final Item NETHERITE_SPEAR = register("netherite_spear", SpearItem::new,
            new Item.Settings()
                    .rarity(Rarity.PROGRESSION_REENVISIONED_RELIC)
                    .maxCount(1)
                    .enchantable(15)
                    .maxDamage(2031)
                    .repairable(Items.NETHERITE_INGOT)
                    .attributeModifiers(createElectrumSpearAttributes())
    );
    public static final Item ALUMNABRASS_SPEAR = register("alumnabrass_spear", SpearItem::new,
            new Item.Settings()
                    .rarity(Rarity.PROGRESSION_REENVISIONED_RELIC)
                    .maxCount(1)
                    .enchantable(15)
                    .maxDamage(2031)
                    .repairable(PREItems.ALUMNABRASS_INGOT)
                    .attributeModifiers(createElectrumSpearAttributes())
    );
    public static final Item VOCALITE_SPEAR = register("vocalite_spear", SpearItem::new,
            new Item.Settings()
                    .rarity(Rarity.PROGRESSION_REENVISIONED_RELIC)
                    .maxCount(1)
                    .enchantable(15)
                    .maxDamage(2031)
                    .repairable(PREItems.VOCALITE_INGOT)
                    .attributeModifiers(createElectrumSpearAttributes())
    );

    public static final Item RECYCLER = register(PREBlocks.RECYCLER);
    public static final Item INDUCTION_FURNACE = register(PREBlocks.INDUCTION_FURNACE);
    public static final Item BASIC_UPGRADE_SMITHING_TEMPLATE = register(
            "basic_upgrade_smithing_template",
            SmithingTemplateUtil::createBasicSmithingTemplate,
            new Item.Settings().rarity(Rarity.UNCOMMON)
    );
    public static final Item ELECTRUM_HELMET = register(
            "electrum_helmet", Item::new, new Item.Settings()
                    .armor(PREArmorMaterials.ELECTRUM, EquipmentType.HELMET));
    public static final Item ELECTRUM_CHESTPLATE = register(
            "electrum_chestplate", Item::new, new Item.Settings()
                    .armor(PREArmorMaterials.ELECTRUM, EquipmentType.CHESTPLATE)
    );
    public static final Item ELECTRUM_LEGGINGS = register(
            "electrum_leggings", Item::new, new Item.Settings()
                    .armor(PREArmorMaterials.ELECTRUM, EquipmentType.LEGGINGS)
    );
    public static final Item ELECTRUM_BOOTS = register(
            "electrum_boots", Item::new, new Item.Settings()
                    .armor(PREArmorMaterials.ELECTRUM, EquipmentType.BOOTS)
    );
    public static final Item ELECTRUM_SWORD = register("electrum_sword", new Item.Settings().sword(PREToolMaterials.ELECTRUM, 3.0F, -2.4F));
    public static final Item ELECTRUM_SHOVEL = register(
            "electrum_shovel", settings -> new ShovelItem(PREToolMaterials.ELECTRUM, 1.5F, -3.0F, settings), new Item.Settings()
    );
    public static final Item ELECTRUM_PICKAXE = register("electrum_pickaxe", new Item.Settings().pickaxe(PREToolMaterials.ELECTRUM, 1.0F, -2.8F));
    public static final Item ELECTRUM_AXE = register(
            "electrum_axe", settings -> new AxeItem(PREToolMaterials.ELECTRUM, 5.0F, -3.0F, settings), new Item.Settings()
    );
    public static final Item ELECTRUM_HOE = register(
            "electrum_hoe", settings -> new HoeItem(PREToolMaterials.ELECTRUM, -4.0F, 0.0F, settings), new Item.Settings()
    );

    public static final Item ALUMNABRASS_HELMET = register(
            "alumnabrass_helmet",
            new Item.Settings()
                    .armor(PREArmorMaterials.ALUMNABRASS, EquipmentType.HELMET)
    );
    public static final Item ALUMNABRASS_CHESTPLATE = register(
            "alumnabrass_chestplate",
            new Item.Settings()
                    .armor(PREArmorMaterials.ALUMNABRASS, EquipmentType.CHESTPLATE)
    );
    public static final Item ALUMNABRASS_LEGGINGS = register(
            "alumnabrass_leggings",
            new Item.Settings()
                    .armor(PREArmorMaterials.ALUMNABRASS, EquipmentType.LEGGINGS)

    );
    public static final Item ALUMNABRASS_BOOTS = register(
            "alumnabrass_boots",
            new Item.Settings()
                    .armor(PREArmorMaterials.ALUMNABRASS, EquipmentType.BOOTS)
    );
    public static final Item ALUMNABRASS_SWORD = register(
            "alumnabrass_sword",
            new Item.Settings()
                    .sword(PREToolMaterials.ALUMNABRASS, 3.0F, -2.4F)
                    .component(PREDataComponentTypes.BONUS_FORTUNE, new FortuneComponent(2))
    );
    public static final Item ALUMNABRASS_SHOVEL = register(
            "alumnabrass_shovel", settings -> new ShovelItem(PREToolMaterials.ALUMNABRASS, 1.5F, -3.0F, settings),
            new Item.Settings()
                    .component(PREDataComponentTypes.BONUS_FORTUNE, new FortuneComponent(2))
    );
    public static final Item ALUMNABRASS_PICKAXE = register(
            "alumnabrass_pickaxe",
            new Item.Settings()
                    .pickaxe(PREToolMaterials.ALUMNABRASS, 1.0F, -2.8F)
                    .component(PREDataComponentTypes.BONUS_FORTUNE, new FortuneComponent(2))
    );
    public static final Item ALUMNABRASS_AXE = register(
            "alumnabrass_axe",
            settings -> new AxeItem(PREToolMaterials.ALUMNABRASS, 5.0F, -3.0F, settings),
            new Item.Settings()
                    .component(PREDataComponentTypes.BONUS_FORTUNE, new FortuneComponent(2))

    );
    public static final Item ALUMNABRASS_HOE = register(
            "alumnabrass_hoe", settings -> new HoeItem(PREToolMaterials.ALUMNABRASS, -4.0F, 0.0F, settings), new Item.Settings().component(PREDataComponentTypes.BONUS_FORTUNE, new FortuneComponent(2))
    );
    public static final Item VOCALITE_HELMET = register("vocalite_helmet", new Item.Settings().armor(PREArmorMaterials.VOCALITE, EquipmentType.HELMET));
    public static final Item VOCALITE_CHESTPLATE = register(
            "vocalite_chestplate", new Item.Settings().armor(PREArmorMaterials.VOCALITE, EquipmentType.CHESTPLATE)
    );
    public static final Item VOCALITE_LEGGINGS = register(
            "vocalite_leggings", new Item.Settings().armor(PREArmorMaterials.VOCALITE, EquipmentType.LEGGINGS)
    );
    public static final Item VOCALITE_BOOTS = register("vocalite_boots", new Item.Settings().armor(PREArmorMaterials.VOCALITE, EquipmentType.BOOTS));
    public static final Item VOCALITE_SWORD = register("vocalite_sword", new Item.Settings().sword(PREToolMaterials.VOCALITE, 3.0F, -2.4F));
    public static final Item VOCALITE_SHOVEL = register(
            "vocalite_shovel", settings -> new ShovelItem(PREToolMaterials.VOCALITE, 1.5F, -3.0F, settings), new Item.Settings()
    );
    public static final Item VOCALITE_PICKAXE = register("vocalite_pickaxe", new Item.Settings().pickaxe(PREToolMaterials.VOCALITE, 1.0F, -2.8F));
    public static final Item VOCALITE_AXE = register(
            "vocalite_axe", settings -> new AxeItem(PREToolMaterials.VOCALITE, 5.0F, -3.0F, settings), new Item.Settings()
    );
    public static final Item VOCALITE_HOE = register(
            "vocalite_hoe", settings -> new HoeItem(PREToolMaterials.VOCALITE, -4.0F, 0.0F, settings), new Item.Settings()
    );
    public static final Item COPPER_HELMET = register("copper_helmet", new Item.Settings().armor(PREArmorMaterials.COPPER, EquipmentType.HELMET));
    public static final Item COPPER_CHESTPLATE = register(
            "copper_chestplate", new Item.Settings().armor(PREArmorMaterials.COPPER, EquipmentType.CHESTPLATE)
    );
    public static final Item COPPER_LEGGINGS = register(
            "copper_leggings", new Item.Settings().armor(PREArmorMaterials.COPPER, EquipmentType.LEGGINGS)
    );
    public static final Item COPPER_BOOTS = register("copper_boots", new Item.Settings().armor(PREArmorMaterials.COPPER, EquipmentType.BOOTS));
    public static final Item COPPER_SWORD = register("copper_sword", new Item.Settings().sword(PREToolMaterials.COPPER, 2.0F, -2.4F));
    public static final Item COPPER_SHOVEL = register(
            "copper_shovel", settings -> new ShovelItem(PREToolMaterials.COPPER, 1.5F, -3.0F, settings), new Item.Settings()
    );
    public static final Item COPPER_PICKAXE = register("copper_pickaxe", new Item.Settings().pickaxe(PREToolMaterials.COPPER, 1.0F, -2.8F));
    public static final Item COPPER_AXE = register(
            "copper_axe", settings -> new AxeItem(PREToolMaterials.COPPER, 5.0F, -3.1F, settings), new Item.Settings()
    );
    public static final Item COPPER_HOE = register(
            "copper_hoe", settings -> new HoeItem(PREToolMaterials.COPPER, -2.0F, -1.0F, settings), new Item.Settings()
    );
    public static final Item BRASS_HELMET = register("brass_helmet", new Item.Settings().armor(PREArmorMaterials.BRASS, EquipmentType.HELMET));
    public static final Item BRASS_CHESTPLATE = register(
            "brass_chestplate", new Item.Settings().armor(PREArmorMaterials.BRASS, EquipmentType.CHESTPLATE)
    );
    public static final Item BRASS_LEGGINGS = register(
            "brass_leggings", new Item.Settings().armor(PREArmorMaterials.BRASS, EquipmentType.LEGGINGS)
    );
    public static final Item BRASS_BOOTS = register("brass_boots", new Item.Settings().armor(PREArmorMaterials.BRASS, EquipmentType.BOOTS));
    public static final Item BRASS_SWORD = register("brass_sword", new Item.Settings().sword(PREToolMaterials.BRASS, 3.0F, -2.4F));
    public static final Item BRASS_SHOVEL = register(
            "brass_shovel", settings -> new ShovelItem(PREToolMaterials.BRASS, 1.5F, -3.0F, settings), new Item.Settings()
    );
    public static final Item BRASS_PICKAXE = register("brass_pickaxe", new Item.Settings().pickaxe(PREToolMaterials.BRASS, 1.0F, -2.8F));
    public static final Item BRASS_AXE = register(
            "brass_axe", settings -> new AxeItem(PREToolMaterials.BRASS, 5.0F, -3.0F, settings), new Item.Settings()
    );
    public static final Item BRASS_HOE = register(
            "brass_hoe", settings -> new HoeItem(PREToolMaterials.BRASS, -3.0F, 0.0F, settings), new Item.Settings()
    );
    public static final Item ROSE_GOLD_HELMET = register("rose_gold_helmet", new Item.Settings().armor(PREArmorMaterials.ROSE_GOLD, EquipmentType.HELMET));
    public static final Item ROSE_GOLD_CHESTPLATE = register(
            "rose_gold_chestplate", new Item.Settings().armor(PREArmorMaterials.ROSE_GOLD, EquipmentType.CHESTPLATE)
    );
    public static final Item ROSE_GOLD_LEGGINGS = register(
            "rose_gold_leggings", new Item.Settings().armor(PREArmorMaterials.ROSE_GOLD, EquipmentType.LEGGINGS)
    );
    public static final Item ROSE_GOLD_BOOTS = register("rose_gold_boots", new Item.Settings().armor(PREArmorMaterials.ROSE_GOLD, EquipmentType.BOOTS));
    public static final Item ROSE_GOLD_SWORD = register("rose_gold_sword", new Item.Settings().sword(PREToolMaterials.ROSE_GOLD, 3.0F, -2.4F).component(PREDataComponentTypes.SILK_TOUCH, new SilkTouchComponent(true)));
    public static final Item ROSE_GOLD_SHOVEL = register(
            "rose_gold_shovel", settings -> new ShovelItem(PREToolMaterials.ROSE_GOLD, 1.5F, -3.0F, settings), new Item.Settings().component(PREDataComponentTypes.SILK_TOUCH, new SilkTouchComponent(true))
    );
    public static final Item ROSE_GOLD_PICKAXE = register("rose_gold_pickaxe", new Item.Settings().pickaxe(PREToolMaterials.ROSE_GOLD, 1.0F, -2.8F).component(PREDataComponentTypes.SILK_TOUCH, new SilkTouchComponent(true)));
    public static final Item ROSE_GOLD_AXE = register(
            "rose_gold_axe", settings -> new AxeItem(PREToolMaterials.ROSE_GOLD, 5.0F, -3.0F, settings), new Item.Settings().component(PREDataComponentTypes.SILK_TOUCH, new SilkTouchComponent(true))
    );
    public static final Item ROSE_GOLD_HOE = register(
            "rose_gold_hoe", settings -> new HoeItem(PREToolMaterials.ROSE_GOLD, -3.0F, 0.0F, settings), new Item.Settings().component(PREDataComponentTypes.SILK_TOUCH, new SilkTouchComponent(true))
    );
    //endregion

    //region Attributes
    public static final Identifier BASE_PHYSICAL_PROTECTION_MODIFIER_ID = ProgressionReEnvisioned.id("base_physical_protection");
    public static final Identifier BASE_MAGIC_PROTECTION_MODIFIER_ID = ProgressionReEnvisioned.id("base_magic_protection");
    public static final Identifier BASE_FIRE_PROTECTION_MODIFIER_ID = ProgressionReEnvisioned.id("base_fire_protection");
    public static final Identifier BASE_EXPLOSION_PROTECTION_MODIFIER_ID = ProgressionReEnvisioned.id("base_explosion_protection");
    public static final Identifier BASE_PROJECTILE_PROTECTION_MODIFIER_ID = ProgressionReEnvisioned.id("base_projectile_protection");
    public static final Identifier BASE_ENDER_DAMAGE_MODIFIER_ID = ProgressionReEnvisioned.id("base_ender_damage");
    public static final Identifier BASE_WILD_DAMAGE_MODIFIER_ID = ProgressionReEnvisioned.id("base_wild_damage");
    public static final Identifier BASE_UNDEAD_DAMAGE_MODIFIER_ID = ProgressionReEnvisioned.id("base_undead_damage");
    public static final Identifier BASE_NETHER_DAMAGE_MODIFIER_ID = ProgressionReEnvisioned.id("base_nether_damage");
    public static final Identifier BASE_BLOCK_INTERACTION_RANGE_MODIFIER_ID = ProgressionReEnvisioned.id("base_block_interaction_range_modifier");
    public static final Identifier BASE_ENTITY_INTERACTION_RANGE_MODIFIER_ID = ProgressionReEnvisioned.id("base_entity_interaction_range_modifier");

    private PREItems() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static AttributeModifiersComponent merge(@Nullable AttributeModifiersComponent original, AttributeModifiersComponent addition) {
        if (original == null) return addition;

        AttributeModifiersComponent.Builder builder = AttributeModifiersComponent.builder();
        original.modifiers().forEach(entry -> builder.add(entry.attribute(), entry.modifier(), entry.slot()));
        addition.modifiers().forEach(entry -> builder.add(entry.attribute(), entry.modifier(), entry.slot()));
        return builder.build();
    }

    public static AttributeModifiersComponent createElectrumSpearAttributes() {
        return AttributeModifiersComponent.builder()
                .add(
                        EntityAttributes.ATTACK_DAMAGE,
                        new EntityAttributeModifier(Item.BASE_ATTACK_DAMAGE_MODIFIER_ID, 5, EntityAttributeModifier.Operation.ADD_VALUE),
                        AttributeModifierSlot.MAINHAND
                )
                .add(
                        EntityAttributes.ATTACK_SPEED,
                        new EntityAttributeModifier(Item.BASE_ATTACK_SPEED_MODIFIER_ID, -2.5F, EntityAttributeModifier.Operation.ADD_VALUE),
                        AttributeModifierSlot.MAINHAND
                )
                .build();
    }

    public static AttributeModifiersComponent createNetheriteMaceAttributes() {
        return AttributeModifiersComponent.builder()
                .add(
                        EntityAttributes.ATTACK_DAMAGE,
                        new EntityAttributeModifier(MaceItem.BASE_ATTACK_DAMAGE_MODIFIER_ID, 5.5, EntityAttributeModifier.Operation.ADD_VALUE),
                        AttributeModifierSlot.MAINHAND
                )
                .add(
                        EntityAttributes.ATTACK_SPEED,
                        new EntityAttributeModifier(MaceItem.BASE_ATTACK_SPEED_MODIFIER_ID, -3.4F, EntityAttributeModifier.Operation.ADD_VALUE),
                        AttributeModifierSlot.MAINHAND
                )
                .build();
    }

    public static AttributeModifiersComponent createNetheriteToolAttributes() {
        return AttributeModifiersComponent.builder()
                .add(
                        PREEntityAttributes.WILD_DAMAGE,
                        new EntityAttributeModifier(BASE_WILD_DAMAGE_MODIFIER_ID, 0.33, EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE),
                        AttributeModifierSlot.MAINHAND
                )
                .build();
    }

    public static AttributeModifiersComponent createVocaliteToolAttributes() {
        return AttributeModifiersComponent.builder()
                .add(
                        PREEntityAttributes.UNDEAD_DAMAGE,
                        new EntityAttributeModifier(BASE_UNDEAD_DAMAGE_MODIFIER_ID, 0.33, EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE),
                        AttributeModifierSlot.MAINHAND
                )
                .build();
    }

    public static AttributeModifiersComponent createElectrumToolAttributes() {
        return AttributeModifiersComponent.builder()
                .add(
                        PREEntityAttributes.NETHER_DAMAGE,
                        new EntityAttributeModifier(BASE_NETHER_DAMAGE_MODIFIER_ID, 0.33, EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE),
                        AttributeModifierSlot.MAINHAND
                )
                .build();
    }

    public static AttributeModifiersComponent createAlumnabrassToolAttributes() {
        return AttributeModifiersComponent.builder()
                .add(
                        PREEntityAttributes.ENDER_DAMAGE,
                        new EntityAttributeModifier(BASE_ENDER_DAMAGE_MODIFIER_ID, 0.33, EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE),
                        AttributeModifierSlot.MAINHAND
                )
                .build();
    }

    public static AttributeModifiersComponent createElectrumArmorAttributes(AttributeModifierSlot slot) {
        return AttributeModifiersComponent.builder()
                .add(
                        PREEntityAttributes.EXPLOSION_PROTECTION,
                        new EntityAttributeModifier(BASE_EXPLOSION_PROTECTION_MODIFIER_ID, 0.15, EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE),
                        slot
                )
                .add(
                        PREEntityAttributes.FIRE_PROTECTION,
                        new EntityAttributeModifier(BASE_FIRE_PROTECTION_MODIFIER_ID, 0.15, EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE),
                        slot
                )
                .add(
                        EntityAttributes.BLOCK_INTERACTION_RANGE,
                        new EntityAttributeModifier(BASE_BLOCK_INTERACTION_RANGE_MODIFIER_ID, 1, EntityAttributeModifier.Operation.ADD_VALUE),
                        slot
                )
                .build();
    }

    public static AttributeModifiersComponent createNetheriteArmorAttributes(AttributeModifierSlot slot) {
        return AttributeModifiersComponent.builder()
                .add(
                        PREEntityAttributes.PHYSICAL_PROTECTION,
                        new EntityAttributeModifier(BASE_PHYSICAL_PROTECTION_MODIFIER_ID, 0.15, EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE),
                        slot
                )
                .build();
    }

    public static AttributeModifiersComponent createVocaliteArmorAttributes(AttributeModifierSlot slot) {
        return AttributeModifiersComponent.builder()
                .add(
                        PREEntityAttributes.PROJECTILE_PROTECTION,
                        new EntityAttributeModifier(BASE_PROJECTILE_PROTECTION_MODIFIER_ID, 0.15, EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE),
                        slot
                )
                .build();
    }

    public static AttributeModifiersComponent createAlumnabrassArmorAttributes(AttributeModifierSlot slot) {
        return AttributeModifiersComponent.builder()
                .add(
                        PREEntityAttributes.MAGIC_PROTECTION,
                        new EntityAttributeModifier(BASE_MAGIC_PROTECTION_MODIFIER_ID, 0.15, EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE),
                        slot
                )
                .build();
    }
    //endregion

    //region Register Methods
    private static RegistryKey<Item> keyOf(String id) {
        return RegistryKey.of(RegistryKeys.ITEM, ProgressionReEnvisioned.id(id));
    }

    public static Item register(String id) {
        return register(keyOf(id), Item::new, new Item.Settings());
    }

    public static Item register(String id, Item.Settings settings) {
        return register(keyOf(id), Item::new, settings);
    }

    public static Item register(String id, Function<Item.Settings, Item> factory, Item.Settings settings) {
        return register(keyOf(id), factory, settings);
    }

    public static Item register(Block block) {
        return register(block, BlockItem::new, new Item.Settings().maxCount(64));
    }

    public static Item register(Block block, Item.Settings settings) {
        return register(block, BlockItem::new, settings);
    }

    public static Item register(Block block, BiFunction<Block, Item.Settings, Item> factory, Item.Settings settings) {
        String id = Registries.BLOCK.getId(block).getPath();
        return register(keyOf(id), (itemSettings) -> factory.apply(block, itemSettings.useBlockPrefixedTranslationKey()), settings);
    }

    public static Item register(RegistryKey<Item> key, @NotNull Function<Item.Settings, Item> factory, Item.@NotNull Settings settings) {
        Item item = factory.apply(settings.registryKey(key));
        if (item instanceof BlockItem blockItem)
            blockItem.appendBlocks(Item.BLOCK_ITEMS, item);

        return Registry.register(Registries.ITEM, key, item);
    }
    //endregion

    public static void initialize() {
        //region Combat Tab
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT)
                .register(entries -> {
                    //region Axes
                    entries.addBefore(Items.NETHERITE_AXE, PREItems.VOCALITE_AXE);
                    entries.addBefore(PREItems.VOCALITE_AXE, PREItems.ELECTRUM_AXE);
                    entries.addBefore(PREItems.ELECTRUM_AXE, PREItems.ALUMNABRASS_AXE);
                    entries.addAfter(Items.DIAMOND_AXE, PREItems.ROSE_GOLD_AXE);
                    entries.addBefore(Items.DIAMOND_AXE, PREItems.BRASS_AXE);
                    entries.addBefore(Items.IRON_AXE, PREItems.COPPER_AXE);
                    //endregion
                    //region Vocalite Armor
                    entries.addBefore(Items.NETHERITE_HELMET, PREItems.VOCALITE_HELMET);
                    entries.addAfter(PREItems.VOCALITE_HELMET, PREItems.VOCALITE_CHESTPLATE);
                    entries.addAfter(PREItems.VOCALITE_CHESTPLATE, PREItems.VOCALITE_LEGGINGS);
                    entries.addAfter(PREItems.VOCALITE_LEGGINGS, PREItems.VOCALITE_BOOTS);
                    //endregion
                    //region Electrum Armor
                    entries.addBefore(PREItems.VOCALITE_HELMET, PREItems.ELECTRUM_HELMET);
                    entries.addAfter(PREItems.ELECTRUM_HELMET, PREItems.ELECTRUM_CHESTPLATE);
                    entries.addAfter(PREItems.ELECTRUM_CHESTPLATE, PREItems.ELECTRUM_LEGGINGS);
                    entries.addAfter(PREItems.ELECTRUM_LEGGINGS, PREItems.ELECTRUM_BOOTS);
                    //endregion
                    //region Alumnabrass Armor
                    entries.addBefore(PREItems.ELECTRUM_HELMET, PREItems.ALUMNABRASS_HELMET);
                    entries.addAfter(PREItems.ALUMNABRASS_HELMET, PREItems.ALUMNABRASS_CHESTPLATE);
                    entries.addAfter(PREItems.ALUMNABRASS_CHESTPLATE, PREItems.ALUMNABRASS_LEGGINGS);
                    entries.addAfter(PREItems.ALUMNABRASS_LEGGINGS, PREItems.ALUMNABRASS_BOOTS);
                    //endregion
                    //region Brass Armor
                    entries.addBefore(Items.DIAMOND_HELMET, PREItems.BRASS_HELMET);
                    entries.addAfter(PREItems.BRASS_HELMET, PREItems.BRASS_CHESTPLATE);
                    entries.addAfter(PREItems.BRASS_CHESTPLATE, PREItems.BRASS_LEGGINGS);
                    entries.addAfter(PREItems.BRASS_LEGGINGS, PREItems.BRASS_BOOTS);
                    //endregion
                    //region Rose Gold Armor
                    entries.addAfter(Items.DIAMOND_BOOTS, PREItems.ROSE_GOLD_HELMET);
                    entries.addAfter(PREItems.ROSE_GOLD_HELMET, PREItems.ROSE_GOLD_CHESTPLATE);
                    entries.addAfter(PREItems.ROSE_GOLD_CHESTPLATE, PREItems.ROSE_GOLD_LEGGINGS);
                    entries.addAfter(PREItems.ROSE_GOLD_LEGGINGS, PREItems.ROSE_GOLD_BOOTS);
                    //endregion
                    //region Copper Armor
                    entries.addBefore(Items.IRON_HELMET, PREItems.COPPER_HELMET);
                    entries.addAfter(PREItems.COPPER_HELMET, PREItems.COPPER_CHESTPLATE);
                    entries.addAfter(PREItems.COPPER_CHESTPLATE, PREItems.COPPER_LEGGINGS);
                    entries.addAfter(PREItems.COPPER_LEGGINGS, PREItems.COPPER_BOOTS);
                    //endregion
                    //region Swords
                    entries.addBefore(Items.DIAMOND_SWORD, PREItems.BRASS_SWORD);
                    entries.addAfter(Items.DIAMOND_SWORD, PREItems.ROSE_GOLD_SWORD);
                    entries.addBefore(Items.NETHERITE_SWORD, PREItems.VOCALITE_SWORD);
                    entries.addBefore(PREItems.VOCALITE_SWORD, PREItems.ELECTRUM_SWORD);
                    entries.addBefore(PREItems.ELECTRUM_SWORD, PREItems.ALUMNABRASS_SWORD);
                    entries.addBefore(Items.IRON_SWORD, PREItems.COPPER_SWORD);
                    //endregion
                    entries.addAfter(Items.MACE, PREItems.ALUMNABRASS_SPEAR);
                    entries.addAfter(PREItems.ALUMNABRASS_SPEAR, PREItems.ELECTRUM_SPEAR);
                    entries.addAfter(PREItems.ELECTRUM_SPEAR, PREItems.NETHERITE_SPEAR);
                    entries.addAfter(PREItems.NETHERITE_SPEAR, PREItems.VOCALITE_SPEAR);
                });
        //endregion
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS)
                .register(entries -> {
                    entries.addAfter(Items.ENDER_EYE, PREItems.TRANSENDER);
                    entries.addAfter(PREItems.TRANSENDER, PREItems.OLD_CLOCK);
                    entries.addAfter(PREItems.OLD_CLOCK, PREItems.ALUMNABRASS_BRUSH);
                    entries.addBefore(Items.NETHERITE_SHOVEL, PREItems.VOCALITE_SHOVEL);
                    entries.addAfter(PREItems.VOCALITE_SHOVEL, PREItems.VOCALITE_PICKAXE);
                    entries.addAfter(PREItems.VOCALITE_PICKAXE, PREItems.VOCALITE_AXE);
                    entries.addAfter(PREItems.VOCALITE_AXE, PREItems.VOCALITE_HOE);
                    entries.addBefore(PREItems.VOCALITE_SHOVEL, PREItems.ELECTRUM_SHOVEL);
                    entries.addAfter(PREItems.ELECTRUM_SHOVEL, PREItems.ELECTRUM_PICKAXE);
                    entries.addAfter(PREItems.ELECTRUM_PICKAXE, PREItems.ELECTRUM_AXE);
                    entries.addAfter(PREItems.ELECTRUM_AXE, PREItems.ELECTRUM_HOE);
                    entries.addBefore(PREItems.ELECTRUM_SHOVEL, PREItems.ALUMNABRASS_SHOVEL);
                    entries.addAfter(PREItems.ALUMNABRASS_SHOVEL, PREItems.ALUMNABRASS_PICKAXE);
                    entries.addAfter(PREItems.ALUMNABRASS_PICKAXE, PREItems.ALUMNABRASS_AXE);
                    entries.addAfter(PREItems.ALUMNABRASS_AXE, PREItems.ALUMNABRASS_HOE);

                    entries.addAfter(Items.DIAMOND_HOE, PREItems.ROSE_GOLD_SHOVEL);
                    entries.addAfter(PREItems.ROSE_GOLD_SHOVEL, PREItems.ROSE_GOLD_PICKAXE);
                    entries.addAfter(PREItems.ROSE_GOLD_PICKAXE, PREItems.ROSE_GOLD_AXE);
                    entries.addAfter(PREItems.ROSE_GOLD_AXE, PREItems.ROSE_GOLD_HOE);
                    entries.addBefore(Items.DIAMOND_SHOVEL, PREItems.BRASS_SHOVEL);
                    entries.addAfter(PREItems.BRASS_SHOVEL, PREItems.BRASS_PICKAXE);
                    entries.addAfter(PREItems.BRASS_PICKAXE, PREItems.BRASS_AXE);
                    entries.addAfter(PREItems.BRASS_AXE, PREItems.BRASS_HOE);
                    entries.addBefore(Items.IRON_SHOVEL, PREItems.COPPER_SHOVEL);
                    entries.addAfter(PREItems.COPPER_SHOVEL, PREItems.COPPER_PICKAXE);
                    entries.addAfter(PREItems.COPPER_PICKAXE, PREItems.COPPER_AXE);
                    entries.addAfter(PREItems.COPPER_AXE, PREItems.COPPER_HOE);
                });
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS)
                .register(entries -> {
                    entries.addAfter(Items.GOLD_INGOT, PREItems.BRASS_INGOT);
                    entries.addAfter(PREItems.BRASS_INGOT, PREItems.ROSE_GOLD_INGOT);
                    entries.addBefore(Items.NETHERITE_INGOT, PREItems.ELECTRUM_INGOT);
                    entries.addBefore(PREItems.ELECTRUM_INGOT, PREItems.ALUMNABRASS_INGOT);
                    entries.addBefore(Items.NETHERITE_INGOT, PREItems.VOCALITE_INGOT);
                    entries.addBefore(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE, PREItems.BASIC_UPGRADE_SMITHING_TEMPLATE);
                });
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL)
                .register(entries -> {
                    entries.addAfter(Items.COMPOSTER, PREItems.RECYCLER);
                    entries.addAfter(Items.BLAST_FURNACE, PREItems.INDUCTION_FURNACE);
                });
        DefaultItemComponentEvents.MODIFY.register(context -> {
            context.modify(item -> item == Items.ENDER_EYE,
                    (builder, item) -> builder.add(DataComponentTypes.MAX_STACK_SIZE, 16));

            context.modify(item -> item == ALUMNABRASS_HELMET
                            || item == ALUMNABRASS_CHESTPLATE
                            || item == ALUMNABRASS_LEGGINGS
                            || item == ALUMNABRASS_BOOTS,
                    (builder, item) -> {
                        AttributeModifierSlot slot;
                        if (item == ALUMNABRASS_HELMET) slot = AttributeModifierSlot.HEAD;
                        else if (item == ALUMNABRASS_CHESTPLATE) slot = AttributeModifierSlot.CHEST;
                        else if (item == ALUMNABRASS_LEGGINGS) slot = AttributeModifierSlot.LEGS;
                        else slot = AttributeModifierSlot.FEET;

                        var existing = builder.getOrDefault(DataComponentTypes.ATTRIBUTE_MODIFIERS, AttributeModifiersComponent.builder().build());
                        var addition = createAlumnabrassArmorAttributes(slot);

                        builder.put(DataComponentTypes.ATTRIBUTE_MODIFIERS, merge(existing, addition));
                    });

            context.modify(item -> item == ELECTRUM_HELMET
                            || item == ELECTRUM_CHESTPLATE
                            || item == ELECTRUM_LEGGINGS
                            || item == ELECTRUM_BOOTS,
                    (builder, item) -> {
                        AttributeModifierSlot slot;
                        if (item == ELECTRUM_HELMET) slot = AttributeModifierSlot.HEAD;
                        else if (item == ELECTRUM_CHESTPLATE) slot = AttributeModifierSlot.CHEST;
                        else if (item == ELECTRUM_LEGGINGS) slot = AttributeModifierSlot.LEGS;
                        else slot = AttributeModifierSlot.FEET;

                        var existing = builder.getOrDefault(DataComponentTypes.ATTRIBUTE_MODIFIERS, AttributeModifiersComponent.builder().build());
                        var addition = createElectrumArmorAttributes(slot);

                        builder.put(DataComponentTypes.ATTRIBUTE_MODIFIERS, merge(existing, addition));
                    });

            context.modify(item -> item == VOCALITE_HELMET
                            || item == VOCALITE_CHESTPLATE
                            || item == VOCALITE_LEGGINGS
                            || item == VOCALITE_BOOTS,
                    (builder, item) -> {
                        AttributeModifierSlot slot;
                        if (item == VOCALITE_HELMET) slot = AttributeModifierSlot.HEAD;
                        else if (item == VOCALITE_CHESTPLATE) slot = AttributeModifierSlot.CHEST;
                        else if (item == VOCALITE_LEGGINGS) slot = AttributeModifierSlot.LEGS;
                        else slot = AttributeModifierSlot.FEET;

                        var existing = builder.getOrDefault(DataComponentTypes.ATTRIBUTE_MODIFIERS, AttributeModifiersComponent.builder().build());
                        var addition = createVocaliteArmorAttributes(slot);

                        builder.put(DataComponentTypes.ATTRIBUTE_MODIFIERS, merge(existing, addition));
                    });

            context.modify(item -> item == Items.NETHERITE_HELMET
                            || item == Items.NETHERITE_CHESTPLATE
                            || item == Items.NETHERITE_LEGGINGS
                            || item == Items.NETHERITE_BOOTS,
                    (builder, item) -> {
                        AttributeModifierSlot slot;
                        if (item == Items.NETHERITE_HELMET) slot = AttributeModifierSlot.HEAD;
                        else if (item == Items.NETHERITE_CHESTPLATE) slot = AttributeModifierSlot.CHEST;
                        else if (item == Items.NETHERITE_LEGGINGS) slot = AttributeModifierSlot.LEGS;
                        else slot = AttributeModifierSlot.FEET;

                        var existing = builder.getOrDefault(DataComponentTypes.ATTRIBUTE_MODIFIERS, AttributeModifiersComponent.builder().build());
                        var addition = createNetheriteArmorAttributes(slot);

                        builder.put(DataComponentTypes.ATTRIBUTE_MODIFIERS, merge(existing, addition));
                    });

            context.modify(item -> item == ALUMNABRASS_SWORD
                            || item == ALUMNABRASS_SHOVEL
                            || item == ALUMNABRASS_PICKAXE
                            || item == ALUMNABRASS_AXE
                            || item == ALUMNABRASS_HOE
                            || item == ALUMNABRASS_SPEAR,
                    (builder, item) -> {
                        var existing = builder.getOrDefault(DataComponentTypes.ATTRIBUTE_MODIFIERS, AttributeModifiersComponent.builder().build());
                        var addition = createAlumnabrassToolAttributes();

                        builder.put(DataComponentTypes.ATTRIBUTE_MODIFIERS, merge(existing, addition));
                    });

            context.modify(item -> item == ELECTRUM_SWORD
                            || item == ELECTRUM_SHOVEL
                            || item == ELECTRUM_PICKAXE
                            || item == ELECTRUM_AXE
                            || item == ELECTRUM_HOE
                            || item == ELECTRUM_SPEAR,
                    (builder, item) -> {
                        var existing = builder.getOrDefault(DataComponentTypes.ATTRIBUTE_MODIFIERS, AttributeModifiersComponent.builder().build());
                        var addition = createElectrumToolAttributes();

                        builder.put(DataComponentTypes.ATTRIBUTE_MODIFIERS, merge(existing, addition));
                    });

            context.modify(item -> item == VOCALITE_SWORD
                            || item == VOCALITE_SHOVEL
                            || item == VOCALITE_PICKAXE
                            || item == VOCALITE_AXE
                            || item == VOCALITE_HOE
                            || item == VOCALITE_SPEAR,
                    (builder, item) -> {
                        var existing = builder.getOrDefault(DataComponentTypes.ATTRIBUTE_MODIFIERS, AttributeModifiersComponent.builder().build());
                        var addition = createVocaliteToolAttributes();

                        builder.put(DataComponentTypes.ATTRIBUTE_MODIFIERS, merge(existing, addition));
                    });

            context.modify(item -> item == Items.NETHERITE_SWORD
                            || item == Items.NETHERITE_SHOVEL
                            || item == Items.NETHERITE_PICKAXE
                            || item == Items.NETHERITE_AXE
                            || item == Items.NETHERITE_HOE
                            || item == NETHERITE_SPEAR,
                    (builder, item) -> {
                        var existing = builder.getOrDefault(DataComponentTypes.ATTRIBUTE_MODIFIERS, AttributeModifiersComponent.builder().build());
                        var addition = createNetheriteToolAttributes();

                        builder.put(DataComponentTypes.ATTRIBUTE_MODIFIERS, merge(existing, addition));
                    });
            context.modify(Items.RECOVERY_COMPASS, builder ->
                    builder.add(
                            PREDataComponentTypes.PERSISTS_PLAYER_DEATH,
                            true
                    ).add(
                            PREDataComponentTypes.RECOVERS_INVENTORY,
                            true
                    )
            );
            context.modify(Items.FLINT_AND_STEEL, builder -> builder.add(
                    DataComponentTypes.ENCHANTABLE,
                    new EnchantableComponent(5)
            ));
            context.modify(Items.MACE, builder -> builder.add(
                    DataComponentTypes.REPAIRABLE,
                    new RepairableComponent(
                            RegistryEntryList.of(
                                    Items.NETHERITE_INGOT.getRegistryEntry()
                            )
                    )).add(DataComponentTypes.ATTRIBUTE_MODIFIERS, createNetheriteMaceAttributes())
                    .put(DataComponentTypes.RARITY, Rarity.PROGRESSION_REENVISIONED_RELIC));
            context.modify(Items.ENDER_EYE, builder -> builder.add(
                    DataComponentTypes.RARITY,
                    Rarity.UNCOMMON
            ));
            context.modify(Items.RECOVERY_COMPASS, builder ->
                    builder.put(DataComponentTypes.RARITY, Rarity.PROGRESSION_REENVISIONED_RELIC));
            context.modify(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE, builder -> builder.add(
                    DataComponentTypes.RARITY,
                    Rarity.RARE
            ));
        });


        LootTableEvents.MODIFY.register((key, tableBuilder, source, registries) -> {
            if (source.isBuiltin()) {
                if (key.equals(LootTables.ANCIENT_CITY_CHEST)) {
                    tableBuilder.pool(
                            LootPool.builder()
                                     .with(ItemEntry.builder(PREItems.BASIC_UPGRADE_SMITHING_TEMPLATE))
                                     .conditionally(RandomChanceLootCondition.builder(0.5f))
                                     .build()
                    );
                } else if (key.equals(LootTables.TRAIL_RUINS_RARE_ARCHAEOLOGY)) {
                    tableBuilder.modifyPools(pool -> pool.with(ItemEntry.builder(PREItems.ALUMNABRASS_BRUSH).weight(1)));
                } else if (key.equals(LootTables.ABANDONED_MINESHAFT_CHEST)) {
                    tableBuilder.pool(
                            LootPool.builder()
                                    .with(ItemEntry.builder(PREItems.BASIC_UPGRADE_SMITHING_TEMPLATE))
                                    .conditionally(RandomChanceLootCondition.builder(0.33f))
                                    .build()
                    );
                } else if (key.equals(LootTables.NETHER_BRIDGE_CHEST)) {

                    tableBuilder.pool(
                            LootPool.builder()
                                    .with(ItemEntry.builder(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE))
                                    .conditionally(RandomChanceLootCondition.builder(0.025f))
                                    .build()
                    );
                    tableBuilder.pool(
                            LootPool.builder()
                                    .with(ItemEntry.builder(Items.NETHERITE_SCRAP))
                                    .conditionally(RandomChanceLootCondition.builder(0.025f))
                                    .build()
                    );
                    tableBuilder.pool(
                            LootPool.builder()
                                    .with(ItemEntry.builder(Items.NETHERITE_INGOT))
                                    .conditionally(RandomChanceLootCondition.builder(0.025f))
                                    .build()
                    );
                    tableBuilder.pool(
                            LootPool.builder()
                                    .with(ItemEntry.builder(Items.ANCIENT_DEBRIS))
                                    .conditionally(RandomChanceLootCondition.builder(0.025f))
                                    .build()
                    );
                } else if (key.equals(LootTables.BASTION_TREASURE_CHEST)) {

                    tableBuilder.pool(
                            LootPool.builder()
                                    .with(ItemEntry.builder(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE))
                                    .conditionally(RandomChanceLootCondition.builder(0.25f))
                                    .build()
                    );
                    tableBuilder.pool(
                            LootPool.builder()
                                    .with(ItemEntry.builder(Items.NETHERITE_SCRAP))
                                    .conditionally(RandomChanceLootCondition.builder(0.25f))
                                    .build()
                    );
                    tableBuilder.pool(
                            LootPool.builder()
                                    .with(ItemEntry.builder(Items.NETHERITE_INGOT))
                                    .conditionally(RandomChanceLootCondition.builder(0.25f))
                                    .build()
                    );
                    tableBuilder.pool(
                            LootPool.builder()
                                    .with(ItemEntry.builder(Items.ANCIENT_DEBRIS))
                                    .conditionally(RandomChanceLootCondition.builder(0.25f))
                                    .build()
                    );
                }
            }
        });
    }
}
