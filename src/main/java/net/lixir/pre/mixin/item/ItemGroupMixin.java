package net.lixir.pre.mixin.item;


import net.lixir.pre.common.init.tag.PREItemTags;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.*;
import java.util.stream.Collectors;

@Mixin(ItemGroup.class)
public abstract class ItemGroupMixin {
    @Shadow
    private Collection<ItemStack> displayStacks;
    @Shadow
    private Set<ItemStack> searchTabStacks;

    @Inject(method = "updateEntries", at = @At("TAIL"))
    private void removeBannedEntries(CallbackInfo ci) {
        this.displayStacks = new ArrayList<>(displayStacks.stream()
                .filter(stack -> !stack.isIn(PREItemTags.BANNED))
                .toList());

        this.searchTabStacks = searchTabStacks.stream()
                .filter(stack -> !stack.isIn(PREItemTags.BANNED))
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }
}