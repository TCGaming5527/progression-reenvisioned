package net.lixir.pre.mixin.item;

import net.lixir.pre.common.init.PREAttachmentTypes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Rarity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

@Mixin(ServerPlayerEntity.class)
public class ServerPlayerEntityMixin {

    @Inject(method = "onDeath", at = @At("HEAD"))
    private void pre$storeRelics(DamageSource damageSource, CallbackInfo ci) {
        ServerPlayerEntity player = (ServerPlayerEntity) (Object) this;

        List<ItemStack> relics = new ArrayList<>();
        for (ItemStack stack : player.getInventory()) {
            if (stack.getRarity() == Rarity.PROGRESSION_REENVISIONED_RELIC) {
                relics.add(stack.copyAndEmpty());
            }
        }
        player.setAttached(PREAttachmentTypes.RELICS, relics);

    }
}
