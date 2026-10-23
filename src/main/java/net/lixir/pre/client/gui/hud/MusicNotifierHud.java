package net.lixir.pre.client.gui.hud;

import easing.EasingTypes;
import easing.tween.TweenFunction;
import net.lixir.pre.common.ProgressionReEnvisioned;
import net.lixir.pre.common.util.EvictingList;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.registry.RegistryKey;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Objects;

public class MusicNotifierHud {
    public static final Identifier LAYER = ProgressionReEnvisioned.id("music_notifier");
    public static final MusicNotifierHud INSTANCE = new MusicNotifierHud();
    private static final HashMap<RegistryKey<World>, Identifier> TEXTURES = new HashMap<>();
    private static final EvictingList<Notifier> NOTIFIERS = new EvictingList<>(2);

    static {
        TEXTURES.put(World.OVERWORLD, ProgressionReEnvisioned.id("hud/music_notifier/overworld_music"));
        TEXTURES.put(World.NETHER, ProgressionReEnvisioned.id("hud/music_notifier/nether_music"));
        TEXTURES.put(World.END, ProgressionReEnvisioned.id("hud/music_notifier/end_music"));
    }

    protected MusicNotifierHud() {

    }

    public void addNotifier(SoundInstance instance) {
        var text = buildMusicText(instance);
        var artistText = buildMusicArtistText(instance);
        if (text == null || artistText == null) return;
        var notifier = new Notifier(text, artistText);
        NOTIFIERS.add(notifier);
    }

    public static String getMusicTranslationKey(SoundInstance instance) {
        MinecraftClient client = MinecraftClient.getInstance();
        ClientPlayerEntity player = client.player;
        if (player == null) return null;

        if (instance == null) return null;

        var sound = instance.getSound();
        if (sound == null) return null;

        Identifier id = sound.getIdentifier();
        if (id == null) return null;

        String path = id.getPath();
        if (path.contains(".")) {
            path = path.substring(0, path.indexOf('.'));
        }
        path = path.replace("/", ".");

        String key = id.getNamespace() + "." + path;

        String[] parts = key.split("\\.");
        int musicIndex = -1;

        for (int i = 0; i < parts.length; i++) {
            if (parts[i].equals("music")) {
                musicIndex = i;
                break;
            }
        }

        if (musicIndex > 0) {
            StringBuilder sb = new StringBuilder("music");
            for (int i = 0; i < parts.length; i++) {
                if (i != musicIndex) {
                    sb.append('.').append(parts[i]);
                }
            }
            key = sb.toString();
        }

        return key;
    }

    @Nullable
    public static Text buildMusicText(SoundInstance instance) {
        String key = getMusicTranslationKey(instance);
        return key == null ? null : Text.translatable(key);
    }

    @Nullable
    public static Text buildMusicArtistText(SoundInstance instance) {
        String key = getMusicTranslationKey(instance);
        return key == null ? null : Text.translatable(key + ".artist");
    }

    public void onHudRender(DrawContext context, RenderTickCounter tickCounter) {
        MinecraftClient client = MinecraftClient.getInstance();
        int width = client.getWindow().getScaledWidth();
        int height = client.getWindow().getScaledHeight();

        for (int i = 0; i < NOTIFIERS.size(); i++) {
            var notifier = NOTIFIERS.get(i);
            notifier.render(context, tickCounter, -63, 4 + i * 18);
        }
    }

    public void tick(MinecraftClient client) {
        for (Notifier notifier : NOTIFIERS)
            notifier.tick();
        NOTIFIERS.removeIf(notifier -> notifier.age >= Notifier.LIFETIME);
    }

    private static final class Notifier {
        private static final int LIFETIME = 200;
        private static final int TRANSITION_IN_TICKS = 10;
        private static final int TRANSITION_OUT_TICKS = 10;
        private final Text text;
        private final Text artistText;

        private int age = 0;
        public Notifier(Text text, Text artistText) {
            this.text = text;
            this.artistText = artistText;
        }

        public void render(
                @NotNull DrawContext context,
                RenderTickCounter tickCounter,
                int x,
                int y
        ) {
            MinecraftClient client = MinecraftClient.getInstance();
            ClientPlayerEntity player = client.player;
            if (player == null) return;
            ClientWorld world = player.clientWorld;

            TextRenderer textRenderer = client.textRenderer;
            MatrixStack matrices = context.getMatrices();
            matrices.push();
            float partial = tickCounter.getTickProgress(false);
            float animAge = age + partial;
            var transition = new TweenFunction(
                    TRANSITION_IN_TICKS / (float) LIFETIME,
                    TRANSITION_OUT_TICKS / (float) LIFETIME,
                    EasingTypes.EASE_IN_CUBIC,
                    EasingTypes.EASE_OUT_CUBIC
            );

            float var = transition.apply(animAge / LIFETIME);
            matrices.translate(67 * var, 0, 0);
            context.drawGuiTexture(
                    RenderLayer::getGuiTextured,
                    TEXTURES.get(world.getRegistryKey()),
                    x,
                    y,
                    65,
                    18
            );


            matrices.push();
            float floatOffset = (float) Math.sin(animAge * 0.15) * 2;
            matrices.translate(x + 56, y + 5 + floatOffset, 0);
            context.drawText(textRenderer, Text.literal("♪"), 0, 0, 0xFF55FF55, true);
            matrices.pop();

            matrices.pop();

            final int maxWidth = 56;
            final float minScale = 0.5f;
            final float maxScale = 0.75f;

            int titleWidth = textRenderer.getWidth(text);
            int artistWidth = textRenderer.getWidth(artistText);

            float titleScale = MathHelper.clamp((float) maxWidth / titleWidth, minScale, maxScale);
            float artistScale = MathHelper.clamp((float) maxWidth / artistWidth, minScale, maxScale);

            float totalHeight = textRenderer.fontHeight * titleScale + textRenderer.fontHeight * artistScale;

            matrices.push();
            matrices.translate(
                    x + 70 * var,
                    (y + 1) - totalHeight / 2f + 8,
                    0
            );

            matrices.push();
            matrices.scale(titleScale, titleScale, 1f);
            context.drawText(textRenderer, text, 0, 0, 0xFFFFFFFF, true);
            matrices.pop();

            matrices.translate(0, textRenderer.fontHeight * titleScale, 0);
            matrices.push();
            matrices.scale(artistScale, artistScale, 1f);
            context.drawText(textRenderer, artistText, 0, 0, 0xFFBBBBBB, true);
            matrices.pop();


            matrices.pop();
        }

        public void tick() {
            this.age++;
        }


        @Override
        public boolean equals(Object obj) {
            if (obj == this) return true;
            if (obj == null || obj.getClass() != this.getClass()) return false;
            var that = (Notifier) obj;
            return Objects.equals(this.text, that.text);
        }

        @Override
        public int hashCode() {
            return Objects.hash(text);
        }
    }
}
