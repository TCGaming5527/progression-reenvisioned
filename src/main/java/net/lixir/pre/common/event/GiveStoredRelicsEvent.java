package net.lixir.pre.common.event;

import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.lixir.pre.common.init.PREAttachmentTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.List;

public class GiveStoredRelicsEvent implements ServerPlayerEvents.AfterRespawn{

    @Override
    public void afterRespawn(ServerPlayerEntity oldPlayer, ServerPlayerEntity newPlayer, boolean alive) {
        List<ItemStack> relics = oldPlayer.getAttachedOrCreate(PREAttachmentTypes.RELICS);

        if (!relics.isEmpty()) {
            for (ItemStack stack : relics) {
                newPlayer.getInventory().offerOrDrop(stack);
            }

            oldPlayer.removeAttached(PREAttachmentTypes.RELICS);
        }
    }
}
