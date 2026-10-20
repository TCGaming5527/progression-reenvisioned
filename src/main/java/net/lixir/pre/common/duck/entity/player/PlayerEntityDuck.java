package net.lixir.pre.common.duck.entity.player;

public interface PlayerEntityDuck {
    default int getLungeCooldown() {
        return 0;
    }

    default void setLungeCooldown(int lungeCooldown) {

    }
}
