package net.lixir.pre.common.init.tag;

import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class PREConventionalItemTags {
    public static final TagKey<Item> VOCALITE_INGOTS = of("ingots/vocalite");
    public static final TagKey<Item> ALUMNABRASS_INGOTS = of("ingots/alumnabrass");
    public static final TagKey<Item> ELECTRUM_INGOTS = of("ingots/electrum");
    public static final TagKey<Item> BRASS_INGOTS = of("ingots/brass");
    public static final TagKey<Item> ROSE_GOLD_INGOTS = of("ingots/rose_gold");


    private static TagKey<Item> of(String name) {
        return TagKey.of(RegistryKeys.ITEM, Identifier.of("c", name));
    }
}
