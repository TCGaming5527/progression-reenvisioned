package net.lixir.pre.component;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.minecraft.component.ComponentsAccess;
import net.minecraft.item.Item;
import net.minecraft.item.tooltip.TooltipAppender;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.function.Consumer;

public record SilkTouchComponent(boolean enabled) implements TooltipAppender {

    public static final Codec<SilkTouchComponent> CODEC =
            Codec.BOOL.xmap(SilkTouchComponent::new, SilkTouchComponent::enabled);

    public static final PacketCodec<ByteBuf, SilkTouchComponent> PACKET_CODEC = new PacketCodec<>() {
        public SilkTouchComponent decode(ByteBuf byteBuf) {
            return new SilkTouchComponent(byteBuf.readBoolean());
        }

        public void encode(ByteBuf byteBuf, SilkTouchComponent silkTouchComponent) {
            byteBuf.writeBoolean(silkTouchComponent.enabled);
        }
    };
    @Override
    public void appendTooltip(
            Item.TooltipContext context,
            Consumer<Text> textConsumer,
            TooltipType type,
            ComponentsAccess components
    ) {
        if (enabled)
            textConsumer.accept(Text.translatable("tooltip.silk_touch").formatted(Formatting.BLUE));
    }
}