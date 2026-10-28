package net.lixir.pre.common.event;

import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.lixir.pre.common.init.PREAttachmentTypes;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.Map;

public class GiveStoredRelicsEvent implements ServerPlayerEvents.AfterRespawn{

    @Override
    public void afterRespawn(ServerPlayerEntity oldPlayer, ServerPlayerEntity newPlayer, boolean alive) {
        Map<Integer, ItemStack> relics = oldPlayer.getAttachedOrCreate(PREAttachmentTypes.RELICS);

        if (!relics.isEmpty()) {
            PlayerInventory inventory = newPlayer.getInventory();

            for (int slot : relics.keySet()) {
                ItemStack stack = relics.get(slot);

                if (inventory.getStack(slot).isEmpty()) {
                    inventory.setStack(slot, stack);
                } else {
                    inventory.offerOrDrop(stack);
                }
            }

            oldPlayer.removeAttached(PREAttachmentTypes.RELICS);
        }
    }
}
