package net.lixir.pre.common.block;

import com.mojang.serialization.MapCodec;
import net.lixir.pre.common.init.PREBlockEntityTypes;
import net.lixir.pre.common.block.entity.PlatformBlockEntity;
import net.lixir.pre.common.state.property.PREProperties;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class PlatformBlock extends BlockWithEntity {
    public static final MapCodec<PlatformBlock> CODEC = createCodec(PlatformBlock::new);
    public static final BooleanProperty BLINKING = PREProperties.BLINKING;

    public PlatformBlock(Settings settings) {
        super(settings);
        this.setDefaultState(getStateManager().getDefaultState().with(BLINKING, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder.add(BLINKING));
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return CODEC;
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new PlatformBlockEntity(pos, state);
    }

    protected static <T extends BlockEntity> BlockEntityTicker<T> validateTicker(
            World world, BlockEntityType<T> givenType, BlockEntityType<? extends PlatformBlockEntity> expectedType
    ) {
        return world instanceof ServerWorld serverWorld
                ? validateTicker(givenType, expectedType, (worldx, pos, state, blockEntity) -> PlatformBlockEntity.tick(serverWorld, pos, state, blockEntity))
                : null;
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(World givenWorld, BlockState givenState, BlockEntityType<T> type) {
        return validateTicker(givenWorld, type, PREBlockEntityTypes.PLATFORM);
    }
}
