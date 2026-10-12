package net.lixir.pre.client.gui.hud;

import net.lixir.pre.ProgressionReEnvisioned;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.GlobalPos;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Optional;
import java.util.function.Predicate;

public class CompassHud {
    protected CompassHud() {

    }
    private static final Identifier RECOVERY_COMPASS_TEXTURE = ProgressionReEnvisioned.id("hud/recovery_compass");
    private static final Identifier COMPASS_TEXTURE = ProgressionReEnvisioned.id("hud/compass");
    public static final Identifier LAYER = ProgressionReEnvisioned.id("compass");
    public static final CompassHud INSTANCE = new CompassHud();

    public void onHudRender(DrawContext context, RenderTickCounter tickCounter) {
        MinecraftClient client = MinecraftClient.getInstance();
        ClientPlayerEntity player = client.player;
        TextRenderer textRenderer = client.textRenderer;
        if (player == null) return;
        ArrayList<Tools> activeTools = new ArrayList<>();
        for (Tools tool : Tools.values()) {
            if (player.getInventory().contains(tool.predicate)) {
                activeTools.add(tool);
            }
        }

        final int x = 8;
        final int y = 8;

        int cursorX = x;
        MatrixStack matrices = context.getMatrices();

        int i = 0;
        for (Tools tool : activeTools) {
            Text text = tool.getText(client, player);

            matrices.push();
            matrices.scale(1.5f, 1.5f, 1f);
            context.drawGuiTexture(RenderLayer::getGuiTextured, tool.texture, cursorX, y, 32, 32);
            matrices.pop();

            matrices.push();
            int textWidth = textRenderer.getWidth(text);

            float scaling = 38f / (float) textWidth;
            float textX = (cursorX + (((float) textWidth / 2f) * scaling) - 10) + i * 20;
            float textY = y - (((float) textRenderer.fontHeight /2) * scaling) + 24;
            matrices.translate(textX, textY, 100f);

            matrices.scale(scaling, scaling, 1f);
            context.drawText(textRenderer, text, 0, 0, 0xFFFFFFFF, true);


            matrices.pop();

            i++;
            cursorX += 40;
        }

    }

    public Text getDeathCoords(MinecraftClient client, @NotNull PlayerEntity player) {
        Optional<GlobalPos> deathPosOpt = player.getLastDeathPos();
        if (deathPosOpt.isPresent()) {
            GlobalPos deathPos = deathPosOpt.get();
            if (deathPos.dimension() == player.getWorld().getRegistryKey()) {
                BlockPos deathBlockPos = deathPos.pos();
                return Text.literal(deathBlockPos.getX() + ", " + deathBlockPos.getY()+ ", " + deathBlockPos.getZ());
            }
        }
        return Text.literal("???");
    }
    public Text getCompassCoords(MinecraftClient client, @NotNull PlayerEntity player) {
        return Text.literal((int) Math.floor(player.getX()) + ", " + (int) Math.floor(player.getY()) + ", " + (int) Math.floor(player.getZ()));
    }

    enum Tools {
        COMPASS(stack -> stack.isOf(Items.COMPASS), COMPASS_TEXTURE, CompassHud.INSTANCE::getCompassCoords),
        RECOVERY_COMPASS(stack -> stack.isOf(Items.RECOVERY_COMPASS), RECOVERY_COMPASS_TEXTURE, CompassHud.INSTANCE::getDeathCoords);

        private final Predicate<ItemStack> predicate;
        private final Identifier texture;
        private final ToolText toolText;

        Tools(Predicate<ItemStack> predicate, Identifier texture, ToolText toolText) {
            this.predicate = predicate;
            this.texture = texture;
            this.toolText = toolText;
        }

        @FunctionalInterface
        interface ToolText {
            Text getText(MinecraftClient client, PlayerEntity player);
        }

        public Text getText(MinecraftClient client, PlayerEntity player) {
            return toolText.getText(client, player);
        }
    }
}
