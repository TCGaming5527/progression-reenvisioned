package net.lixir.pre.duck.entity.player;

public interface PlayerEntityDuck {
    default int getLungeCooldown() {
        return 0;
    }

    default void setLungeCooldown(int lungeCooldown) {

    }
}
