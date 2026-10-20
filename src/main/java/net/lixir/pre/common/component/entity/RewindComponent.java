package net.lixir.pre.common.component.entity;

import lombok.val;
import net.lixir.pre.common.init.PREEntityComponents;
import net.lixir.pre.common.item.OldClockItem;
import net.lixir.pre.common.util.EvictingList;
import net.lixir.pre.common.util.PositionAngle;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.NotNull;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.CommonTickingComponent;

public class RewindComponent implements CommonTickingComponent, AutoSyncedComponent {
    private final PlayerEntity player;

    private static final String IS_REWINDING = "isRewinding";
    private boolean isRewinding = false;

    private static final String TIMELINE = "Timeline";
    private final EvictingList<Vec3d> timeline = new EvictingList<>(30);

    private Vec3d lastPos = Vec3d.ZERO;
    private int rewindTimeout = 0;

    public RewindComponent(PlayerEntity player) {
        this.player = player;
    }

    public static RewindComponent get(PlayerEntity player) {
        return PREEntityComponents.REWIND_DATA.get(player);
    }

    public void sync() {
        PREEntityComponents.REWIND_DATA.sync(this.player);
    }

    @Override
    public void tick() {
        if (this.isRewinding()) {
            ItemStack mainHandStack = this.player.getMainHandStack();

            if (!(mainHandStack.getItem() instanceof OldClockItem) || this.timeline.isEmpty()) {
                this.setRewinding(false);
                this.player.setVelocity(this.player.getVelocity().normalize().multiply(2));
            } else {
                Vec3d target = this.timeline.getLast();

                Vec3d vel = this.player.getPos().relativize(target);
                if (vel.length() > 1) {
                    vel = vel.normalize();
                }

                this.player.setVelocity(this.player.getVelocity().multiply(0.73).add(vel.multiply(0.27)));
                this.player.getWorld().addParticleClient(new DustParticleEffect(0xFF6600, 0.6F), target.getX(), target.getY(), target.getZ(), 0, 0, 0);
                if (!this.player.getWorld().isClient()) {
                    if (this.player.getPos().isInRange(target, 1)) {
                        this.timeline.remove(target);
                        this.rewindTimeout = 0;

                        this.sync();
                        return;
                    }

                    if (this.player.getPos().isInRange(this.lastPos, 0.08)) {
                        this.rewindTimeout++;

                        if (this.rewindTimeout > 10) {
                            this.rewindTimeout = 0;
                            this.setRewinding(false);
                        }
                    } else {
                        this.rewindTimeout = 0;
                    }
                    this.lastPos = this.player.getPos();
                }
            }
        } else if (!this.player.getWorld().isClient()) {
            if (this.player.age % 10 == 0) {
                this.timeline.add(this.player.getPos());
            }
        }
    }

    @Override
    public void readFromNbt(@NotNull NbtCompound nbt, RegistryWrapper.WrapperLookup wrapperLookup) {
        this.timeline.clear();

        NbtList timelineNbt = nbt.getListOrEmpty(TIMELINE);
        for (NbtElement posNbt : timelineNbt) {
            if (posNbt instanceof NbtCompound compound) {
                this.timeline.add(new Vec3d(
                        compound.getDouble("X", 0),
                        compound.getDouble("Y", 0),
                        compound.getDouble("Z", 0)
                ));
            }

        }
        this.isRewinding = nbt.getBoolean(IS_REWINDING, false);
    }

    @Override
    public void writeToNbt(@NotNull NbtCompound nbt, RegistryWrapper.WrapperLookup wrapperLookup) {
        val timelineTag = new NbtList();
        for (Vec3d pos : this.timeline) {
            NbtCompound compound = new NbtCompound();
            compound.putDouble("X", pos.getX());
            compound.putDouble("Y", pos.getY());
            compound.putDouble("Z", pos.getZ());

            timelineTag.add(compound);
        }
        nbt.put(TIMELINE, timelineTag);
        nbt.putBoolean(IS_REWINDING, isRewinding);
    }

    public boolean isRewinding() {
        return this.isRewinding;
    }

    public void setRewinding(boolean rewind) {
        this.isRewinding = rewind;
        this.sync();

        this.lastPos = this.player.getPos();
    }

    public void addToTimeline(Vec3d pos) {
        timeline.add(pos);
    }

    public EvictingList<Vec3d> getTimeline() {
        return this.timeline;
    }
}
