package net.lixir.pre.mixin.entity;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.lixir.pre.common.component.entity.RewindComponent;
import net.lixir.pre.common.init.PREEntityAttributes;
import net.lixir.pre.common.init.PREStatusEffects;
import net.lixir.pre.common.init.tag.PREDamageTypeTags;
import net.lixir.pre.common.init.tag.PREEntityTypeTags;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    @Shadow
    public float forwardSpeed;

    @Shadow
    public abstract float getYaw(float tickProgress);

    @Shadow
    public float sidewaysSpeed;

    @Shadow
    public float upwardSpeed;

    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

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

    @ModifyReturnValue(method = "getEffectiveGravity", at = @At("RETURN"))
    private double pre$rewindGravity(double original) {
        LivingEntity entity = (LivingEntity) (Object) this;
        if (entity instanceof PlayerEntity player && RewindComponent.get(player).isRewinding()
                || entity.hasStatusEffect(PREStatusEffects.GODS_GRACE)) {
            return 0;
        }
        return original;
    }

    @Definition(id = "g", local = @Local(type = float.class, name = "g"))
    @Expression("g = @(?)")
    @ModifyExpressionValue(method = "travelMidAir", at = @At("MIXINEXTRAS:EXPRESSION"))
    private float pre$godsGraceMovement(float original) {
        LivingEntity entity = (LivingEntity) (Object) this;
        if (entity.hasStatusEffect(PREStatusEffects.GODS_GRACE)) {
            return 1F;
        }
        return original;
    }

    @Definition(id = "vec3d", local = @Local(type = Vec3d.class, name = "vec3d"))
    @Expression("vec3d = @(?)")
    @ModifyExpressionValue(method = "travelMidAir", at = @At("MIXINEXTRAS:EXPRESSION"))
    private Vec3d pre$godsGraceMovement(Vec3d original, Vec3d movementInput) {
        LivingEntity entity = (LivingEntity) (Object) this;
        if (entity.hasStatusEffect(PREStatusEffects.GODS_GRACE)) {
            Vec3d vel = this.getRotationVector().multiply(this.forwardSpeed)
                    .add(this.getRotationVector(0, this.getYaw() - 90).multiply(this.sidewaysSpeed))
                    .multiply(0.03);

            return this.getVelocity().multiply(0.87).add(vel);
        }
        return original;
    }
}
