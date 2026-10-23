package net.lixir.pre.client.gui.hud;

import net.lixir.pre.client.PREClient;
import net.lixir.pre.common.init.PRESoundEvents;
import net.lixir.pre.common.util.easing.TransitionMath;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.client.util.Window;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static java.lang.Math.min;

public abstract class SpeechHud {
    public final MinecraftClient client;

    private final int gradientColorStart;
    private final int gradientColorEnd;

    private final float fadeInPercentage;
    private final float fadeOutPercentage;

    private final List<Text> speech;

    private int speechTicks = 0;
    private final int speechTickDuration;

    private final int textColor;

    private boolean active = false;

    protected SpeechHud(
            List<Text> speech,
            int speechTickDuration,
            int textColor,
            int gradientColorStart,
            int gradientColorEnd,
            float fadeInPercentage,
            float fadeOutPercentage
    ) {
        this.speech = speech;
        this.speechTickDuration = speechTickDuration;
        this.textColor = textColor;
        this.gradientColorStart = gradientColorStart;
        this.gradientColorEnd = gradientColorEnd;
        this.fadeInPercentage = fadeInPercentage;
        this.fadeOutPercentage = fadeOutPercentage;
        this.client = MinecraftClient.getInstance();
    }

    public void onHudRender(DrawContext context, RenderTickCounter tickCounter) {
        if (!active)
            return;
        renderSpeech(context, tickCounter);
    }

    private int lastCharsToShow = 0;

    private void renderSpeech(@NotNull DrawContext context, RenderTickCounter tickCounter) {
        ClientWorld world = client.world;
        if (world == null) return;
        TextRenderer textRenderer = client.textRenderer;
        Window window = client.getWindow();
        int width = window.getScaledWidth();
        int height = window.getScaledHeight();

        List<Text> speech = getSpeech();
        if (speech == null || speech.isEmpty())
            return;
        int lastSpeechIndex = speech.size() - 1;
        int speechIndex = Math.min(lastSpeechIndex, (int) (getSpeechProgress() * speech.size()));

        Text text = speech.get(speechIndex);
        int textX = (width / 2) - (Math.min(textRenderer.getWidth(text), 196) / 2);
        int textY = (height / 2) + textRenderer.fontHeight;
        int cursorX = textX;
        int cursorY = textY;

        float lineFraction = getSpeechProgress() * speech.size() - speechIndex;

        String rawString = text.getString();
        int charsToShow = (int) ((lineFraction * rawString.length()) * 2);
        charsToShow = Math.min(charsToShow, rawString.length());

        for (int i = 0; i < charsToShow; i++) {
            char c = rawString.charAt(i);
            boolean skipCharacter = c == '\\';
            String oneChar = String.valueOf(c);
            if (!skipCharacter) {
                int offset = (int) (Math.sin(getSpeechTicks() + (i * 16) * getTextWobble()) * 2);
                context.drawTextWithShadow(textRenderer,
                        Text.literal(oneChar),
                        cursorX,
                        cursorY + offset,
                        TransitionMath.fadeAlpha(getTextColor(), getCurrentFade()));
            }

            cursorX += textRenderer.getWidth(oneChar);
            if ((cursorX - textY > 196 && c == ' ') || skipCharacter) {
                cursorX = textX;
                int offset = textRenderer.fontHeight + 3;
                if (skipCharacter)
                    offset *= 2;
                cursorY += offset;
            }
        }
        if (charsToShow != lastCharsToShow && rawString.charAt(rawString.length()-1) != ' ')
            world.playSoundClient(PRESoundEvents.MISC_TYPE, SoundCategory.MASTER, 0.4f, 1.1f + ((world.random.nextFloat() - 0.5f) * 0.15f));

        context.fillGradient(0, 0, width, height, getCurrentGradientStartColor(), getCurrentGradientEndColor());
        lastCharsToShow = charsToShow;

        context.drawCenteredTextWithShadow(textRenderer, Text.translatable("gui.speech.skip", client.options.inventoryKey.getBoundKeyLocalizedText()), width / 2, height - height / 8, -1);
    }

    abstract float getTextWobble();

    public int getCurrentGradientEndColor() {
        return TransitionMath.fadeAlpha(gradientColorEnd, getCurrentFade());
    }

    public int getCurrentGradientStartColor() {
        return TransitionMath.fadeAlpha(gradientColorStart, getCurrentFade());
    }

    public float getCurrentFade() {
        return TransitionMath.transitionInOut(getSpeechProgress(), getFadeInPercentage(), getFadeOutPercentage());
    }

    public float getSpeechProgress() {
        return (float) getSpeechTicks() / getSpeechTickDuration();
    }


    public final void setActive(boolean active) {
        this.active = active;
    }

    public final boolean isActive() {
        return active;
    }

    public List<Text> getSpeech() {
        return speech;
    }

    public float getFadeInPercentage() {
        return fadeInPercentage;
    }

    public float getFadeOutPercentage() {
        return fadeOutPercentage;
    }

    public int getSpeechTicks() {
        return speechTicks;
    }

    public int getSpeechTickDuration() {
        return speechTickDuration;
    }

    public int getTextColor() {
        return textColor;
    }

    public void activate() {
        active = true;
        speechTicks = 0;
        lastCharsToShow = 0;
        PREClient.CURRENT_SPEECH = this;
    }

    public void tick(MinecraftClient client) {
        if (!active)
            return;
        speechTicks++;
        if (getSpeechTicks() == getSpeechTickDuration()) {
            onSpeechEnd();
        } else if (client.options.inventoryKey.wasPressed()) {
            while (client.options.inventoryKey.wasPressed()) {}
            onSpeechEnd();
        }
    }

    public void reset() {
        speechTicks = 0;
        active = false;
        lastCharsToShow = 0;
    }

    protected void onSpeechEnd() {
        reset();
    }
}
