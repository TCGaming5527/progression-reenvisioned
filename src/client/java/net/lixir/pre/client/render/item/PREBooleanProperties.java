package net.lixir.pre.client.render.item;

import net.lixir.pre.ProgressionReEnvisioned;
import net.minecraft.client.render.item.property.bool.CustomModelDataFlagProperty;
import net.minecraft.util.Identifier;

import static net.minecraft.client.render.item.property.bool.BooleanProperties.ID_MAPPER;

public class PREBooleanProperties {
    public static void initialize() {
        ID_MAPPER.put(ProgressionReEnvisioned.id("player_rewinding"), RewindingProperty.CODEC);
    }
}
