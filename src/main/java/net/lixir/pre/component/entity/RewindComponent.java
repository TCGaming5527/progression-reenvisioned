package net.lixir.pre.component.entity;

import net.lixir.pre.util.EvictingList;
import net.lixir.pre.util.PositionAngle;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;

public interface RewindComponent extends AutoSyncedComponent {
    boolean isRewinding();

    void setRewinding(boolean rewind);

    void addToTimeline(PositionAngle positionAngle);

    EvictingList<PositionAngle> getTimeline();
}
