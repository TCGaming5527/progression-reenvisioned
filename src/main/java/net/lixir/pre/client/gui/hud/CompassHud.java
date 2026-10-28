package net.lixir.pre.client.gui.hud;

import net.lixir.pre.common.ProgressionReEnvisioned;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.BundleContentsComponent;
import net.minecraft.component.type.LodestoneTrackerComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.util.math.Position;
import net.minecraft.util.math.Vec3d;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Predicate;

public class CompassHud {
    protected CompassHud() {

    }
    private static final Identifier RECOVERY_COMPASS_TEXTURE = ProgressionReEnvisioned.id("hud/recovery_compass");
    private static final Identifier COMPASS_TEXTURE = ProgressionReEnvisioned.id("hud/compass");

    private static final int TEXTURE_WIDTH = 101;
    private static final int TEXTURE_HEIGHT = 22;

    public static final Identifier LAYER = ProgressionReEnvisioned.id("compass");
    public static final CompassHud INSTANCE = new CompassHud();

    public void onHudRender(DrawContext context, RenderTickCounter tickCounter) {
        MinecraftClient client = MinecraftClient.getInstance();
        TextRenderer textRenderer = client.textRenderer;

        ClientPlayerEntity player = client.player;
        if (player == null) return;

        Map<Tools, ItemStack> activeTools = new HashMap<>();
        ItemStack mainHandStack = player.getMainHandStack();

        for (Tools tool : Tools.values()) {
            if (tool.predicate.test(mainHandStack)) {
                activeTools.put(tool, mainHandStack);
                continue;
            }
            player.getInventory().contains(stack -> {
                if (tool.predicate.test(stack)) {
                    activeTools.put(tool, stack);
                    return true;
                } else if (stack.contains(DataComponentTypes.BUNDLE_CONTENTS)) {
                    BundleContentsComponent bundleContents = stack.getOrDefault(DataComponentTypes.BUNDLE_CONTENTS, BundleContentsComponent.DEFAULT);
                    for (ItemStack s : bundleContents.iterate()) {
                        if (tool.predicate.test(s)) {
                            activeTools.put(tool, s);
                            break;
                        }
                    }
                }
                return false;
            });
        }

        final int x = 8;
        final int y = 8;

        int cursorX = x;
        MatrixStack matrices = context.getMatrices();

        for (Tools tool : activeTools.keySet()) {
            ItemStack toolStack = activeTools.get(tool);
            Text text = tool.getText(client, player, toolStack);

            float textWidth = (float) textRenderer.getWidth(text);
            float scaling = Math.min(1F, 74F / textWidth);

            matrices.push();
            matrices.translate(cursorX, y, 0);

            context.drawItem(toolStack, 3, 3);
            context.drawGuiTexture(RenderLayer::getGuiTextured, tool.texture, 0, 0, TEXTURE_WIDTH, TEXTURE_HEIGHT);

            matrices.translate(23 + (scaling < 1 ? 0 : 37F - textWidth / 2), TEXTURE_HEIGHT / 2F - 8 * scaling + 4, 0F);
            if (scaling < 1) {
                matrices.scale(scaling, scaling, 1F);
            }


            context.drawText(textRenderer, text, 0, 0, 0xFFFFFFFF, true);
            matrices.pop();

            cursorX += TEXTURE_WIDTH + 10;
        }

    }

    public static Text getDeathCoords(MinecraftClient client, PlayerEntity player, ItemStack stack) {
        Optional<GlobalPos> deathPosOpt = player.getLastDeathPos();
        if (deathPosOpt.isPresent()) {
            GlobalPos deathPos = deathPosOpt.get();
            if (deathPos.dimension() == player.getWorld().getRegistryKey()) {
                BlockPos deathBlockPos = deathPos.pos();
                return Text.literal(deathBlockPos.getX() + " " + deathBlockPos.getY()+ " " + deathBlockPos.getZ());
            }
        }
        return Text.literal("???");
    }
    public static Text getCompassCoords(MinecraftClient client, PlayerEntity player, ItemStack stack) {
        Position coords = player.getPos();
        LodestoneTrackerComponent lodestone = stack.get(DataComponentTypes.LODESTONE_TRACKER);

        if (lodestone != null) {
            GlobalPos lodestonePos = lodestone.target().orElse(null);
            if (lodestonePos != null && player.getWorld().getRegistryKey() == lodestonePos.dimension()) {
                coords = new Vec3d(lodestonePos.pos().getX(), lodestonePos.pos().getY(), lodestonePos.pos().getZ());
            } else {
                return Text.literal("???");
            }
        }
        return Text.literal((int) coords.getX() + " " + (int) coords.getY() + " " + (int) coords.getZ());
    }

    enum Tools {
        COMPASS(stack -> stack.isOf(Items.COMPASS), COMPASS_TEXTURE, CompassHud::getCompassCoords),
        RECOVERY_COMPASS(stack -> stack.isOf(Items.RECOVERY_COMPASS), RECOVERY_COMPASS_TEXTURE, CompassHud::getDeathCoords);

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
            Text getText(MinecraftClient client, PlayerEntity player, ItemStack stack);
        }

        public Text getText(MinecraftClient client, PlayerEntity player, ItemStack stack) {
            return toolText.getText(client, player, stack);
        }
    }
}
