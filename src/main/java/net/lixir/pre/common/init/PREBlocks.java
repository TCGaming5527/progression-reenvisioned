package net.lixir.pre.common.init;

import net.lixir.pre.common.ProgressionReEnvisioned;
import net.lixir.pre.common.block.InductionFurnaceBlock;
import net.lixir.pre.common.block.PlatformBlock;
import net.lixir.pre.common.block.RecyclerBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.BlockSoundGroup;

import java.util.function.Function;

public interface PREBlocks {
    Block PLATFORM = register(
            "platform",
            PlatformBlock::new,
            AbstractBlock.Settings.create()
                    .resistance(12000f)
                    .hardness(-1f)
                    .nonOpaque()
                    .dropsNothing()
                    .suffocates((a, b, c) -> false)
                    .emissiveLighting((state, world, pos) -> true)
    );
    Block RECYCLER = register("recycler", RecyclerBlock::new, AbstractBlock.Settings.create()
            .sounds(BlockSoundGroup.VAULT).resistance(1f).hardness(1f));
    Block INDUCTION_FURNACE = register("induction_furnace", InductionFurnaceBlock::new,
            AbstractBlock.Settings.copy(Blocks.BLAST_FURNACE));

    static RegistryKey<Block> keyOf(String id) {
        return RegistryKey.of(RegistryKeys.BLOCK, ProgressionReEnvisioned.id(id));
    }

    static Block register(String id, Function<AbstractBlock.Settings, Block> factory, AbstractBlock.Settings settings) {
        return register(keyOf(id), factory, settings);
    }

    static Block register(String id, AbstractBlock.Settings settings) {
        return register(id, Block::new, settings);
    }

    static Block register(RegistryKey<Block> key, Function<AbstractBlock.Settings, Block> factory, AbstractBlock.Settings settings) {
        Block block = factory.apply(settings.registryKey(key));
        return Registry.register(Registries.BLOCK, key, block);
    }

    static void initialize() {

    }
}
