package net.lixir.pre.common.item;

import lombok.val;
import net.lixir.pre.common.init.PREBlocks;
import net.lixir.pre.common.init.PREDataComponentTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class AlumnabrassBrushItem extends Item {

    public AlumnabrassBrushItem(Settings settings) {
        super(settings);
    }

    @Override
    public boolean allowComponentsUpdateAnimation(
            PlayerEntity player,
            Hand hand,
            ItemStack oldStack,
            ItemStack newStack
    ) {
        return Objects.equals(
                oldStack.getOrDefault(PREDataComponentTypes.CHARGING_TICKS, 0),
                oldStack.getOrDefault(PREDataComponentTypes.MAX_CHARGING_TICKS, 0)
        );
    }

    @Override
    public void inventoryTick(ItemStack stack, ServerWorld world, Entity entity, @Nullable EquipmentSlot slot) {
        if (this.getCharge(stack) < this.getMaxCharge(stack)) {
            if (stack.getOrDefault(PREDataComponentTypes.CHARGING_TICKS, 0) < stack.getOrDefault(PREDataComponentTypes.MAX_CHARGING_TICKS, 0)) {
                stack.set(
                        PREDataComponentTypes.CHARGING_TICKS,
                        stack.getOrDefault(PREDataComponentTypes.CHARGING_TICKS, 0) + 1
                );
            } else {
                this.setCharge(stack, this.getCharge(stack) + 1);
                stack.set(PREDataComponentTypes.CHARGING_TICKS, 0);
            }
        }
        super.inventoryTick(stack, world, entity, slot);
    }

    @Override
    public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
        if (world.isClient) {
            val targetPos = getTargetPos(user, world);
            preview(user, targetPos);
        }
        super.usageTick(world, user, stack, remainingUseTicks);
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        val stack = user.getStackInHand(hand);
        if (this.getCharge(stack) > 0 || user.isCreative()) {
            user.setCurrentHand(hand);
            return ActionResult.CONSUME;
        }
        return ActionResult.PASS;
    }

    @Override
    public boolean onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        val targetPos = getTargetPos(user, world);

        if (!world.isClient) {
            createPlatform(world, targetPos);

            this.setCharge(stack, this.getCharge(stack) - 1);
            stack.damage(1, user, user.getActiveHand() == Hand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND);
        }

        return super.onStoppedUsing(stack, world, user, remainingUseTicks);
    }

    public void setCharge(ItemStack stack, int charge) {
        stack.set(PREDataComponentTypes.CHARGE, Math.clamp(charge, 0, this.getMaxCharge(stack)));
    }

    public int getCharge(ItemStack stack) {
        return stack.getOrDefault(PREDataComponentTypes.CHARGE, 0);
    }

    public int getMaxCharge(ItemStack stack) {
        return stack.getOrDefault(PREDataComponentTypes.MAX_CHARGE, 4);
    }

    @Override
    public int getItemBarStep(ItemStack stack) {
        return MathHelper.clamp(Math.round(13.0F - (this.getMaxCharge(stack) - this.getCharge(stack)) * 13.0F / this.getMaxCharge(stack)), 0, 13);
    }

    @Override
    public int getItemBarColor(ItemStack stack) {
        return -1;
    }

    @Override
    public boolean isItemBarVisible(ItemStack stack) {
        return true;
    }

    private BlockPos getTargetPos(LivingEntity user, World world) {
        val start = user.getEyePos();
        val end = start.add(user.getRotationVector().multiply(3.0));
        val context = new RaycastContext(start, end,
                RaycastContext.ShapeType.COLLIDER,
                RaycastContext.FluidHandling.ANY,
                user);
        val hitResult = world.raycast(context);
        return hitResult != null ? hitResult.getBlockPos() : BlockPos.ofFloored(start.add(end));
    }


    private void preview(LivingEntity user, BlockPos pos) {
        val random = user.getRandom();
        val center = pos.toCenterPos();
        val world = user.getWorld();
        for (int i = 0; i < random.nextBetween(7, 9); i++) {
            double x = (center.x + ((random.nextFloat() - 0.5f) * 3));
            double y = (center.y + random.nextFloat() - 0.5f);
            double z = (center.z + ((random.nextFloat() - 0.5f) * 3));

            double vx = (random.nextFloat() - 0.5f) * 0.05;
            double vy = (random.nextFloat() - 0.5f) * 0.05;
            double vz = (random.nextFloat() - 0.5f) * 0.05;

            world.addParticleClient(new DustParticleEffect(0x8e3036, 1.0f), x, y, z, vx, vy, vz);
        }

    }

    private void createPlatform(World world, BlockPos pos) {
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                val platPos = pos.add(i, 0, j);
                val state = world.getBlockState(platPos);
                if (state.isAir() || state.isReplaceable()) {
                    world.setBlockState(platPos, PREBlocks.PLATFORM.getDefaultState());
                }
            }
        }
    }
}
