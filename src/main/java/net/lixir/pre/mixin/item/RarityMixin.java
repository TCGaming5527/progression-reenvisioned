package net.lixir.pre.mixin.item;

import net.lixir.pre.common.ProgressionReEnvisioned;
import net.minecraft.util.Formatting;
import net.minecraft.util.Rarity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.MixinIntrinsics;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Rarity.class)
public enum RarityMixin {
    PROGRESSION_REENVISIONED_RELIC(MixinIntrinsics.currentEnumOrdinal(), ProgressionReEnvisioned.ID + ":relic", Formatting.RED);

    @Shadow
    RarityMixin(int index, String name, Formatting formatting) {}
}
