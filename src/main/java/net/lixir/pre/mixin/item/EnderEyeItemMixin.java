package net.lixir.pre.mixin.item;


import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.lixir.pre.network.payload.EnderEyeSpeechPayload;
import net.lixir.pre.sound.PRESoundEvents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.EnderEyeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.s2c.play.StopSoundS2CPacket;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.Structure;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EnderEyeItem.class)
public class EnderEyeItemMixin extends Item {
    public EnderEyeItemMixin(Settings settings) {
        super(settings);
    }

    @WrapMethod(method = "getMaxUseTime")
    private int detour$preventOriginalEnderEyeFunction(ItemStack stack, LivingEntity user, Operation<Integer> original) {
        return 72000;
    }

    @WrapOperation(
            method = "use",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/PlayerEntity;setCurrentHand(Lnet/minecraft/util/Hand;)V"
            )
    )
    private void detour$preventOriginalEnderEyeFunction(PlayerEntity instance, Hand hand, Operation<Void> original) {

    }

    @WrapOperation(
            method = "use",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/world/ServerWorld;locateStructure(Lnet/minecraft/registry/tag/TagKey;Lnet/minecraft/util/math/BlockPos;IZ)Lnet/minecraft/util/math/BlockPos;"
            )

    )
    private @Nullable BlockPos detour$preventOriginalEnderEyeFunction(
            ServerWorld instance,
            TagKey<Structure> structureTag,
            BlockPos pos,
            int radius,
            boolean skipReferencedStructures,
            @NotNull Operation<BlockPos> original,
            @Local(argsOnly = true) PlayerEntity entity,
            @Local ItemStack stack,
            @Local(argsOnly = true) Hand hand
            ) {
        BlockPos strongholdPos = original.call(instance, structureTag, pos, radius, skipReferencedStructures);
        if (entity instanceof ServerPlayerEntity serverPlayer) {
            if (serverPlayer.isSneaking() && strongholdPos != null) {
                ServerPlayNetworking.send(serverPlayer, new EnderEyeSpeechPayload(strongholdPos));
                instance.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.ENTITY_ENDER_EYE_LAUNCH, SoundCategory.PLAYERS, 1f, 1f);
                serverPlayer.swingHand(hand, true);
                stack.decrementUnlessCreative(1, serverPlayer);
            } else {
                serverPlayer.setCurrentHand(hand);
                instance.playSound(null, serverPlayer.getX(), serverPlayer.getY(), serverPlayer.getZ(), PRESoundEvents.ITEM_ENDER_EYE_TELEPORT_START, SoundCategory.PLAYERS);
            }
        }
        return null;
    }

    @Override
    public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
        super.usageTick(world, user, stack, remainingUseTicks);
        Random random = world.getRandom();
        Vec3d tpPos = getTpPos(user);

        float width = user.getWidth();
        float height = user.getHeight();
        if (remainingUseTicks % 240 == 0 || remainingUseTicks == getMaxUseTime(stack, user)) {
            world.playSound(null, user.getX(), user.getY(), user.getZ(), PRESoundEvents.ITEM_ENDER_EYE_TELEPORT_AMBIENT, SoundCategory.PLAYERS);
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

    @Unique
    private Vec3d getTpPos(@NotNull LivingEntity user) {
        Vec3d rotVec = user.getRotationVector();
        Vec3d pos = user.getPos().add(0, 1, 0);
        return pos.add(rotVec.multiply(15));
    }

    @Override
    public boolean onStoppedUsing(ItemStack stack, @NotNull World world, LivingEntity user, int remainingUseTicks) {
        Vec3d tpPos = getTpPos(user);
        user.setPosition(tpPos.x, tpPos.y, tpPos.z);
        if (!world.isClient) {
            world.playSound(null, tpPos.getX(), tpPos.getY(), tpPos.getZ(), PRESoundEvents.ITEM_ENDER_EYE_TELEPORT, SoundCategory.PLAYERS);
            if (user instanceof PlayerEntity player) {
                player.getItemCooldownManager().set(stack, 60);
            }
            if (world instanceof ServerWorld serverWorld) {
                StopSoundS2CPacket stopSoundS2CPacket = new StopSoundS2CPacket(PRESoundEvents.ITEM_ENDER_EYE_TELEPORT_AMBIENT.id(), SoundCategory.PLAYERS);

                for (ServerPlayerEntity serverPlayerEntity : serverWorld.getPlayers(serverPlayerEntity -> serverPlayerEntity.distanceTo(user) < 32)) {
                    serverPlayerEntity.networkHandler.sendPacket(stopSoundS2CPacket);
                }
            }
            user.swingHand(user.getActiveHand(), true);
            stack.decrementUnlessCreative(1, user);
        }
        return super.onStoppedUsing(stack, world, user, remainingUseTicks);
    }

}