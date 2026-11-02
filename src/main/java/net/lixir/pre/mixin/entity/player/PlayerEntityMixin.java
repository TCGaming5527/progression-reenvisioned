package net.lixir.pre.mixin.entity.player;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.lixir.pre.common.duck.entity.player.PlayerEntityDuck;
import net.lixir.pre.common.init.PREStatusEffects;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity implements PlayerEntityDuck {
    @Unique
    private PlayerEntity pre$player = (PlayerEntity) (Object) this;

    @Unique
    private int lungeCooldown = 0;

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public void setLungeCooldown(int lungeCooldown) {
        this.lungeCooldown = lungeCooldown;
    }

    @Override
    public int getLungeCooldown() {
        return lungeCooldown;
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void pre$tick(CallbackInfo ci) {
        if (lungeCooldown > 0)
            lungeCooldown--;
    }

    @ModifyExpressionValue(method = "getBlockBreakingSpeed", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;isOnGround()Z"))
    private boolean pre$godsGraceBuff(boolean original) {
        PlayerEntity player = (PlayerEntity) (Object) this;
        return original || player.hasStatusEffect(PREStatusEffects.GODS_GRACE);
    }

}
