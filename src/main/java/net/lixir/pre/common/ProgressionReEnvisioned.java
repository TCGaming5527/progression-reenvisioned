package net.lixir.pre.common;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.lixir.pre.common.init.*;
import net.lixir.pre.common.data.RecyclableItemDataLoader;
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
        PREAttachmentTypes.initialize();

    }

    public static @NotNull Identifier id(String str) {
        return Identifier.of(ID, str);
    }
}
