package net.lixir.pre.common.data;

import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.lixir.pre.common.ProgressionReEnvisioned;
import net.minecraft.resource.JsonDataLoader;
import net.minecraft.resource.ResourceFinder;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class RecyclableItemDataLoader extends JsonDataLoader<RecyclableItem> implements IdentifiableResourceReloadListener {
    private static final Logger LOGGER = LoggerFactory.getLogger("RecyclableItemLoader");

    public static final RecyclableItemDataLoader INSTANCE = new RecyclableItemDataLoader();
    private static final Map<Identifier, RecyclableItem> ENTRIES = new HashMap<>();

    private RecyclableItemDataLoader() {
        super(RecyclableItem.CODEC, ResourceFinder.json("recyclable_items"));
    }
    @Override
    protected void apply(
            Map<Identifier, RecyclableItem> prepared,
            ResourceManager manager,
            Profiler profiler
    ) {
        ENTRIES.clear();
        for (RecyclableItem entry : prepared.values()) {
            ENTRIES.put(entry.item(), entry);
        }

        LOGGER.info("Loaded {} recyclable item(s)", ENTRIES.size());
    }


    public static Map<Identifier, RecyclableItem> getEntries() {
        return ENTRIES;
    }

    public static RecyclableItem get(Identifier id) {
        return ENTRIES.get(id);
    }

    @Override
    public Identifier getFabricId() {
        return ProgressionReEnvisioned.id("recyclable_items");
    }
}
