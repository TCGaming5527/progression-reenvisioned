package net.lixir.pre.mixin.entity;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.lixir.pre.entity.attribute.PREEntityAttributes;
import net.lixir.pre.registry.tag.PREDamageTypeTags;
import net.lixir.pre.registry.tag.PREEntityTypeTags;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.damage.DamageSource;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @ModifyArg(
            method = "damage",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/LivingEntity;applyDamage(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/entity/damage/DamageSource;F)V"
            ),
            index = 2
    )
    private float pre$amplifyDamage(
            float amount,
            @Local(argsOnly = true) DamageSource source
    ) {
        if (amount <= 0.0F) {
            return amount;
        }

        if (!(source.getAttacker() instanceof LivingEntity attacker)) {
            return amount;
        }

        LivingEntity target = (LivingEntity) (Object) this;
        EntityType<?> targetType = target.getType();

        double multiplier = 1.0;

        if (targetType.isIn(PREEntityTypeTags.SENSITIVE_TO_UNDEAD_DAMAGE)) {
            multiplier *= attacker.getAttributeValue(PREEntityAttributes.UNDEAD_DAMAGE) / 100.0;
        }
        if (targetType.isIn(PREEntityTypeTags.SENSITIVE_TO_NETHER_DAMAGE)) {
            multiplier *= attacker.getAttributeValue(PREEntityAttributes.NETHER_DAMAGE) / 100.0;
        }
        if (targetType.isIn(PREEntityTypeTags.SENSITIVE_TO_ENDER_DAMAGE)) {
            multiplier *= attacker.getAttributeValue(PREEntityAttributes.ENDER_DAMAGE) / 100.0;
        }
        if (targetType.isIn(PREEntityTypeTags.SENSITIVE_TO_WILD_DAMAGE)) {
            multiplier *= attacker.getAttributeValue(PREEntityAttributes.WILD_DAMAGE) / 100.0;
        }

        return (float) (amount * multiplier);
    }



    @ModifyReturnValue(
            method = "createLivingAttributes",
            at = @At(
                    value = "RETURN"
            )
    )
    private static DefaultAttributeContainer.Builder pre$createLivingAttribute(DefaultAttributeContainer.@NotNull Builder original) {
        return original
                .add(PREEntityAttributes.ENDER_DAMAGE, 100f)
                .add(PREEntityAttributes.UNDEAD_DAMAGE, 100f)
                .add(PREEntityAttributes.NETHER_DAMAGE, 100f)
                .add(PREEntityAttributes.WILD_DAMAGE, 100f)
                .add(PREEntityAttributes.EXPLOSION_PROTECTION, 100f)
                .add(PREEntityAttributes.PROJECTILE_PROTECTION, 100f)
                .add(PREEntityAttributes.FIRE_PROTECTION, 100f)
                .add(PREEntityAttributes.MAGIC_PROTECTION, 100f)
                .add(PREEntityAttributes.PHYSICAL_PROTECTION, 100f);
    }

    @ModifyReturnValue(
            method = "modifyAppliedDamage",
            at = @At("RETURN")
    )
    private float pre$applyCustomProtections(float amount, DamageSource source) {
        if (amount <= 0.0F) {
            return amount;
        }

        LivingEntity self = (LivingEntity) (Object) this;
        var damageType = source.getTypeRegistryEntry();

        double protection = 1.0;

        if (damageType.isIn(PREDamageTypeTags.FIRE_PROTECTION_PROTECTS)) {
            protection *= self.getAttributeValue(PREEntityAttributes.FIRE_PROTECTION) / 100.0;
        }
        if (damageType.isIn(PREDamageTypeTags.EXPLOSION_PROTECTION_PROTECTS)) {
            protection *= self.getAttributeValue(PREEntityAttributes.EXPLOSION_PROTECTION) / 100.0;
        }
        if (damageType.isIn(PREDamageTypeTags.MAGIC_PROTECTION_PROTECTS)) {
            protection *= self.getAttributeValue(PREEntityAttributes.MAGIC_PROTECTION) / 100.0;
        }
        if (damageType.isIn(PREDamageTypeTags.PROJECTILE_PROTECTION_PROTECTS)) {
            protection *= self.getAttributeValue(PREEntityAttributes.PROJECTILE_PROTECTION) / 100.0;
        }
        if (damageType.isIn(PREDamageTypeTags.PHYSICAL_PROTECTION_PROTECTS)) {
            protection *= self.getAttributeValue(PREEntityAttributes.PHYSICAL_PROTECTION) / 100.0;
        }

        if (protection <= 0.0) {
            return amount;
        }

        return (float) (amount / protection);
    }
}
