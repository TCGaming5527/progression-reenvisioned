package net.lixir.pre.common.block.entity;

import net.lixir.pre.common.block.PlatformBlock;
import net.lixir.pre.common.init.PREBlockEntityTypes;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class PlatformBlockEntity extends BlockEntity {
    public static final int LIFETIME = 700;
    public static final int BLINK_START = LIFETIME - 80;
    private int age = 0;

    public PlatformBlockEntity(BlockPos pos, BlockState state) {
        super(PREBlockEntityTypes.PLATFORM, pos, state);
    }

    public static void tick(World world, BlockPos pos, BlockState state, PlatformBlockEntity blockEntity) {
        blockEntity.setAge(blockEntity.getAge() + 1);
        if (blockEntity.getAge() >= LIFETIME) {
            world.setBlockState(pos, Blocks.AIR.getDefaultState());
        } else if (blockEntity.getAge() >= BLINK_START) {
            if (!state.get(PlatformBlock.BLINKING)) {
                world.setBlockState(pos, state.with(PlatformBlock.BLINKING, true));
            }
        }
    }

    public int getAge() {
        return age;
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        super.writeNbt(nbt, registries);
        nbt.putInt("Age", age);
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        super.readNbt(nbt, registries);
        this.age = nbt.getInt("Age", 0);
    }

    public void setAge(int age) {
        this.age = age;
        markDirty();
    }
}
