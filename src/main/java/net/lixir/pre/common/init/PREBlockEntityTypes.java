package net.lixir.pre.common.init;

import net.lixir.pre.common.ProgressionReEnvisioned;
import net.lixir.pre.common.block.entity.InductionFurnaceBlockEntity;
import net.lixir.pre.common.block.entity.PlatformBlockEntity;
import net.lixir.pre.common.block.entity.RecyclerBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.datafixer.TypeReferences;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Util;

import java.util.Set;

public interface PREBlockEntityTypes {
    BlockEntityType<RecyclerBlockEntity> RECYCLER = register(
            "recycler",
            RecyclerBlockEntity::new,
            PREBlocks.RECYCLER
    );
    BlockEntityType<PlatformBlockEntity> PLATFORM = register(
            "platform",
            PlatformBlockEntity::new,
            PREBlocks.PLATFORM
    );
    BlockEntityType<InductionFurnaceBlockEntity> INDUCTION_FURNACE = register(
            "induction_furnace",
            InductionFurnaceBlockEntity::new,
            PREBlocks.INDUCTION_FURNACE
    );

    static <T extends BlockEntity> BlockEntityType<T> register(String id, BlockEntityType.BlockEntityFactory<? extends T> factory, Block... blocks) {
        Util.getChoiceType(TypeReferences.BLOCK_ENTITY, id);
        return Registry.register(Registries.BLOCK_ENTITY_TYPE, ProgressionReEnvisioned.id(id), new BlockEntityType<>(factory, Set.of(blocks)));
    }

    static void initialize() {

    }
}
