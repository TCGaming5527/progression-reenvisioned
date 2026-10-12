package net.lixir.pre.duck.item;

import net.lixir.pre.item.HandTransformationType;

public interface ItemDuck {
    default HandTransformationType getHandTransformationType() {
        return null;
    }
}
