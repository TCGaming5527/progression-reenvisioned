package net.lixir.pre.common.init.tag;

import net.lixir.pre.common.ProgressionReEnvisioned;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;

public interface PREItemTags {
    TagKey<Item> BANNED = of("banned");
    TagKey<Item> REPAIRS_ELECTRUM_ARMOR = of("repairs_electrum_armor");
    TagKey<Item> REPAIRS_VOCALITE_ARMOR = of("repairs_vocalite_armor");
    TagKey<Item> REPAIRS_ALUMNABRASS_ARMOR = of("repairs_alumnabrass_armor");
    TagKey<Item> REPAIRS_ROSE_GOLD_ARMOR = of("repairs_rose_gold_armor");
    TagKey<Item> REPAIRS_BRASS_ARMOR = of("repairs_brass_armor");
    TagKey<Item> REPAIRS_COPPER_ARMOR = of("repairs_copper_armor");
    TagKey<Item> BRASS_TOOL_MATERIALS = of("brass_tool_materials");
    TagKey<Item> ROSE_GOLD_TOOL_MATERIALS = of("rose_gold_tool_materials");
    TagKey<Item> INDUCTION_SMELTING_BASE = of("induction_smelting_base");
    TagKey<Item> SPEARS = of("spears");
    TagKey<Item> SPEAR_ENCHANTABLE = of("enchantable/spear");
    TagKey<Item> ALUMNABRASS_TOOL_MATERIALS = of("alumnabrass_tool_materials");
    TagKey<Item> VOCALITE_TOOL_MATERIALS = of("vocalite_tool_materials");
    TagKey<Item> ELECTRUM_TOOL_MATERIALS = of("electrum_tool_materials");
    TagKey<Item> COPPER_TOOL_MATERIALS = of("copper_tool_materials");
    TagKey<Item> BASIC_UPGRADE_SMITHING_TEMPLATE_BLOCKS = of("basic_upgrade_smithing_template_blocks");
    TagKey<Item> INDUCTION_FURNACE_UPGRADE = of("induction_furnace_upgrade");

    static TagKey<Item> of(String name) {
        return TagKey.of(RegistryKeys.ITEM, ProgressionReEnvisioned.id(name));
    }
}
