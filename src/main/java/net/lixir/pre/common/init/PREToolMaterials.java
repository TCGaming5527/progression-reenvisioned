package net.lixir.pre.common.init;

import net.lixir.pre.common.init.tag.PREBlockTags;
import net.lixir.pre.common.init.tag.PREItemTags;
import net.minecraft.item.ToolMaterial;

public interface PREToolMaterials {
    ToolMaterial ELECTRUM = new ToolMaterial(PREBlockTags.INCORRECT_FOR_ELECTRUM_TOOL, 2031, 9.0F, 4.0F, 15, PREItemTags.ELECTRUM_TOOL_MATERIALS);
    ToolMaterial VOCALITE = new ToolMaterial(PREBlockTags.INCORRECT_FOR_VOCALITE_TOOL, 2031, 9.0F, 4.0F, 15, PREItemTags.VOCALITE_TOOL_MATERIALS);
    ToolMaterial ALUMNABRASS = new ToolMaterial(PREBlockTags.INCORRECT_FOR_ALUMNABRASS_TOOL, 2031, 9.0F, 4.0F, 15, PREItemTags.ALUMNABRASS_TOOL_MATERIALS);
    ToolMaterial BRASS = new ToolMaterial(PREBlockTags.INCORRECT_FOR_BRASS_TOOL, 1561, 8.0F, 3.0F, 10, PREItemTags.BRASS_TOOL_MATERIALS);
    ToolMaterial COPPER = new ToolMaterial(PREBlockTags.INCORRECT_FOR_COPPER_TOOL, 225, 7.0F, 2.0F, 14, PREItemTags.COPPER_TOOL_MATERIALS);
    ToolMaterial ROSE_GOLD = new ToolMaterial(PREBlockTags.INCORRECT_FOR_ROSE_GOLD_TOOL, 1561, 8.0F, 3.0F, 10, PREItemTags.ROSE_GOLD_TOOL_MATERIALS);
}
