package net.lixir.pre.common.item;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.lixir.pre.common.init.PREEnchantments;
import net.lixir.pre.common.payload.AddVelocityPayload;
import net.lixir.pre.common.init.PRESoundEvents;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SpearItem extends Item {
    public SpearItem(Settings settings) {
        super(settings);
    }

    public boolean canAttack(PlayerEntity player, ItemStack stack, float partialTick) {
        return player.getAttackCooldownProgress(partialTick) >= 0.9;
    }

    @Override
    public HandTransformationType getHandTransformationType() {
        return HandTransformationTypes.SPEAR;
    }

    @Override
    public int getMaxUseTime(ItemStack stack, LivingEntity user) {
        return 72000;
    }

    @Override
    public ActionResult use(World world, @NotNull PlayerEntity user, Hand hand) {
        if (user.isUsingItem() || !canAttack(user, user.getWeaponStack(), 0.0f) || hand == Hand.OFF_HAND)
            return ActionResult.PASS;
        if (user instanceof ServerPlayerEntity serverPlayer && canLunge(serverPlayer)) {
            lunge(serverPlayer, getLungeLevel(user.getWeaponStack(), serverPlayer.getServerWorld()));
        }
        user.setCurrentHand(hand);
        world.playSound(user, user.getX(), user.getY(), user.getZ(), PRESoundEvents.ITEM_SPEAR_USE, SoundCategory.PLAYERS);
        return ActionResult.CONSUME;
    }

    public int getLungeLevel(ItemStack stack, ServerWorld serverWorld) {
        RegistryWrapper.WrapperLookup lookup = serverWorld.getRegistryManager();
        RegistryEntry<Enchantment> entry = lookup
                .getOrThrow(RegistryKeys.ENCHANTMENT)
                .getOrThrow(PREEnchantments.LUNGE);
        return EnchantmentHelper.getLevel(entry, stack);
    }

    public float getExhaustionPerLevel(int level) {
        return level * 4;
    }

    public boolean canLunge(ServerPlayerEntity user) {
        ItemStack stack = user.getWeaponStack();
        if (user.getLungeCooldown() > 0)
            return false;
        int level = getLungeLevel(stack, user.getServerWorld());
        if (level <= 0)
            return false;
        int foodLevel = user.getHungerManager().getFoodLevel();
        return foodLevel >= 7 || user.isInCreativeMode();
    }

    public void lunge(@NotNull ServerPlayerEntity user, int level) {
        double baseSpeed = 0.75 + (level * 0.5);
        double maxHorizontalSpeed = 1.5;
        double maxVerticalSpeed = 1.0;

        Vec3d currentVel = user.getVelocity();
        Vec3d addVel = user.getRotationVector().multiply(baseSpeed);

        double newVelX = currentVel.x + addVel.x;
        double newVelY = currentVel.y + addVel.y;
        double newVelZ = currentVel.z + addVel.z;

        double horizontalSpeed = Math.sqrt(newVelX * newVelX + newVelZ * newVelZ);
        if (horizontalSpeed > maxHorizontalSpeed) {
            double scale = maxHorizontalSpeed / horizontalSpeed;
            newVelX *= scale;
            newVelZ *= scale;
        }

        if (Math.abs(newVelY) > maxVerticalSpeed) {
            newVelY = Math.signum(newVelY) * maxVerticalSpeed;
        }

        Vec3d finalVel = new Vec3d(newVelX, newVelY, newVelZ);

        ServerPlayNetworking.send(user, new AddVelocityPayload(finalVel));
        user.addVelocity(finalVel);
        user.setLungeCooldown(10);
        user.getWeaponStack().damage(1, user);
        user.getHungerManager().addExhaustion(getExhaustionPerLevel(level));

        user.getWorld().playSound(null, user.getX(), user.getY(), user.getZ(), PRESoundEvents.ITEM_SPEAR_LUNGE, SoundCategory.PLAYERS);
    }

    @Override
    public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
        if (world.isClient || !(user instanceof ServerPlayerEntity player))
            return;

        Vec3d velocity = player.getVelocity();
        double horizontalSpeed = Math.sqrt(velocity.x * velocity.x + velocity.z * velocity.z);
        if (horizontalSpeed < 0.13)
            return;

        double range = getInteractionRange(player);

        Vec3d look = player.getRotationVector().normalize();
        Vec3d up = new Vec3d(0, 1, 0);
        Vec3d right = look.crossProduct(up).normalize();

        Vec3d prevPos = player.getPos().subtract(player.getVelocity());
        Vec3d currPos = player.getPos();

        Vec3d prevCenter = prevPos.add(look.multiply(1.2));
        Vec3d currCenter = currPos.add(look.multiply(1.2));

        Box forwardBox = new Box(prevCenter, currCenter)
                .expand(0.9, 0.7, 0.9);


        List<LivingEntity> candidates = world.getEntitiesByClass(
                LivingEntity.class,
                forwardBox,
                e -> e != player && e.canTakeDamage() && e.hurtTime == 0
        );

        if (candidates.isEmpty())
            return;

        Vec3d rayStart = player.getEyePos().subtract(velocity);
        Vec3d rayEnd = rayStart.add(look.multiply(range));

        Vec3d[] offsets = {
                Vec3d.ZERO,
                right.multiply(0.9),
                right.multiply(-0.9),
                up.multiply(0.4),
                up.multiply(-0.25)

        };

        for (LivingEntity target : candidates) {
            Box targetBox = target.getBoundingBox().expand(0.2);

            for (Vec3d offset : offsets) {
                EntityHitResult hit = ProjectileUtil.raycast(
                        player,
                        rayStart.add(offset),
                        rayEnd.add(offset),
                        targetBox,
                        e -> e == target,
                        range
                );

                if (hit != null) {
                    dealChargeDamage(player, target, horizontalSpeed, stack);
                    return;
                }
            }
        }
    }


    private double getInteractionRange(@NotNull PlayerEntity player) {
        return player.getAttributeValue(EntityAttributes.ENTITY_INTERACTION_RANGE);
    }

    private float getBaseAttackDamage(@NotNull PlayerEntity player) {
        return (float) player.getAttributeValue(EntityAttributes.ATTACK_DAMAGE);
    }

    private float computeChargeDamage(PlayerEntity player, double speed) {
        float base = getBaseAttackDamage(player) * 0.9f;

        float speedScale = 3.25f;
        float maxBonus = base * 3f;

        float bonus = Math.min((float) speed * speedScale, maxBonus);

        return base + bonus;
    }

    private void dealChargeDamage(
            ServerPlayerEntity player,
            @NotNull LivingEntity target,
            double speed,
            @NotNull ItemStack stack
    ) {
        float damage = computeChargeDamage(player, speed);
        if (damage <= 0)
            return;
        DamageSource source = player.getDamageSources().playerAttack(player);
        target.damage(
                player.getServerWorld(),
                source,
                damage
        );
        EnchantmentHelper.onTargetDamaged(player.getServerWorld(), target, source);
        target.takeKnockback(
                0.6,
                player.getX() - target.getX(),
                player.getZ() - target.getZ()
        );
        player.getWorld().playSound(null, target.getX(), target.getY(), target.getZ(), PRESoundEvents.ITEM_SPEAR_JAB, SoundCategory.PLAYERS);
        stack.damage(1, player, EquipmentSlot.MAINHAND);
    }

    @Override
    public void postHit(@NotNull ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.damage(1, attacker, EquipmentSlot.MAINHAND);
    }
}
