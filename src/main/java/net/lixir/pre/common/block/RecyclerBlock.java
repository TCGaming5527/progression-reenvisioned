package net.lixir.pre.common.block;

import com.mojang.serialization.MapCodec;
import net.lixir.pre.common.init.PREBlockEntityTypes;
import net.lixir.pre.common.block.entity.RecyclerBlockEntity;
import net.lixir.pre.common.init.PRESoundEvents;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.*;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.block.WireOrientation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class RecyclerBlock extends BlockWithEntity implements BlockEntityProvider, SpeechBlock {
    public static final BooleanProperty OPEN = Properties.OPEN;
    public static final EnumProperty<Direction> FACING = Properties.FACING;
    private static final MapCodec<? extends RecyclerBlock> MAP_CODEC = createCodec(RecyclerBlock::new);

    protected BlockState rotate(@NotNull BlockState state, @NotNull BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    protected BlockState mirror(@NotNull BlockState state, @NotNull BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }

    public RecyclerBlock(Settings settings) {
        super(settings);
        setDefaultState(getStateManager().getDefaultState()
                .with(FACING, Direction.NORTH)
                .with(OPEN, false)
        );
    }

    @Override
    public BlockState getPlacementState(@NotNull ItemPlacementContext ctx) {
        return getDefaultState()
                .with(FACING, ctx.getHorizontalPlayerFacing().getOpposite());
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new RecyclerBlockEntity(pos, state);
    }

    @Override
    protected void neighborUpdate(BlockState state, @NotNull World world, BlockPos pos, Block sourceBlock, @Nullable WireOrientation wireOrientation, boolean notify) {
        if (!world.isClient && world.isReceivingRedstonePower(pos)) {
            BlockEntity be = world.getBlockEntity(pos);
            if (be instanceof RecyclerBlockEntity recycler) {
                recycler.tryRecycle();
            }
        }
    }

    @Override
    protected void onStateReplaced(BlockState state, ServerWorld world, BlockPos pos, boolean moved) {
        ItemScatterer.onStateReplaced(state, world, pos);
    }

    @Override
    protected ActionResult onUseWithItem(
            ItemStack stack,
            BlockState state,
            @NotNull World world,
            BlockPos pos,
            @NotNull PlayerEntity player,
            Hand hand,
            BlockHitResult hit
    ) {
        ItemStack held = player.getStackInHand(hand);
        BlockEntity be = world.getBlockEntity(pos);
        if (trySpeak(player, world, state)) {
            return ActionResult.CONSUME;
        }
        if (stack.isEmpty())
            return ActionResult.PASS;
        if (!(be instanceof RecyclerBlockEntity recycler))
            return ActionResult.PASS;

        if (!recycler.canInsert(0, stack, null))
            return ActionResult.PASS;

        if (!world.isClient) {
            world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), PRESoundEvents.BLOCK_RECYCLER_INSERT, SoundCategory.BLOCKS);
            ItemStack remainder = recycler.insertItem(held);
            player.setStackInHand(hand, remainder);
        }
        return ActionResult.CONSUME;
    }

    @Override
    protected void appendProperties(StateManager.@NotNull Builder<Block, BlockState> builder) {
        builder.add(FACING, OPEN);
    }

    @Nullable
    protected static <T extends BlockEntity> BlockEntityTicker<T> validateTicker(
            World world, BlockEntityType<T> givenType, BlockEntityType<? extends RecyclerBlockEntity> expectedType
    ) {
        return world instanceof ServerWorld serverWorld
                ? validateTicker(givenType, expectedType, (worldx, pos, state, blockEntity) -> RecyclerBlockEntity.tick(serverWorld, pos, state, blockEntity))
                : null;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return validateTicker(world, type, PREBlockEntityTypes.RECYCLER);
    }

    @Override
    protected MapCodec<? extends RecyclerBlock> getCodec() {
        return MAP_CODEC;
    }

    @Override
    public SpeechHudType getSpeechHudType() {
        return SpeechHudTypes.RECYCLER;
    }
}
