package net.lixir.pre.common.block.entity;


import net.lixir.pre.common.ProgressionReEnvisioned;
import net.lixir.pre.common.block.InductionFurnaceBlock;
import net.lixir.pre.common.init.PREBlockEntityTypes;
import net.lixir.pre.common.item.ItemStackUtil;
import net.lixir.pre.common.recipe.InductionSmeltingRecipe;
import net.lixir.pre.common.init.PRERecipeTypes;
import net.lixir.pre.common.init.tag.PREItemTags;
import net.lixir.pre.common.screen.InductionFurnaceScreenHandler;
import net.lixir.pre.common.init.PREScreenHandlerTypes;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.LockableContainerBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.*;
import net.minecraft.recipe.input.SmithingRecipeInput;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class InductionFurnaceBlockEntity extends LockableContainerBlockEntity implements SidedInventory {
    protected DefaultedList<ItemStack> inventory = DefaultedList.ofSize(4, ItemStack.EMPTY);
    private final ServerRecipeManager.MatchGetter<SmithingRecipeInput, ? extends InductionSmeltingRecipe> matchGetter;

    public InductionFurnaceBlockEntity(BlockPos pos, BlockState state) {
        super(PREBlockEntityTypes.INDUCTION_FURNACE, pos, state);
        this.matchGetter = ServerRecipeManager.createCachedMatchGetter(PRERecipeTypes.INDUCTION_SMELTING);
    }

    public static final int BASE_SLOT_INDEX = 0;
    public static final int ADDITION_SLOT_INDEX = 1;
    public static final int TEMPLATE_SLOT_INDEX = 2;
    public static final int OUTPUT_SLOT_INDEX = 3;

    private int progress;
    private int maxProgress;

    protected final PropertyDelegate propertyDelegate = new PropertyDelegate() {
        @Override
        public int get(int index) {
            return switch (index) {
                case 0 -> InductionFurnaceBlockEntity.this.progress;
                case 1 -> InductionFurnaceBlockEntity.this.maxProgress;
                default -> 0;
            };
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case 0:
                    InductionFurnaceBlockEntity.this.progress = value;
                    break;
                case 1:
                    InductionFurnaceBlockEntity.this.maxProgress = value;
                    break;
            }
        }

        @Override
        public int size() {
            return 2;
        }
    };

    @Override
    public int size() {
        return inventory.size();
    }

    @Override
    protected Text getContainerName() {
        return Text.translatable("container." + ProgressionReEnvisioned.ID + ".induction_furnace");
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        super.writeNbt(nbt, registries);
        if (nbt.contains("Progress"))
            this.progress = nbt.getInt("Progress").get();
        if (nbt.contains("MaxProgress"))
            this.maxProgress = nbt.getInt("MaxProgress").get();
        Inventories.writeNbt(nbt, this.inventory, registries);
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        super.readNbt(nbt, registries);
        Inventories.readNbt(nbt, this.inventory, registries);
        nbt.putInt("Progress", progress);
        nbt.putInt("MaxProgress", maxProgress);
    }

    @Nullable
    public ItemStack getTemplate() {
        return this.inventory.get(TEMPLATE_SLOT_INDEX);
    }

    @Nullable
    public ItemStack getBase() {
        return this.inventory.get(BASE_SLOT_INDEX);
    }

    @Nullable
    public ItemStack getAddition() {
        return this.inventory.get(ADDITION_SLOT_INDEX);
    }

    @Nullable
    public ItemStack getOutput() {
        return this.inventory.get(OUTPUT_SLOT_INDEX);
    }

    @Override
    public boolean isValid(int slot, ItemStack stack) {
        if (slot != TEMPLATE_SLOT_INDEX && stack.isIn(PREItemTags.INDUCTION_FURNACE_UPGRADE))
            return false;
        return slot != OUTPUT_SLOT_INDEX;
    }

    public boolean isFull() {
        ItemStack template = this.getTemplate();
        ItemStack base = this.getBase();
        ItemStack addition = this.getAddition();
        return isFull(template, base, addition);
    }

    public boolean isFull(ItemStack template, ItemStack base, ItemStack addition) {
        return (template != null && !template.isEmpty())
                && (base != null && !base.isEmpty())
                && (addition != null && !addition.isEmpty());
    }

    public static void tick(ServerWorld world, BlockPos pos, @NotNull BlockState state, @NotNull InductionFurnaceBlockEntity be) {
        ItemStack template = be.getTemplate();
        ItemStack base = be.getBase();
        ItemStack addition = be.getAddition();

        boolean wasLit = state.get(InductionFurnaceBlock.LIT);
        boolean isSmelting = false;

        if (be.isFull(template, base, addition)) {
            SmithingRecipeInput input = new SmithingRecipeInput(template, base, addition);
            RecipeEntry<? extends InductionSmeltingRecipe> recipeEntry =
                    be.matchGetter.getFirstMatch(input, world).orElse(null);

            if (recipeEntry != null &&
                    canAcceptRecipeOutput(world.getRegistryManager(), recipeEntry, input, be.inventory, be.getMaxCountPerStack())) {

                InductionSmeltingRecipe recipe = recipeEntry.value();

                if (be.maxProgress != recipe.smeltTicks()) {
                    be.maxProgress = recipe.smeltTicks();
                    be.progress = 0;
                }

                be.progress++;
                isSmelting = true;

                if (be.progress >= be.maxProgress) {
                    craftRecipe(world.getRegistryManager(), recipeEntry, input, be.inventory);
                    be.resetProgress();
                    isSmelting = false;
                }
            } else {
                be.resetProgress();
            }
        } else {
            be.resetProgress();
        }

        if (wasLit != isSmelting) {
            setLit(world, pos, state, isSmelting);
        }

        if (isSmelting) {
            be.markDirty();
        }
    }

    private void resetProgress() {
        this.progress = 0;
        this.maxProgress = 0;
    }

    private static void setLit(ServerWorld world, BlockPos pos, @NotNull BlockState state, boolean lit) {
        if (state.get(InductionFurnaceBlock.LIT) != lit) {
            world.setBlockState(
                    pos,
                    state.with(InductionFurnaceBlock.LIT, lit),
                    Block.NOTIFY_ALL
            );
        }
    }

    private static boolean craftRecipe(DynamicRegistryManager registryManager,
                                       @Nullable RecipeEntry<? extends InductionSmeltingRecipe> recipeEntry,
                                       SmithingRecipeInput input,
                                       DefaultedList<ItemStack> inventory) {
        if (recipeEntry == null) return false;
        InductionSmeltingRecipe recipe = recipeEntry.value();
        ItemStack result = recipe.craft(input, registryManager);
        if (result.isEmpty()) return false;

        ItemStack outputStack = inventory.get(OUTPUT_SLOT_INDEX);
        if (outputStack.isEmpty()) {
            inventory.set(OUTPUT_SLOT_INDEX, result.copy());
        } else if (ItemStack.areItemsEqual(outputStack, result)) {
            outputStack.increment(result.getCount());
        } else {
            return false;
        }

        inventory.get(TEMPLATE_SLOT_INDEX).decrement(1);
        inventory.get(BASE_SLOT_INDEX).decrement(1);
        inventory.get(ADDITION_SLOT_INDEX).decrement(recipe.additionCount());

        return true;
    }

    private static boolean canAcceptRecipeOutput(DynamicRegistryManager registryManager,
                                                 @Nullable RecipeEntry<? extends InductionSmeltingRecipe> recipe,
                                                 SmithingRecipeInput input,
                                                 DefaultedList<ItemStack> inventory,
                                                 int maxCount) {
        if (recipe == null) return false;

        ItemStack result = recipe.value().craft(input, registryManager);
        if (result.isEmpty()) return false;

        // Check output slot
        ItemStack outputStack = inventory.get(OUTPUT_SLOT_INDEX);
        if (!outputStack.isEmpty() && !ItemStack.areItemsEqual(outputStack, result)) return false;
        if (!outputStack.isEmpty() &&
                outputStack.getCount() + result.getCount() > Math.min(maxCount, outputStack.getMaxCount())) return false;

        // Check input slots
        if (inventory.get(TEMPLATE_SLOT_INDEX).getCount() < 1) return false;
        if (inventory.get(BASE_SLOT_INDEX).getCount() < 1) return false;
        if (inventory.get(ADDITION_SLOT_INDEX).getCount() < recipe.value().additionCount()) return false;

        return true;
    }


    @Override
    protected DefaultedList<ItemStack> getHeldStacks() {
        return inventory;
    }

    @Override
    protected void setHeldStacks(DefaultedList<ItemStack> stacks) {
        this.inventory = stacks;
    }

    @Override
    public boolean isEmpty() {
        for (ItemStack stack : inventory) {
            if (!stack.isEmpty()) return false;
        }
        return true;
    }

    @Override
    public ItemStack getStack(int slot) {
        return inventory.get(slot);
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        return ItemStackUtil.splitStack(inventory, slot, amount);
    }

    @Override
    public ItemStack removeStack(int slot) {
        return ItemStackUtil.takeStack(inventory, slot);
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        inventory.set(slot, stack);
        if (stack.getCount() > getMaxCountPerStack()) {
            stack.setCount(getMaxCountPerStack());
        }
        markDirty();
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        if (world == null || world.getBlockEntity(pos) != this) return false;
        return player.squaredDistanceTo(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5) <= 64;
    }

    @Override
    public void clear() {
        inventory.clear();
    }

    @Override
    protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
        return new InductionFurnaceScreenHandler(
                PREScreenHandlerTypes.INDUCTION_FURNACE,
                syncId,
                playerInventory,
                this,
                this.propertyDelegate
        );
    }


    @Override
    public int[] getAvailableSlots(Direction side) {
        return new int[0];
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack, @Nullable Direction dir) {
        return this.isValid(slot, stack);
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction dir) {
        return false;
    }
}
