package net.lixir.pre.block.entity;

import net.lixir.pre.ProgressionReEnvisioned;
import net.lixir.pre.block.PREBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.datafixer.TypeReferences;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Util;

import java.util.Set;

public class PREBlockEntityTypes {
    public static BlockEntityType<RecyclerBlockEntity> RECYCLER = register(
            "recycler",
            RecyclerBlockEntity::new,
            PREBlocks.RECYCLER
    );
    public static BlockEntityType<PlatformBlockEntity> PLATFORM = register(
            "platform",
            PlatformBlockEntity::new,
            PREBlocks.PLATFORM
    );
    public static BlockEntityType<InductionFurnaceBlockEntity> INDUCTION_FURNACE = register(
            "induction_furnace",
            InductionFurnaceBlockEntity::new,
            PREBlocks.INDUCTION_FURNACE
    );

    private static <T extends BlockEntity> BlockEntityType<T> register(String id, BlockEntityType.BlockEntityFactory<? extends T> factory, Block... blocks) {
        Util.getChoiceType(TypeReferences.BLOCK_ENTITY, id);
        return Registry.register(Registries.BLOCK_ENTITY_TYPE, ProgressionReEnvisioned.id(id), new BlockEntityType<>(factory, Set.of(blocks)));
    }

    public static void initialize() {

    }
}
