package net.lixir.pre.mixin.server.network;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.authlib.GameProfile;
import lombok.val;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.lixir.pre.ProgressionReEnvisioned;
import net.lixir.pre.component.entity.PREEntityComponents;
import net.lixir.pre.item.OldClockItem;
import net.lixir.pre.item.SpearItem;
import net.lixir.pre.network.payload.SetPositionPayload;
import net.lixir.pre.network.payload.SetVelocityPayload;
import net.lixir.pre.util.PositionAngle;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin extends PlayerEntity {
    /*
        Had to do it like this because of the error "Error sending packet clientbound/minecraft:update_attributes"
        when trying to add it to a regular item with attribute modifiers.
     */
    @Unique
    private static final EntityAttributeModifier SPEAR_RANGE_MODIFIER = new EntityAttributeModifier(
            ProgressionReEnvisioned.id("spear_range"), 0.4, EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE
    );
    @Unique
    private final ServerPlayerEntity pre$self = (ServerPlayerEntity) (Object) this;

    public ServerPlayerEntityMixin(World world, BlockPos pos, float yaw, GameProfile gameProfile) {
        super(world, pos, yaw, gameProfile);
    }

    @Shadow
    public abstract ServerWorld getServerWorld();

    @WrapOperation(
            method = "copyFrom",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/network/ServerPlayerEntity;isSpectator()Z"
            )
    )
    private boolean pre$recoveryCompassDeath(ServerPlayerEntity instance, Operation<Boolean> original) {
        return true;
    }

    @Inject(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/network/ServerPlayerEntity;updateCreativeInteractionRangeModifiers()V",
                    shift = At.Shift.AFTER
            )
    )
    private void pre$tick(CallbackInfo ci) {
        updateSpearAttributes();
    }

    @Inject(
            method = "tick",
            at = @At(
                    value = "TAIL"
            )
    )
    private void pre$tick2(CallbackInfo ci) {
        val rewindData = PREEntityComponents.REWIND_DATA.get(pre$self);
        val isRewinding = rewindData.isRewinding();
        val mainhandStack = pre$self.getMainHandStack();
        if (!(mainhandStack.getItem() instanceof OldClockItem) && isRewinding) {
            setNoGravity(false);
            rewindData.setRewinding(false);
        }

        val timeline = rewindData.getTimeline();
        val last = timeline.isEmpty() ? null : timeline.getLast();
        if (!isRewinding && getWorld().getTime() % 20 == 0) {
            val positionAngle = new PositionAngle(getPos(), getYaw(), getPitch());
            if (last == null || !last.arePositionsEqual(positionAngle)) {
                rewindData.addToTimeline(positionAngle);
            }
        } else if (getWorld().getTime() % 10 == 0 && isRewinding) {
            if (last != null) {
                setNoGravity(true);
                val pos = last.position();
                ServerPlayNetworking.send(pre$self, new SetVelocityPayload(Vec3d.ZERO));
                pre$self.fallDistance = 0;
                pre$self.setVelocity(Vec3d.ZERO);
                float yaw = last.yaw();
                float pitch = last.pitch();
                ServerPlayNetworking.send(pre$self, new SetPositionPayload(pos, yaw, pitch));
                pre$self.setPosition(pos);
                pre$self.setYaw(yaw);
                pre$self.setPitch(pitch);
                timeline.remove(last);
                mainhandStack.damage(1, pre$self, EquipmentSlot.MAINHAND);
            } else {
                setNoGravity(false);
                rewindData.setRewinding(false);
            }
        }
    }

    @Unique
    private void updateSpearAttributes() {
        EntityAttributeInstance attributeInstance = this.getAttributeInstance(EntityAttributes.ENTITY_INTERACTION_RANGE);
        if (attributeInstance != null) {
            if (this.getWeaponStack().getItem() instanceof SpearItem) {
                attributeInstance.updateModifier(SPEAR_RANGE_MODIFIER);
            } else {
                attributeInstance.removeModifier(SPEAR_RANGE_MODIFIER);
            }
        }
    }
}
