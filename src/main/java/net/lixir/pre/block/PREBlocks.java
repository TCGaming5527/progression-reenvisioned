package net.lixir.pre.block;

import net.lixir.pre.ProgressionReEnvisioned;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;

import java.util.function.Function;

public class PREBlocks {
    public static final Block PLATFORM = register(
            "platform",
            PlatformBlock::new,
            AbstractBlock.Settings.create()
                    .resistance(12000f)
                    .hardness(-1f)
                    .nonOpaque()
                    .dropsNothing()
                    .emissiveLighting((state, world, pos) -> true)
    );
    public static final Block RECYCLER = register("recycler", RecyclerBlock::new, AbstractBlock.Settings.create().resistance(1f).hardness(1f));
    public static final Block INDUCTION_FURNACE = register("induction_furnace", InductionFurnaceBlock::new, AbstractBlock.Settings.copy(Blocks.BLAST_FURNACE));

    private static RegistryKey<Block> keyOf(String id) {
        return RegistryKey.of(RegistryKeys.BLOCK, ProgressionReEnvisioned.id(id));
    }

    private static Block register(String id, Function<AbstractBlock.Settings, Block> factory, AbstractBlock.Settings settings) {
        return register(keyOf(id), factory, settings);
    }

    private static Block register(String id, AbstractBlock.Settings settings) {
        return register(id, Block::new, settings);
    }

    public static Block register(RegistryKey<Block> key, Function<AbstractBlock.Settings, Block> factory, AbstractBlock.Settings settings) {
        Block block = factory.apply(settings.registryKey(key));
        return Registry.register(Registries.BLOCK, key, block);
    }

    public static void initialize() {

    }
}
