package net.lixir.pre.client.gui.hud;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.lixir.pre.common.ProgressionReEnvisioned;
import net.lixir.pre.common.payload.EnderEyeSpeechPayload;
import net.lixir.pre.common.util.easing.TransitionMath;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.List;


public class EnderEyeSpeechHud extends SpeechHud {
    public static final Identifier LAYER = ProgressionReEnvisioned.id("ender_eye_speech");
    public static final List<Text> SPEECH = List.of(
            Text.translatable("item.minecraft.ender_eye.speech.0"),
            Text.translatable("item.minecraft.ender_eye.speech.1"),
            Text.translatable("item.minecraft.ender_eye.speech.2"),
            Text.translatable("item.minecraft.ender_eye.speech.3"),
            Text.translatable("item.minecraft.ender_eye.speech.4"),
            Text.translatable("item.minecraft.ender_eye.speech.5"),
            Text.translatable("item.minecraft.ender_eye.speech.6"),
            Text.translatable("item.minecraft.ender_eye.speech.7"),
            Text.translatable("item.minecraft.ender_eye.speech.8"),
            Text.translatable("item.minecraft.ender_eye.speech.9")
    );
    public static final List<Identifier> EYE_PIECE_TEXTURES = List.of(
            ProgressionReEnvisioned.id("hud/ender_eye_shatter/ender_eye_piece_0"),
            ProgressionReEnvisioned.id("hud/ender_eye_shatter/ender_eye_piece_1"),
            ProgressionReEnvisioned.id("hud/ender_eye_shatter/ender_eye_piece_2"),
            ProgressionReEnvisioned.id("hud/ender_eye_shatter/ender_eye_piece_3")
    );

    public static final EnderEyeSpeechHud INSTANCE = new EnderEyeSpeechHud(
        SPEECH, 1200, 0xFF902de0, 0xD0102010, 0xE5431A7D, 0.05f, 0.025f
    );

    public final ArrayList<EnderEyePiece> ENDER_EYE_PIECES = new ArrayList<>(4);
    public boolean brokeEnderEye = false;

    @Nullable
    public BlockPos strongholdPos = null;

    protected EnderEyeSpeechHud(List<Text> speech, int speechTickDuration, int textColor, int gradientColorStart, int gradientColorEnd, float fadeInPercentage, float fadeOutPercentage) {
        super(speech, speechTickDuration, textColor, gradientColorStart, gradientColorEnd, fadeInPercentage, fadeOutPercentage);
    }

    @Override
    float getTextWobble() {
        return 0.05f;
    }

    public  MutableText getCoordinateText() {
        return Text.literal("X=" + strongholdPos.getX() + ", Y=" + strongholdPos.getY()  + ", Z=" + strongholdPos.getZ());
    }

    public void onHudRender(DrawContext context, RenderTickCounter tickCounter) {
        if (!isActive() || strongholdPos == null)
            return;
        client.gameRenderer.renderBlur();
        MinecraftClient client = MinecraftClient.getInstance();
        int width = client.getWindow().getScaledWidth();
        int height = client.getWindow().getScaledHeight();

        MatrixStack matrices = context.getMatrices();

        int enderEyeX = (width / 2) - 8;
        int enderEyeY = (height / 2) - 22;
        int enderEyeYOffset = (int) (Math.sin(getSpeechTicks() * 0.1) * 2);
        if (TransitionMath.transitionInOut(getSpeechProgress(), 0, getFadeOutPercentage()) == 1) {
            matrices.push();
            RenderSystem.setShaderColor(1f,1f,1f, TransitionMath.transitionInOut(getSpeechProgress(), getFadeInPercentage(), 0));
            context.drawItem(Items.ENDER_EYE.getDefaultStack(), enderEyeX, (enderEyeY + enderEyeYOffset));
            matrices.scale(2f, 2f, 2f);
            RenderSystem.setShaderColor(1f,1f,1f,1f);
            matrices.pop();
        } else if (!brokeEnderEye && getSpeechTicks() > 0) {
            breakEnderEye(enderEyeX, enderEyeY);
        }
        for (EnderEyePiece piece : ENDER_EYE_PIECES) {
            piece.render(context, tickCounter);
        }
        super.onHudRender(context, tickCounter);
    }

    @Override
    public List<Text> getSpeech() {
        List<Text> speech = new ArrayList<>(SPEECH);
        speech.add(getCoordinateText());
        return speech;
    }

    private void breakEnderEye(int enderEyeX, int enderEyeY) {
        MinecraftClient client = MinecraftClient.getInstance();
        ClientWorld world = client.world;
        if (world == null)
            return;
        PlayerEntity player = client.player;
        if (player == null)
            return;
        Vec3d pos = player.getPos();
        Random random = world.random;
        brokeEnderEye = true;
        for (int i = 0; i < 4; i++) {
            Vector2f velocity = new Vector2f(
                    (random.nextFloat() - 0.5f) * 2f,
                    (random.nextFloat()) * 2f
            );
            Identifier texture = EYE_PIECE_TEXTURES.get(random.nextBetween(0, EYE_PIECE_TEXTURES.size() - 1));
            EnderEyePiece enderEyePiece = new EnderEyePiece(new Vector2f((float) enderEyeX, (float) enderEyeY), velocity, texture, 140);
            ENDER_EYE_PIECES.add(enderEyePiece);
        }
        world.playSoundClient(SoundEvents.ENTITY_ENDER_EYE_DEATH, SoundCategory.PLAYERS, 1f, 1f);
    }

    public void onPayloadReceived(@NotNull EnderEyeSpeechPayload payload, ClientPlayNetworking.Context ctx) {
        activate();
        brokeEnderEye = false;
        strongholdPos = payload.strongholdPos();
    }

    public void tick(MinecraftClient client) {
        for (EnderEyePiece piece : ENDER_EYE_PIECES)
            piece.tick();
        ENDER_EYE_PIECES.removeIf(eyePiece -> eyePiece.age <= 0);
        super.tick(client);
    }

    @Override
    protected void onSpeechEnd() {
        super.onSpeechEnd();
        client.player.sendMessage(getCoordinateText().formatted(Formatting.DARK_PURPLE), false);
        strongholdPos = null;
    }

    public static class EnderEyePiece {
        private final Vector2f pos;
        private final Vector2f velocity;
        private final Identifier texture;
        private final int lifetime;
        private int age;

        public float getX() {
            return pos.x;
        }

        public float getY() {
            return pos.y;
        }

        private EnderEyePiece(Vector2f pos, Vector2f velocity, Identifier texture, int lifetime) {
            this.pos = pos;
            this.velocity = velocity;
            this.texture = texture;
            this.lifetime = lifetime;
            this.age = lifetime;
        }

        public void tick() {
            this.velocity.mul(0.98f, 0.98f);
            this.age--;
        }

        public void render(@NotNull DrawContext context, RenderTickCounter tickCounter) {
            this.pos.add(velocity.x, velocity.y + 0.8f);
            context.drawGuiTexture(RenderLayer::getGuiTextured, texture, (int) pos.x, (int) pos.y, 10, 10);
        }
    }
}
