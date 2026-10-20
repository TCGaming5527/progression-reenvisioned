package net.lixir.pre.common.util;

import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.registry.tag.TagKey;
import org.jetbrains.annotations.NotNull;

public final class RecipeUtil {
    private RecipeUtil() {

    }

    public static RegistryEntryList<Item> getItemsFromTag(
            RegistryWrapper.@NotNull WrapperLookup registries,
            TagKey<Item> tag
    ) {
        RegistryWrapper.Impl<Item> itemRegistry = registries.getOrThrow(RegistryKeys.ITEM);

        return itemRegistry.getOptional(tag)
                .orElseThrow(() -> new IllegalStateException("Tag not found: " + tag));
    }
}
