package net.lixir.pre.client.gui.screen.ingame;

import net.lixir.pre.common.ProgressionReEnvisioned;
import net.lixir.pre.common.block.entity.InductionFurnaceBlockEntity;
import net.lixir.pre.common.screen.InductionFurnaceScreenHandler;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.CyclingSlotIcon;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SmithingTemplateItem;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerListener;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

public class InductionFurnaceScreen extends HandledScreen<InductionFurnaceScreenHandler> implements ScreenHandlerListener {
    private static final Identifier TEXTURE = ProgressionReEnvisioned.id("textures/gui/container/induction_furnace.png");
    private static final Identifier BURN_PROGRESS_TEXTURE = Identifier.ofVanilla("container/furnace/burn_progress");
    private static final Text MISSING_TEMPLATE_TOOLTIP = Text.translatable("Add Upgrade Template");
    private final CyclingSlotIcon baseSlotIcon = new CyclingSlotIcon(InductionFurnaceBlockEntity.BASE_SLOT_INDEX);

    public InductionFurnaceScreen(InductionFurnaceScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void drawBackground(@NotNull DrawContext context, float deltaTicks, int mouseX, int mouseY) {
        context.drawTexture(RenderLayer::getGuiTextured, TEXTURE, this.x, this.y, 0.0F, 0.0F, this.backgroundWidth, this.backgroundHeight, 256, 256);
        int l = MathHelper.ceil(this.handler.getSmeltProgress() * 24.0F);
        context.drawGuiTexture(RenderLayer::getGuiTextured, BURN_PROGRESS_TEXTURE, 24, 16, 0, 0, x + 107, y + 34, l, 16);
        this.baseSlotIcon.render(this.handler, context, deltaTicks, this.x, this.y);
    }

    @Override
    public void onSlotUpdate(ScreenHandler handler, int slotId, ItemStack stack) {

    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
        super.render(context, mouseX, mouseY, deltaTicks);
        this.renderSlotTooltip(context, mouseX, mouseY);
    }

    private Optional<SmithingTemplateItem> getSmithingTemplate() {
        ItemStack itemStack = this.handler.getSlot(InductionFurnaceBlockEntity.TEMPLATE_SLOT_INDEX).getStack();
        return !itemStack.isEmpty() && itemStack.getItem() instanceof SmithingTemplateItem smithingTemplateItem
                ? Optional.of(smithingTemplateItem)
                : Optional.empty();
    }

    @Override
    protected void handledScreenTick() {
        super.handledScreenTick();
        Optional<SmithingTemplateItem> optional = this.getSmithingTemplate();
        this.baseSlotIcon.updateTexture(optional.map(SmithingTemplateItem::getEmptyBaseSlotTextures).orElse(List.of()));
    }

    @Override
    public void onPropertyUpdate(ScreenHandler handler, int property, int value) {

    }

    private void renderSlotTooltip(DrawContext context, int mouseX, int mouseY) {
        Optional<Text> optional = Optional.empty();

        if (this.focusedSlot != null) {
            ItemStack templateStack = this.handler
                    .getSlot(InductionFurnaceBlockEntity.TEMPLATE_SLOT_INDEX)
                    .getStack();
            ItemStack focusedStack = this.focusedSlot.getStack();

            if (templateStack.isEmpty()) {
                if (this.focusedSlot.id == InductionFurnaceBlockEntity.TEMPLATE_SLOT_INDEX) {
                    optional = Optional.of(MISSING_TEMPLATE_TOOLTIP);
                }
            } else if (templateStack.getItem() instanceof SmithingTemplateItem smithingTemplateItem
                    && focusedStack.isEmpty()) {

                if (this.focusedSlot.id == InductionFurnaceBlockEntity.BASE_SLOT_INDEX) {
                    optional = Optional.of(smithingTemplateItem.getBaseSlotDescription());
                } else if (this.focusedSlot.id == InductionFurnaceBlockEntity.ADDITION_SLOT_INDEX) {
                    optional = Optional.of(smithingTemplateItem.getAdditionsSlotDescription());
                }
            }
        }

        if (optional.isPresent()) {
            context.drawOrderedTooltip(
                    this.textRenderer,
                    this.textRenderer.wrapLines(optional.get(), 115),
                    mouseX,
                    mouseY
            );
            return;
        }

        if (this.focusedSlot != null && this.focusedSlot.hasStack()) {
            context.drawItemTooltip(
                    this.textRenderer,
                    this.focusedSlot.getStack(),
                    mouseX,
                    mouseY
            );
        }
    }
}
