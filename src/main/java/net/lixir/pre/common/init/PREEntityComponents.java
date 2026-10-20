package net.lixir.pre.common.init;

import net.lixir.pre.common.ProgressionReEnvisioned;
import net.lixir.pre.common.component.entity.RewindComponent;
import org.jetbrains.annotations.NotNull;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentFactoryRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentInitializer;
import org.ladysnake.cca.api.v3.entity.RespawnCopyStrategy;

public class PREEntityComponents implements EntityComponentInitializer {
    public static final ComponentKey<RewindComponent> REWIND_DATA = ComponentRegistry.getOrCreate(
            ProgressionReEnvisioned.id("rewind"),
            RewindComponent.class
    );

    @Override
    public void registerEntityComponentFactories(@NotNull EntityComponentFactoryRegistry registry) {
        registry.registerForPlayers(REWIND_DATA, RewindComponent::new, RespawnCopyStrategy.NEVER_COPY);
    }
}
