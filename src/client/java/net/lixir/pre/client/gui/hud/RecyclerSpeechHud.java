package net.lixir.pre.client.gui.hud;

import net.lixir.pre.ProgressionReEnvisioned;
import net.lixir.pre.block.PREBlocks;
import net.minecraft.block.Block;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.List;

public class RecyclerSpeechHud extends BlockSpeechHud {
    public static final Identifier LAYER = ProgressionReEnvisioned.id("recycler_speech");

    public static final List<Text> SPEECH = List.of(
            Text.translatable("block." + ProgressionReEnvisioned.ID + ".recycler.speech.0"),
            Text.translatable("block." + ProgressionReEnvisioned.ID + ".recycler.speech.1"),
            Text.translatable("block." + ProgressionReEnvisioned.ID + ".recycler.speech.2"),
            Text.translatable("block." + ProgressionReEnvisioned.ID + ".recycler.speech.3"),
            Text.translatable("block." + ProgressionReEnvisioned.ID + ".recycler.speech.4"),
            Text.translatable("block." + ProgressionReEnvisioned.ID + ".recycler.speech.5"),
            Text.translatable("block." + ProgressionReEnvisioned.ID + ".recycler.speech.6")

    );

    public static final RecyclerSpeechHud INSTANCE = new RecyclerSpeechHud(
            PREBlocks.RECYCLER, SPEECH, 1300, 0xFFbbcfba, 0xD0163319, 0xE5404040, 0.01f, 0.01f
    );

    public RecyclerSpeechHud(Block block, List<Text> speech, int speechTickDuration, int textColor, int gradientColorStart, int gradientColorEnd, float fadeInPercentage, float fadeOutPercentage) {
        super(block, speech, speechTickDuration, textColor, gradientColorStart, gradientColorEnd, fadeInPercentage, fadeOutPercentage);
    }
}
