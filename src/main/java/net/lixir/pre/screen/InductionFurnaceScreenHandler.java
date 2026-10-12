package net.lixir.pre.screen;

import net.lixir.pre.block.entity.InductionFurnaceBlockEntity;
import net.lixir.pre.recipe.InductionSmeltingRecipe;
import net.lixir.pre.registry.tag.PREItemTags;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.*;
import net.minecraft.recipe.book.RecipeBookType;
import net.minecraft.recipe.input.SmithingRecipeInput;
import net.minecraft.screen.AbstractRecipeScreenHandler;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.MathHelper;

import java.util.List;

public class InductionFurnaceScreenHandler extends AbstractRecipeScreenHandler {
    private final Inventory inventory;
    private final PropertyDelegate propertyDelegate;

    public InductionFurnaceScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(
                PREScreenHandlerTypes.INDUCTION_FURNACE,
                syncId,
                playerInventory,
                new SimpleInventory(4),
                new ArrayPropertyDelegate(2)
        );
    }

    public InductionFurnaceScreenHandler(ScreenHandlerType<?> screenHandlerType,
                                         int syncId,
                                         PlayerInventory playerInventory,
                                         Inventory inventory,
                                         PropertyDelegate propertyDelegate
    ) {
        super(screenHandlerType, syncId);
        checkSize(inventory, 4);
        checkDataCount(propertyDelegate, 2);
        this.propertyDelegate = propertyDelegate;
        this.inventory = inventory;
        this.addSlot(new Slot(inventory, InductionFurnaceBlockEntity.BASE_SLOT_INDEX, 24, 35) {
            @Override
            public boolean canInsert(ItemStack stack) {
                return !stack.isStackable() || stack.isIn(PREItemTags.INDUCTION_SMELTING_BASE);
            }
        });
        this.addSlot(new Slot(inventory, InductionFurnaceBlockEntity.ADDITION_SLOT_INDEX, 45, 35) {
            @Override
            public boolean canInsert(ItemStack stack) {
                return stack.isStackable() && !stack.isIn(PREItemTags.INDUCTION_SMELTING_BASE);
            }
        });
        this.addSlot(new Slot(inventory, InductionFurnaceBlockEntity.TEMPLATE_SLOT_INDEX, 84, 35) {
            @Override
            public boolean canInsert(ItemStack stack) {
                return stack.isIn(PREItemTags.INDUCTION_FURNACE_UPGRADE);
            }
        });
        this.addSlot(new Slot(inventory, InductionFurnaceBlockEntity.OUTPUT_SLOT_INDEX, 138, 35) {
            @Override
            public boolean canInsert(ItemStack stack) {
                return false;
            }

            @Override
            public boolean canTakeItems(PlayerEntity player) {
                return true;
            }
        });
        this.addPlayerSlots(playerInventory, 8, 84);
        this.addProperties(propertyDelegate);
    }

    public float getSmeltProgress() {
        int current = this.propertyDelegate.get(0);
        int max = this.propertyDelegate.get(1);

        return max > 0
                ? MathHelper.clamp((float) current / (float) max, 0.0F, 1.0F)
                : 0.0F;
    }

    @Override
    public PostFillAction fillInputSlots(boolean craftAll, boolean creative, RecipeEntry<?> recipe, ServerWorld world, PlayerInventory inventory) {
        final List<Slot> list = List.of(
                this.getSlot(InductionFurnaceBlockEntity.TEMPLATE_SLOT_INDEX),
                this.getSlot(InductionFurnaceBlockEntity.BASE_SLOT_INDEX),
                this.getSlot(InductionFurnaceBlockEntity.ADDITION_SLOT_INDEX)
        );
        return InputSlotFiller.fill(new InputSlotFiller.Handler<>() {
            @Override
            public void populateRecipeFinder(RecipeFinder finder) {
                InductionFurnaceScreenHandler.this.populateRecipeFinder(finder);
            }

            @Override
            public void clear() {
                list.forEach(slot -> slot.setStackNoCallbacks(ItemStack.EMPTY));
            }

            @Override
            public boolean matches(RecipeEntry<InductionSmeltingRecipe> entry) {
                return entry.value().matches(new SmithingRecipeInput(
                        InductionFurnaceScreenHandler.this.inventory.getStack(InductionFurnaceBlockEntity.TEMPLATE_SLOT_INDEX),
                        InductionFurnaceScreenHandler.this.inventory.getStack(InductionFurnaceBlockEntity.BASE_SLOT_INDEX),
                        InductionFurnaceScreenHandler.this.inventory.getStack(InductionFurnaceBlockEntity.ADDITION_SLOT_INDEX)
                ), world);
            }
        }, 1, 1, List.of(this.getSlot(0)), list, inventory, (RecipeEntry<InductionSmeltingRecipe>) recipe, craftAll, creative);
    }

    @Override
    public void populateRecipeFinder(RecipeFinder finder) {
        if (this.inventory instanceof RecipeInputProvider) {
            ((RecipeInputProvider) this.inventory).provideRecipeInputs(finder);
        }
    }

    @Override
    public RecipeBookType getCategory() {
        return RecipeBookType.CRAFTING;
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int slotIndex) {
        ItemStack original;
        Slot slot = this.slots.get(slotIndex);

        if (!slot.hasStack()) {
            return ItemStack.EMPTY;
        }

        ItemStack stack = slot.getStack();
        original = stack.copy();

        final int PLAYER_START = 4;
        final int PLAYER_END = this.slots.size();

        if (slotIndex == InductionFurnaceBlockEntity.OUTPUT_SLOT_INDEX) {
            if (!this.insertItem(stack, PLAYER_START, PLAYER_END, true)) {
                return ItemStack.EMPTY;
            }
            slot.onQuickTransfer(stack, original);
        } else if (slotIndex < PLAYER_START) {
            if (!this.insertItem(stack, PLAYER_START, PLAYER_END, false)) {
                return ItemStack.EMPTY;
            }
        } else {
            if (!this.insertItem(stack, 0, InductionFurnaceBlockEntity.OUTPUT_SLOT_INDEX, false)) {
                return ItemStack.EMPTY;
            }
        }

        if (stack.isEmpty()) {
            slot.setStack(ItemStack.EMPTY);
        } else {
            slot.markDirty();
        }

        if (stack.getCount() == original.getCount()) {
            return ItemStack.EMPTY;
        }

        slot.onTakeItem(player, stack);
        return original;
    }

    public Slot getOutputSlot() {
        return this.slots.get(InductionFurnaceBlockEntity.OUTPUT_SLOT_INDEX);
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }
}
