package net.lixir.pre.client.duck.model;

import net.lixir.pre.client.render.HandTransformation;

public interface BipedEntityRenderStateDuck {
    default void setHandTransformation(HandTransformation handTransformation) {

    }

    default HandTransformation getHandTransformation() {
        return null;
    }
}
