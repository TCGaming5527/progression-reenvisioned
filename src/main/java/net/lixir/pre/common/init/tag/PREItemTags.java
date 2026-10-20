package net.lixir.pre.common.init.tag;

import net.lixir.pre.common.ProgressionReEnvisioned;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;

public class PREItemTags {
    public static final TagKey<Item> BANNED = of("banned");
    public static final TagKey<Item> REPAIRS_ELECTRUM_ARMOR = of("repairs_electrum_armor");
    public static final TagKey<Item> REPAIRS_VOCALITE_ARMOR = of("repairs_vocalite_armor");
    public static final TagKey<Item> REPAIRS_ALUMNABRASS_ARMOR = of("repairs_alumnabrass_armor");
    public static final TagKey<Item> REPAIRS_ROSE_GOLD_ARMOR = of("repairs_rose_gold_armor");
    public static final TagKey<Item> REPAIRS_BRASS_ARMOR = of("repairs_brass_armor");
    public static final TagKey<Item> REPAIRS_COPPER_ARMOR = of("repairs_copper_armor");
    public static final TagKey<Item> BRASS_TOOL_MATERIALS = of("brass_tool_materials");
    public static final TagKey<Item> ROSE_GOLD_TOOL_MATERIALS = of("rose_gold_tool_materials");
    public static final TagKey<Item> INDUCTION_SMELTING_BASE = of("induction_smelting_base");
    public static final TagKey<Item> SPEARS = of("spears");
    public static final TagKey<Item> SPEAR_ENCHANTABLE = of("enchantable/spear");
    public static final TagKey<Item> ALUMNABRASS_TOOL_MATERIALS = of("alumnabrass_tool_materials");
    public static final TagKey<Item> VOCALITE_TOOL_MATERIALS = of("vocalite_tool_materials");
    public static final TagKey<Item> ELECTRUM_TOOL_MATERIALS = of("electrum_tool_materials");
    public static final TagKey<Item> COPPER_TOOL_MATERIALS = of("copper_tool_materials");
    public static final TagKey<Item> BASIC_UPGRADE_SMITHING_TEMPLATE_BLOCKS = of("basic_upgrade_smithing_template_blocks");
    public static final TagKey<Item> INDUCTION_FURNACE_UPGRADE = of("induction_furnace_upgrade");

    private static TagKey<Item> of(String name) {
        return TagKey.of(RegistryKeys.ITEM, ProgressionReEnvisioned.id(name));
    }
}
