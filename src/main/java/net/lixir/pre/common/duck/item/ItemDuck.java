package net.lixir.pre.common.duck.item;

import net.lixir.pre.common.item.HandTransformationType;

public interface ItemDuck {
    default HandTransformationType getHandTransformationType() {
        return null;
    }
}
