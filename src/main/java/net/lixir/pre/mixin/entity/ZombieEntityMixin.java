package net.lixir.pre.mixin.entity;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import net.minecraft.entity.mob.ZombieEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ZombieEntity.class)
public class ZombieEntityMixin {

    @WrapWithCondition(method = "initialize", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/mob/ZombieEntity;setBaby(Z)V"))
    private boolean pre$noBabies(ZombieEntity instance, boolean baby) {
        return false;
    }
}
