package net.lixir.pre.component.entity;

import lombok.val;
import net.lixir.pre.util.EvictingList;
import net.lixir.pre.util.PositionAngle;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.ladysnake.cca.api.v3.entity.RespawnableComponent;

public class RewindComponentImpl implements RewindComponent, RespawnableComponent<RewindComponent> {
    private final PlayerEntity player;
    private boolean isRewinding = false;
    private EvictingList<PositionAngle> timeline = new EvictingList<>(30);

    public RewindComponentImpl(PlayerEntity player) {
        this.player = player;
    }

    public static void tick(PlayerEntity player, World world) {

    }

    @Override
    public void readFromNbt(@NotNull NbtCompound nbt, RegistryWrapper.WrapperLookup wrapperLookup) {
        val timeline = new EvictingList<PositionAngle>(30);
        val timelineNbt = nbt.getListOrEmpty("Timeline");
        for (final NbtElement positionAngleNbt : timelineNbt) {
            val positionAngle = PositionAngle.fromNbt((NbtCompound) positionAngleNbt);
            timeline.add(positionAngle);
        }
        this.timeline = timeline;
        this.isRewinding = nbt.getBoolean("IsRewinding", false);
    }

    @Override
    public void writeToNbt(@NotNull NbtCompound nbt, RegistryWrapper.WrapperLookup wrapperLookup) {
        var i = 0;
        val timelineTag = new NbtList();
        for (PositionAngle positionAngle : timeline) {
            timelineTag.add(i, positionAngle.toNbt());
            i++;
        }
        nbt.put("Timeline", timelineTag);
        nbt.putBoolean("IsRewinding", isRewinding);
    }

    @Override
    public boolean isRewinding() {
        return this.isRewinding;
    }

    @Override
    public void setRewinding(boolean rewind) {
        this.isRewinding = rewind;
        PREEntityComponents.REWIND_DATA.sync(this.player);
    }

    @Override
    public void addToTimeline(PositionAngle positionAngle) {
        timeline.add(positionAngle);
    }

    @Override
    public EvictingList<PositionAngle> getTimeline() {
        return timeline;
    }
}
