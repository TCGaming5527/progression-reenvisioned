package net.lixir.pre.mixin.accessor;

import net.minecraft.entity.player.ItemCooldownManager;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(ItemCooldownManager.class)
public interface ItemCooldownManagerAccessor {
    @Accessor("tick")
    int getTick();

    @Accessor("entries")
    Map<Identifier, ItemCooldownManager.Entry> getEntries();
}
