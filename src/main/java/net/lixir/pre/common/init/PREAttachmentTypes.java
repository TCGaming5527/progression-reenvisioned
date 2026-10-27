package net.lixir.pre.common.init;

import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.lixir.pre.common.ProgressionReEnvisioned;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public interface PREAttachmentTypes {

    AttachmentType<List<ItemStack>> RELICS = register("relics", builder ->
            builder.initializer(ArrayList::new).persistent(ItemStack.CODEC.listOf()));

    private static <T> AttachmentType<T> register(String id, Consumer<AttachmentRegistry.Builder<T>> builder) {
        return AttachmentRegistry.create(ProgressionReEnvisioned.id(id), builder);
    }

    static void initialize() {

    }
}
