package net.lixir.pre;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.EnchantmentEvents;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.util.TriState;
import net.lixir.pre.block.PREBlocks;
import net.lixir.pre.block.entity.PREBlockEntityTypes;
import net.lixir.pre.component.PREDataComponentTypes;
import net.lixir.pre.data.RecyclableItemDataLoader;
import net.lixir.pre.enchantment.PREEnchantments;
import net.lixir.pre.entity.attribute.PREEntityAttributes;
import net.lixir.pre.entity.effect.PREStatusEffects;
import net.lixir.pre.item.PREItems;
import net.lixir.pre.loot.context.PRELootContextTypes;
import net.lixir.pre.network.PRENetwork;
import net.lixir.pre.recipe.PRERecipeSerializers;
import net.lixir.pre.recipe.PRERecipeTypes;
import net.lixir.pre.registry.tag.PREItemTags;
import net.lixir.pre.screen.PREScreenHandlerTypes;
import net.lixir.pre.sound.PRESoundEvents;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProgressionReEnvisioned implements ModInitializer {
    public static final String ID = "pre";
    public static final Logger LOGGER = LoggerFactory.getLogger(ID);

    @Override
    public void onInitialize() {
        ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(RecyclableItemDataLoader.INSTANCE);
        PREEvents.initialize();
        PREItems.initialize();
        PRENetwork.initialize();
        PREBlocks.initialize();
        PRESoundEvents.initialize();
        PRELootContextTypes.initialize();
        PREBlockEntityTypes.initialize();
        PRERecipeSerializers.initialize();
        PREStatusEffects.initialize();
        PRERecipeTypes.initialize();
        PREScreenHandlerTypes.initialize();
        PREEntityAttributes.initialize();
        PREDataComponentTypes.initialize();
        EnchantmentEvents.ALLOW_ENCHANTING.register((registryEntry, itemStack, enchantingContext) -> {
            if (itemStack.isIn(PREItemTags.SPEARS) &&
                    (registryEntry.matchesKey(Enchantments.SHARPNESS) ||
                            registryEntry.matchesKey(PREEnchantments.LUNGE) ||
                            registryEntry.matchesKey(Enchantments.FIRE_ASPECT) ||
                            registryEntry.matchesKey(Enchantments.SMITE) ||
                            registryEntry.matchesKey(Enchantments.BANE_OF_ARTHROPODS) ||
                            registryEntry.matchesKey(Enchantments.SHARPNESS) ||
                            registryEntry.matchesKey(Enchantments.KNOCKBACK) ||
                            registryEntry.matchesKey(Enchantments.LOOTING)
                    ))
                return TriState.TRUE;
            return TriState.DEFAULT;
        });
    }

    public static @NotNull Identifier id(String str) {
        return Identifier.of(ID, str);
    }
}
