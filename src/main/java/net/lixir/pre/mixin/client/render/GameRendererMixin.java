package net.lixir.pre.mixin.client.render;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.lixir.pre.common.init.PREItems;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

import java.util.function.Predicate;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {


    @Shadow @Final private MinecraftClient client;
    @WrapOperation(
            method = "findCrosshairTarget",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/projectile/ProjectileUtil;raycast(Lnet/minecraft/entity/Entity;Lnet/minecraft/util/math/Vec3d;Lnet/minecraft/util/math/Vec3d;Lnet/minecraft/util/math/Box;Ljava/util/function/Predicate;D)Lnet/minecraft/util/hit/EntityHitResult;"
            )
    )
    private @Nullable EntityHitResult pre$widenRay(
            Entity entity, Vec3d min, Vec3d max, Box box, Predicate<Entity> predicate, double maxDistance, Operation<EntityHitResult> original
    ) {
        PlayerEntity player = (PlayerEntity) entity;
        boolean spear = player.getWeaponStack().isOf(PREItems.ELECTRUM_SPEAR);

        if (!spear) {
            return original.call(entity, min, max, box, predicate, maxDistance);
        }

        Vec3d forward = max.subtract(min).normalize();
        Vec3d up = Vec3d.fromPolar(90, player.getYaw());
        Vec3d right = forward.crossProduct(up).normalize();

        double width = 0.25;
        EntityHitResult closest = null;
        double closestSq = maxDistance;

        for (Vec3d offset : new Vec3d[]{
                Vec3d.ZERO,
                right.multiply(width),
                right.multiply(-width)
        }) {
            EntityHitResult hit = ProjectileUtil.raycast(
                    entity,
                    min.add(offset),
                    max.add(offset),
                    box,
                    predicate,
                    maxDistance
            );

            if (hit != null) {
                double sq = hit.getPos().squaredDistanceTo(min);
                if (sq < closestSq) {
                    closestSq = sq;
                    closest = hit;
                }
            }
        }

        return closest;
    }

}
