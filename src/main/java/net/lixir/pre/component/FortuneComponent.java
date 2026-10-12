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

public record FortuneComponent(int value) implements TooltipAppender {

    public static final Codec<FortuneComponent> CODEC =
            Codec.INT.xmap(FortuneComponent::new, FortuneComponent::value);

    public static final PacketCodec<ByteBuf, FortuneComponent> PACKET_CODEC = new PacketCodec<>() {
        public FortuneComponent decode(ByteBuf byteBuf) {
            return new FortuneComponent(byteBuf.readInt());
        }

        public void encode(ByteBuf byteBuf, FortuneComponent fortuneComponent) {
            byteBuf.writeInt(fortuneComponent.value);
        }
    };
    @Override
    public void appendTooltip(
            Item.TooltipContext context,
            Consumer<Text> textConsumer,
            TooltipType type,
            ComponentsAccess components
    ) {
        textConsumer.accept(Text.literal("+" + value + " ").append(Text.translatable("tooltip.fortune")).formatted(Formatting.BLUE));
    }
}