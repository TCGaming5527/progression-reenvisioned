package net.lixir.pre.client.gui.hud;

import com.mojang.blaze3d.systems.RenderSystem;
import net.lixir.pre.common.util.easing.TransitionMath;
import net.minecraft.block.Block;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.List;


public abstract class BlockSpeechHud extends SpeechHud {
    private final Block block;

    public BlockSpeechHud(
            Block block,
            List<Text> speech,
            int speechTickDuration,
            int textColor,
            int gradientColorStart,
            int gradientColorEnd,
            float fadeInPercentage,
            float fadeOutPercentage
    ) {
        super(speech, speechTickDuration, textColor, gradientColorStart, gradientColorEnd, fadeInPercentage, fadeOutPercentage);
        this.block = block;

    }

    @Override
    public float getTextWobble() {
        return 0.01f;
    }

    public void onHudRender(DrawContext context, RenderTickCounter tickCounter) {
        if (!isActive())
            return;

        client.gameRenderer.renderBlur();
        MinecraftClient client = MinecraftClient.getInstance();
        int width = client.getWindow().getScaledWidth();
        int height = client.getWindow().getScaledHeight();

        MatrixStack matrices = context.getMatrices();
        int labelX = (width / 2) - 16;
        int labelY = (height / 2) - 32;
        int labelYOffset = (int) (Math.sin(getSpeechTicks() * 0.1) * 2);

        matrices.push();
        matrices.translate(0,0,100f);
        RenderSystem.setShaderColor(1f,1f,1f, getCurrentFade());
        context.drawGuiTexture(RenderLayer::getGuiTextured, getInstructionsTexture(), labelX, (labelY + labelYOffset), 32, 32, TransitionMath.fadeAlpha(0xFFFFFFFF, getCurrentFade()));
        RenderSystem.setShaderColor(1f,1f,1f,1f);
        matrices.pop();

        super.onHudRender(context, tickCounter);
    }

    public Identifier getBlockIdentifier() {
        return Registries.BLOCK.getId(block);
    }

    public Identifier getInstructionsTexture() {
        Identifier blockId = getBlockIdentifier();
        return Identifier.of(blockId.getNamespace(), "hud/speech/instructions/" + blockId.getPath());
    }
}
