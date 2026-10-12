package net.lixir.pre.client.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;

import net.lixir.pre.data.RecyclableItem;
import net.minecraft.data.DataProvider;
import net.minecraft.data.DataWriter;
import net.minecraft.item.Item;
import net.minecraft.loot.LootTable;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public abstract class RecyclableItemProvider implements DataProvider {
    protected final FabricDataOutput output;
    protected final String modId;
    private final CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup;

    private final Map<Identifier, RecyclableItem> entries = new HashMap<>();

    protected RecyclableItemProvider(@NotNull FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        this.output = output;
        this.modId = output.getModId();
        this.registryLookup = registryLookup;
    }

    protected abstract void addRecyclableItems();

    protected void add(Item item, RegistryKey<LootTable> lootTable) {
        Identifier itemId = Registries.ITEM.getId(item);
        Identifier key = Identifier.of(modId, itemId.getPath());
        entries.put(
                key,
                new RecyclableItem(itemId, lootTable)
        );
    }

    @Override
    public CompletableFuture<?> run(DataWriter writer) {
        addRecyclableItems();

        Map<Identifier, RecyclableItem> toWrite = Map.copyOf(entries);

        return DataProvider.writeAllToPath(
                writer,
                RecyclableItem.CODEC,
                this::getPath,
                toWrite
        );
    }

    private Path getPath(Identifier id) {
        return output.getPath()
                .resolve("data")
                .resolve(id.getNamespace())
                .resolve("recyclable_items")
                .resolve(id.getPath() + ".json");
    }

    @Override
    public String getName() {
        return "Recyclable Item Provider";
    }
}
