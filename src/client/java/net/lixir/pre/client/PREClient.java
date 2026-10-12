package net.lixir.pre.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.lixir.pre.block.PREBlocks;
import net.lixir.pre.client.gui.hud.SpeechHud;
import net.lixir.pre.client.gui.hud.SpeechHudTypeRegistry;
import net.lixir.pre.client.gui.screen.ingame.ProgressionReEnvisionedHandledScreens;
import net.lixir.pre.client.network.PREClientNetwork;
import net.lixir.pre.client.render.HandTransformationRegistry;
import net.lixir.pre.client.render.HandTransformations;
import net.lixir.pre.client.render.armor.ArmorModelRegistry;
import net.lixir.pre.client.render.armor.ElectrumArmorModel;
import net.lixir.pre.client.render.armor.VocaliteArmorModel;
import net.lixir.pre.client.render.item.PREBooleanProperties;
import net.lixir.pre.item.HandTransformationTypes;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import static net.lixir.pre.ProgressionReEnvisioned.ID;


public class PREClient implements ClientModInitializer {
    @Nullable
    public static SpeechHud CURRENT_SPEECH = null;

    @Override
    public void onInitializeClient() {
        FabricLoader.getInstance().getModContainer(ID).ifPresent(container ->
                ResourceManagerHelper.registerBuiltinResourcePack(
                Identifier.of(ID, "pre_vanilla"),
                container,
                ResourcePackActivationType.DEFAULT_ENABLED
        ));
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getTranslucent(), PREBlocks.PLATFORM);
        PREBooleanProperties.initialize();
        ProgressionReEnvisionedHandledScreens.initialize();
        PREClientEventHooks.register();
        SpeechHudTypeRegistry.initialize();
        PREClientNetwork.initialize();
        EntityModelLayerRegistry.registerModelLayer(
                ElectrumArmorModel.LAYER_LOCATION,
                ElectrumArmorModel::getTexturedModelData
        );
        EntityModelLayerRegistry.registerModelLayer(
                VocaliteArmorModel.LAYER_LOCATION,
                VocaliteArmorModel::getTexturedModelData
        );
        HandTransformationRegistry.register(HandTransformationTypes.SPEAR, HandTransformations.SPEAR);
        ArmorModelRegistry.initialize();
        ArmorModelRegistry.renderAll();
    }
}
