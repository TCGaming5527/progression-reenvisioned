package net.lixir.pre.registry.tag;

import net.lixir.pre.ProgressionReEnvisioned;
import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;

public class PREBlockTags {
    public static final TagKey<Block> MINEABLE_ANY_PICKAXE = of("mineable/any_pickaxe");
    public static final TagKey<Block> INCORRECT_FOR_ELECTRUM_TOOL = of("incorrect_for_electrum_tool");
    public static final TagKey<Block> INCORRECT_FOR_ROSE_GOLD_TOOL = of("incorrect_for_rose_gold_tool");
    public static final TagKey<Block> INCORRECT_FOR_BRASS_TOOL = of("incorrect_for_brass_tool");
    public static final TagKey<Block> INCORRECT_FOR_COPPER_TOOL = of("incorrect_for_copper_tool");
    public static final TagKey<Block> INCORRECT_FOR_ALUMNABRASS_TOOL = of("incorrect_for_alumnabrass_tool");
    public static final TagKey<Block> INCORRECT_FOR_VOCALITE_TOOL = of("incorrect_for_vocalite_tool");

    private static TagKey<Block> of(String name) {
        return TagKey.of(RegistryKeys.BLOCK, ProgressionReEnvisioned.id(name));
    }
}
