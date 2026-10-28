package net.lixir.pre.common.init;

import com.mojang.serialization.Codec;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.lixir.pre.common.ProgressionReEnvisioned;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public interface PREAttachmentTypes {

    // replace with ItemStackWithSlot once on newer versions instead of map
    AttachmentType<Map<Integer, ItemStack>> RELICS = register("relics", builder ->
            builder.initializer(HashMap::new).persistent(Codec.unboundedMap(Codec.stringResolver(Object::toString, Integer::parseInt), ItemStack.CODEC)));

    private static <T> AttachmentType<T> register(String id, Consumer<AttachmentRegistry.Builder<T>> builder) {
        return AttachmentRegistry.create(ProgressionReEnvisioned.id(id), builder);
    }

    static void initialize() {

    }
}
