package net.lixir.pre.mixin.item;

import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FlintAndSteelItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.dimension.NetherPortal;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(FlintAndSteelItem.class)
public abstract class FlintAndSteelItemMixin {

    @Inject(method = "useOnBlock", at = @At("HEAD"), cancellable = true)
    private static void useOnBlock(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir) {
        PlayerEntity player = context.getPlayer();
        if (player == null) return;

        World world = context.getWorld();
        BlockPos pos = context.getBlockPos();
        BlockPos pos2 = pos.offset(context.getSide());
        Hand hand = context.getHand();
        ItemStack stack = player.getStackInHand(hand);


        RegistryWrapper.Impl<Enchantment> enchantmentRegistry = world.getRegistryManager().getOrThrow(RegistryKeys.ENCHANTMENT);
        RegistryEntry<Enchantment> fireAspectEntry = enchantmentRegistry.getOrThrow(Enchantments.FIRE_ASPECT);
        if (EnchantmentHelper.getLevel(fireAspectEntry, stack) <= 0) return;
        if (!isOverworldOrNether(world)) return;

        Optional<NetherPortal> optionalPortal = NetherPortal.getNewPortal(world, pos2, Direction.Axis.X);
        optionalPortal.ifPresent(portal -> {
            portal.createPortal(world);

            world.playSound(player, pos, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS,
                    1.0F, world.getRandom().nextFloat() * 0.4F + 0.8F);

            if (player instanceof ServerPlayerEntity serverPlayer) {
                Criteria.PLACED_BLOCK.trigger(serverPlayer, pos, stack);
                stack.damage(1, player, LivingEntity.getSlotForHand(hand));
            }

            world.emitGameEvent(player, GameEvent.BLOCK_CHANGE, pos);
            cir.setReturnValue(ActionResult.SUCCESS);
        });
    }

    @Unique
    private static boolean isOverworldOrNether(@NotNull World world) {
        return world.getRegistryKey() == World.OVERWORLD || world.getRegistryKey() == World.NETHER;
    }
}
