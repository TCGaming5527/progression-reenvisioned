package net.lixir.pre.common.block.entity;

import lombok.Getter;
import net.lixir.pre.common.block.RecyclerBlock;
import net.lixir.pre.common.data.RecyclableItem;
import net.lixir.pre.common.data.RecyclableItemDataLoader;
import net.lixir.pre.common.init.PREBlockEntityTypes;
import net.lixir.pre.common.init.PRESoundEvents;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.loot.context.LootWorldContext;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class RecyclerBlockEntity extends BlockEntity
        implements SidedInventory {

    private static final int[] SLOT = new int[]{0};
    @Getter
    private int openTicks = 0;
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(1, ItemStack.EMPTY);

    public RecyclerBlockEntity(BlockPos pos, BlockState state) {
        super(PREBlockEntityTypes.RECYCLER, pos, state);
    }


    public static void tick(ServerWorld world, BlockPos pos, @NotNull BlockState state, @NotNull RecyclerBlockEntity be) {
        if (be.getOpenTicks() > 0) {
            be.setOpenTicks(be.getOpenTicks() - 1);
            if (be.getOpenTicks() == 0) {
                be.closeBlock();
            }
        }
    }

    @Override
    public int[] getAvailableSlots(Direction side) {
        return SLOT;
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        super.writeNbt(nbt, registries);
        Inventories.writeNbt(nbt, this.inventory, registries);
        nbt.putInt("OpenTicks", openTicks);
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        super.readNbt(nbt, registries);
        Inventories.readNbt(nbt, this.inventory, registries);
        openTicks = nbt.getInt("OpenTicks", 0);
    }
    @Override
    public boolean canInsert(int slot, @NotNull ItemStack stack, @Nullable Direction dir) {
        return getStack(slot).isEmpty();
    }

    public void setOpenTicks(int ticks) {
        this.openTicks = ticks;
        markDirty();
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction dir) {
        return false;
    }

    public boolean tryRecycle() {
        if (getOpenTicks() > 0)
            return false;
        ItemStack stack = inventory.getFirst();
        if (stack.isEmpty()) return false;
        if (!(world instanceof ServerWorld serverWorld)) return false;

        int count = stack.getCount();

        Direction facing = world.getBlockState(pos).get(RecyclerBlock.FACING);
        Vec3d offset = new Vec3d(facing.getOffsetX(), 0, facing.getOffsetZ()).multiply(0.75);

        double spawnX = pos.getX() + 0.5 + offset.x;
        double spawnY = pos.getY() + 0.5 + 0.2;
        double spawnZ = pos.getZ() + 0.5 + offset.z;
        Identifier itemId = Registries.ITEM.getId(stack.getItem());
        RecyclableItem recyclable = RecyclableItemDataLoader.get(itemId);
        if (recyclable == null) {
            inventory.set(0, ItemStack.EMPTY);
            openBlock();
            markDirty();
            var itemEntity = new ItemEntity(
                    world,
                    spawnX,
                    spawnY,
                    spawnZ,
                    stack
            );
            Vec3d motion = new Vec3d(
                    facing.getOffsetX() * world.random.nextFloat(),
                    0.2,
                    facing.getOffsetZ() * world.random.nextFloat()
            ).multiply(0.2);

            itemEntity.setVelocity(motion);
            serverWorld.spawnEntity(itemEntity);
            world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), PRESoundEvents.BLOCK_RECYCLER_FAIL, SoundCategory.BLOCKS);
            return false;
        }


        LootTable lootTable = serverWorld
                .getServer()
                .getReloadableRegistries()
                .getLootTable(recyclable.lootTable());

        LootWorldContext context = new LootWorldContext.Builder(serverWorld)
                .add(LootContextParameters.ORIGIN, Vec3d.ofCenter(pos))
                .luck(0.0F)
                .build(LootContextTypes.CHEST);

        inventory.set(0, ItemStack.EMPTY);

        for (int i = 0; i < count; i++) {
            for (ItemStack drop : lootTable.generateLoot(context)) {
                var itemEntity = new ItemEntity(
                        world,
                        spawnX,
                        spawnY,
                        spawnZ,
                        drop
                );
                Vec3d motion = new Vec3d(
                        facing.getOffsetX() * world.random.nextFloat(),
                        0.2,
                        facing.getOffsetZ() * world.random.nextFloat()
                ).multiply(0.2);

                itemEntity.setVelocity(motion);
                serverWorld.spawnEntity(itemEntity);
            }
        }
        serverWorld.spawnParticles(
                new ItemStackParticleEffect(ParticleTypes.ITEM, stack),
                spawnX,
                spawnY,
                spawnZ,
                15,
                0.2, 0.2, 0.2, 0.05
        );

        openBlock();
        markDirty();
        return true;
    }

    public void closeBlock() {
        if (world == null)
            return;
        world.setBlockState(pos, world.getBlockState(pos).with(RecyclerBlock.OPEN, false));
    }

    public void openBlock() {
        if (world == null)
            return;
        world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), PRESoundEvents.BLOCK_RECYCLER_EMPTY, SoundCategory.BLOCKS);
        world.setBlockState(pos, world.getBlockState(pos).with(RecyclerBlock.OPEN, true));
        setOpenTicks(10);
    }


    @Override
    public int size() {
        return inventory.size();
    }

    @Override
    public boolean isEmpty() {
        return inventory.getFirst().isEmpty();
    }

    @Override
    public ItemStack getStack(int slot) {
        return inventory.get(slot);
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        ItemStack result = Inventories.splitStack(inventory, slot, amount);
        if (!result.isEmpty()) {
            markDirty();
        }
        return result;
    }

    @Override
    public ItemStack removeStack(int slot) {
        ItemStack result = Inventories.removeStack(inventory, slot);
        if (!result.isEmpty()) {
            markDirty();
        }
        return result;
    }

    public ItemStack insertItem(@NotNull ItemStack stack) {
        if (stack.isEmpty()) return ItemStack.EMPTY;

        if (inventory.getFirst().isEmpty()) {
            inventory.set(0, stack.split(stack.getCount()));
            markDirty();
            return ItemStack.EMPTY;
        }

        return stack;
    }

    @Override
    public void setStack(int slot, @NotNull ItemStack stack) {
        inventory.set(slot, stack);

        if (stack.getCount() > getMaxCountPerStack()) {
            stack.setCount(getMaxCountPerStack());
        }

        markDirty();
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        if (world == null) return false;
        return world.getBlockEntity(pos) == this &&
                player.squaredDistanceTo(
                        pos.getX() + 0.5,
                        pos.getY() + 0.5,
                        pos.getZ() + 0.5
                ) <= 64.0;
    }

    @Override
    public void clear() {
        inventory.clear();
        markDirty();
    }
}
