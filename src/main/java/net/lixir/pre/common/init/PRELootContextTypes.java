package net.lixir.pre.common.init;

import net.lixir.pre.common.ProgressionReEnvisioned;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.util.Identifier;
import net.minecraft.util.context.ContextType;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class PRELootContextTypes {
    public static final ContextType RECYCLING = register("recycling", (builder) -> builder.require(LootContextParameters.ORIGIN).require(LootContextParameters.THIS_ENTITY));

    private static ContextType register(String name, @NotNull Consumer<ContextType.Builder> type) {
        ContextType.Builder builder = new ContextType.Builder();
        type.accept(builder);
        ContextType contextType = builder.build();
        Identifier identifier = ProgressionReEnvisioned.id(name);
        ContextType contextType2 = LootContextTypes.MAP.put(identifier, contextType);
        if (contextType2 != null) {
            throw new IllegalStateException("Loot table parameter set " + identifier + " is already registered");
        } else {
            return contextType;
        }
    }

    public static void initialize() {

    }
}
