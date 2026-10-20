package net.lixir.pre.client.gui.hud;

import easing.EasingTypes;
import easing.tween.TweenFunction;
import lombok.val;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.lixir.pre.common.ProgressionReEnvisioned;
import net.lixir.pre.common.payload.ItemPickupNotifierPayload;
import net.lixir.pre.common.util.EvictingList;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.Item;
import net.minecraft.text.MutableText;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

public class ItemPickupNotifierHud {
    public static final Identifier LAYER = ProgressionReEnvisioned.id("item_pickup_notifier");
    public static final ItemPickupNotifierHud INSTANCE = new ItemPickupNotifierHud();
    private static final Identifier TEXTURE = ProgressionReEnvisioned.id("hud/item_pickup_notifier");
    private static final EvictingList<Notifier> NOTIFIERS = new EvictingList<>(6);

    protected ItemPickupNotifierHud() {

    }

    public void onPayloadReceived(@NotNull ItemPickupNotifierPayload payload, ClientPlayNetworking.Context ctx) {
        val item = payload.item();
        val count = payload.count();
        val notifier = new Notifier(item, count);
        NOTIFIERS.add(notifier);
    }

    public void onHudRender(DrawContext context, RenderTickCounter tickCounter) {
        MinecraftClient client = MinecraftClient.getInstance();
        int width = client.getWindow().getScaledWidth();
        int height = client.getWindow().getScaledHeight();

        for (int i = 0; i < NOTIFIERS.size(); i++) {
            val notifier = NOTIFIERS.get(i);
            notifier.render(context, tickCounter, -100, -6 + (height - 16) - i * 18);
        }
    }

    public void tick(MinecraftClient client) {
        for (Notifier notifier : NOTIFIERS)
            notifier.tick();
        NOTIFIERS.removeIf(notifier -> notifier.age >= Notifier.LIFETIME);
    }

    private static final class Notifier {
        private static final int LIFETIME = 100;
        private static final int TRANSITION_IN_TICKS = 10;
        private static final int TRANSITION_OUT_TICKS = 10;
        private final Item item;
        private final int count;
        private int age = 0;

        private Notifier(Item item, int count) {
            this.item = item;
            this.count = count;
        }

        public void render(
                @NotNull DrawContext context,
                RenderTickCounter tickCounter,
                int x,
                int y
        ) {
            MinecraftClient client = MinecraftClient.getInstance();
            TextRenderer textRenderer = client.textRenderer;
            MatrixStack matrices = context.getMatrices();
            matrices.push();
            float partial = tickCounter.getTickProgress(false);
            float animAge = age + partial;
            val transition = new TweenFunction(
                    TRANSITION_IN_TICKS / (float) LIFETIME,
                    TRANSITION_OUT_TICKS / (float) LIFETIME,
                    EasingTypes.EASE_IN_CUBIC,
                    EasingTypes.EASE_OUT_CUBIC
            );
            float val = transition.apply(animAge / LIFETIME);
            matrices.translate(102 * val, 0, 0);
            context.drawGuiTexture(
                    RenderLayer::getGuiTextured,
                    TEXTURE,
                    x,
                    y,
                    100,
                    16
            );
            context.drawItem(item.getDefaultStack(), x, y);


            matrices.pop();
            MutableText itemName = item.getName().copy();
            if (count > 1) {
                itemName.append(Text.literal(" (" + count + ")"));
            }
            final int maxWidth = 83;
            final float minScaleBeforeWrap = 0.75f;

            int rawWidth = textRenderer.getWidth(itemName);

            float scaling = Math.min(0.9f, (float) maxWidth / rawWidth);

            int wrapWidth = maxWidth;

            if (scaling < minScaleBeforeWrap) {
                scaling = minScaleBeforeWrap;

                wrapWidth = (int)(maxWidth / scaling);
            }

            List<OrderedText> lines = textRenderer.wrapLines(itemName, wrapWidth);

            matrices.push();
            matrices.translate(
                    x + 18 + 102 * val,
                    y - ((textRenderer.fontHeight * lines.size() * scaling) / 2f) + 8,
                    0
            );
            matrices.scale(scaling, scaling, 1f);

            int yOffset = 0;
            for (OrderedText line : lines) {
                context.drawText(textRenderer, line, 0, yOffset, 0xFFFFFFFF, true);
                yOffset += textRenderer.fontHeight;
            }

            matrices.pop();

        }

        public void tick() {
            this.age++;
        }

        public Item item() {
            return item;
        }

        public int count() {
            return count;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) return true;
            if (obj == null || obj.getClass() != this.getClass()) return false;
            var that = (Notifier) obj;
            return Objects.equals(this.item, that.item) &&
                    this.count == that.count;
        }

        @Override
        public int hashCode() {
            return Objects.hash(item, count);
        }

        @Override
        public String toString() {
            return "Notifier[" +
                    "item=" + item + ", " +
                    "count=" + count + ']';
        }

    }
}
