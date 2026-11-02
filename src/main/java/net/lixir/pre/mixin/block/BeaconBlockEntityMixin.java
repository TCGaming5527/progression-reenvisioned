package net.lixir.pre.mixin.block;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.sugar.Local;
import net.lixir.pre.common.init.PREStatusEffects;
import net.minecraft.block.entity.BeaconBlockEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(BeaconBlockEntity.class)
public class BeaconBlockEntityMixin {

    @Definition(id = "primaryEffect", local = @Local(name = "primaryEffect", type = RegistryEntry.class, argsOnly = true))
    @Expression("primaryEffect != null")
    @ModifyExpressionValue(method = "applyPlayerEffects", at = @At("MIXINEXTRAS:EXPRESSION"))
    private static boolean pre$overrideCondition(boolean original, World world, BlockPos pos) {
        return true;
    }

    @WrapWithCondition(method = "applyPlayerEffects", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;addStatusEffect(Lnet/minecraft/entity/effect/StatusEffectInstance;)Z", ordinal = 0))
    private static boolean pre$addBeaconEffect(PlayerEntity player, StatusEffectInstance effect, World world, BlockPos pos, int beaconLevel, @Nullable RegistryEntry<StatusEffect> primaryEffect, @Nullable RegistryEntry<StatusEffect> secondaryEffect) {
        player.addStatusEffect(new StatusEffectInstance(PREStatusEffects.GODS_GRACE, effect.getDuration(), 0, true, true));
        return primaryEffect != null;
    }
}
