package net.lixir.pre.item;

import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;
import org.jetbrains.annotations.NotNull;

public class ItemStackUtil {
    public static ItemStack splitStack(@NotNull DefaultedList<ItemStack> list, int slot, int amount) {
        ItemStack stack = list.get(slot);
        if (stack.isEmpty()) return ItemStack.EMPTY;
        if (stack.getCount() <= amount) {
            list.set(slot, ItemStack.EMPTY);
            return stack;
        } else {
            ItemStack result = stack.split(amount);
            if (stack.getCount() == 0) list.set(slot, ItemStack.EMPTY);
            return result;
        }
    }

    public static @NotNull ItemStack takeStack(@NotNull DefaultedList<ItemStack> list, int slot) {
        ItemStack stack = list.get(slot);
        list.set(slot, ItemStack.EMPTY);
        return stack;
    }

}
