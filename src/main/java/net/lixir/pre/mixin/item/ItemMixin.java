package net.lixir.pre.mixin.item;


import net.lixir.pre.common.duck.item.ItemDuck;
import net.lixir.pre.common.init.tag.PREBlockTags;
import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.ItemTags;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public abstract class ItemMixin implements ItemDuck {

    @Inject(
            method = "isCorrectForDrops",
            at = @At("HEAD"),
            cancellable = true
    )
    private void allowAnyPickaxe(ItemStack stack, @NotNull BlockState state, CallbackInfoReturnable<Boolean> cir) {
        if (state.isIn(PREBlockTags.MINEABLE_ANY_PICKAXE) && stack.isIn(ItemTags.PICKAXES)) {
            cir.setReturnValue(true);
        }
    }
}