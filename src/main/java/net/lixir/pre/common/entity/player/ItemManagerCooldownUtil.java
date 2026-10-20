package net.lixir.pre.common.entity.player;

import net.lixir.pre.mixin.accessor.ItemCooldownManagerAccessor;
import net.minecraft.entity.player.ItemCooldownManager;
import net.minecraft.item.Item;

public final class ItemManagerCooldownUtil {
    public static int getRemainingCooldownTicks(ItemCooldownManager cooldownManager, Item item) {

        ItemCooldownManagerAccessor accessor = (ItemCooldownManagerAccessor) cooldownManager;
        ItemCooldownManager.Entry entry = accessor.getEntries().get(cooldownManager.getGroup(item.getDefaultStack()));
        if (entry == null)
            return 0;
        int end = entry.endTick();
        return Math.max(0, (end - accessor.getTick()));
    }
}
