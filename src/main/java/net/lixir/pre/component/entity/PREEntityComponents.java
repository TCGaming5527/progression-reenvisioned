package net.lixir.pre.component.entity;

import net.lixir.pre.ProgressionReEnvisioned;
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
        registry.registerForPlayers(REWIND_DATA, RewindComponentImpl::new, RespawnCopyStrategy.NEVER_COPY);
    }
}
