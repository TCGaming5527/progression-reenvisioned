package net.lixir.pre.client.render.item;

import com.mojang.serialization.MapCodec;
import lombok.val;
import net.lixir.pre.common.init.PREEntityComponents;
import net.minecraft.client.render.item.property.bool.BooleanProperty;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemDisplayContext;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public record RewindingProperty() implements BooleanProperty {
	public static final MapCodec<RewindingProperty> CODEC = MapCodec.unit(new RewindingProperty());

	@Override
	public boolean test(ItemStack stack, @Nullable ClientWorld world, @Nullable LivingEntity entity, int seed, ItemDisplayContext displayContext) {
		if (entity instanceof PlayerEntity player) {
			val rewindData = PREEntityComponents.REWIND.get(player);
			return rewindData.isRewinding();
		}
		return false;
	}

	@Override
	public MapCodec<RewindingProperty> getCodec() {
		return CODEC;
	}
}
