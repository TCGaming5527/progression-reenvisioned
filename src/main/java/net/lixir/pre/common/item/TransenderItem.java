package net.lixir.pre.common.item;

import net.lixir.pre.common.entity.player.ItemManagerCooldownUtil;
import net.lixir.pre.common.init.PRESoundEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.s2c.play.StopSoundS2CPacket;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Unique;

import java.util.List;

public class TransenderItem extends Item {
    public TransenderItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        if (user.isSneaking()) {
            return ActionResult.PASS;
        } else {
            user.setCurrentHand(hand);
            world.playSound(null, user.getX(), user.getY(), user.getZ(), PRESoundEvents.ITEM_TRANSENDER_TELEPORT_START, SoundCategory.PLAYERS);
            return ActionResult.CONSUME;
        }
    }

    @Override
    public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
        super.usageTick(world, user, stack, remainingUseTicks);
        Random random = world.getRandom();
        Vec3d tpPos = getTpPos(user);

        float width = user.getWidth();
        float height = user.getHeight();
        if (remainingUseTicks % 240 == 0 || remainingUseTicks == getMaxUseTime(stack, user)) {
            world.playSound(null, user.getX(), user.getY(), user.getZ(), PRESoundEvents.ITEM_TRANSENDER_AMBIENT, SoundCategory.PLAYERS);
        }
        for (int i = 0; i < random.nextBetween(7, 9); i++) {
            double x = (tpPos.x + ((random.nextFloat() - 0.5f) * width));
            double y = (tpPos.y + (random.nextFloat() * height));
            double z = (tpPos.z + ((random.nextFloat() - 0.5f) * width));

            double vx = (random.nextFloat() - 0.5f) * 0.1;
            double vy = (random.nextFloat() - 0.5f) * 0.1;
            double vz = (random.nextFloat() - 0.5f) * 0.1;

            world.addParticleClient(ParticleTypes.REVERSE_PORTAL, x, y, z, vx, vy, vz);
        }
    }

    @Override
    public boolean onStoppedUsing(ItemStack stack, @NotNull World world, LivingEntity user, int remainingUseTicks) {
        Vec3d tpPos = getTpPos(user);

        user.setPosition(tpPos.x, tpPos.y, tpPos.z);
        if (world instanceof ServerWorld serverWorld) {
            if (user instanceof PlayerEntity player) {
                List<Entity> entities = world.getOtherEntities(user, user.getBoundingBox(), entity -> entity instanceof LivingEntity living && living.isAlive());
                for (Entity entity : entities) {
                    entity.damage(serverWorld, world.getDamageSources().playerAttack(player), 15.0f);
                }
            }

            StopSoundS2CPacket stopSoundS2CPacket = new StopSoundS2CPacket(PRESoundEvents.ITEM_ENDER_EYE_TELEPORT_AMBIENT.id(), SoundCategory.PLAYERS);
            for (ServerPlayerEntity serverPlayerEntity : serverWorld.getPlayers(serverPlayerEntity -> serverPlayerEntity.distanceTo(user) < 32)) {
                serverPlayerEntity.networkHandler.sendPacket(stopSoundS2CPacket);
            }

            world.playSound(null, tpPos.getX(), tpPos.getY(), tpPos.getZ(), PRESoundEvents.ITEM_TRANSENDER_TELEPORT, SoundCategory.PLAYERS);
            if (user instanceof PlayerEntity player)
                player.getItemCooldownManager().set(stack, 400);

            user.swingHand(user.getActiveHand(), true);
        }

        return super.onStoppedUsing(stack, world, user, remainingUseTicks);
    }


    @Unique
    public Vec3d getTpPos(@NotNull LivingEntity user) {
        World world = user.getWorld();

        Vec3d start = user.getCameraPosVec(1.0F);
        Vec3d look = user.getRotationVector();
        Vec3d end = start.add(look.multiply(20.0));

        Box searchBox = user.getBoundingBox()
                .stretch(look.multiply(20.0))
                .expand(1.0);

        EntityHitResult entityHit = ProjectileUtil.getEntityCollision(
                world,
                user,
                start,
                end,
                searchBox,
                entity -> entity instanceof LivingEntity living
                        && living.isAlive()
                        && living != user
        );

        if (entityHit != null) {
            Entity target = entityHit.getEntity();
            return target.getPos();
        }

        return end;
    }


    @Override
    public void inventoryTick(ItemStack stack, ServerWorld world, Entity entity, @Nullable EquipmentSlot slot) {
        super.inventoryTick(stack, world, entity, slot);
        if (entity instanceof PlayerEntity player) {
            if (ItemManagerCooldownUtil.getRemainingCooldownTicks(player.getItemCooldownManager(), stack.getItem()) == 1) {
                world.playSound(null, entity.getX(), entity.getY(), entity.getZ(), PRESoundEvents.ITEM_TRANSENDER_CHARGED, SoundCategory.PLAYERS);
            }
        }
    }
}
