package net.lixir.pre.common.util;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.NotNull;

public record PositionAngle(Vec3d position, float yaw, float pitch) {
    public boolean arePositionsEqual(@NotNull PositionAngle positionAngle) {
        return positionAngle.position.equals(position);
    }

    public @NotNull NbtCompound toNbt() {
        NbtCompound nbt = new NbtCompound();
        nbt.putFloat("Yaw", yaw);
        nbt.putFloat("Pitch", pitch);

        NbtCompound posNbt = new NbtCompound();
        posNbt.putDouble("X", position.getX());
        posNbt.putDouble("Y", position.getY());
        posNbt.putDouble("Z", position.getZ());
        nbt.put("Position", posNbt);
        return nbt;
    }

    public static @NotNull PositionAngle fromNbt(@NotNull NbtCompound nbt) {
        float yaw = nbt.getFloat("Yaw", 0.0f);
        float pitch = nbt.getFloat("Pitch", 0.0f);

        NbtCompound posNbt = nbt.getCompoundOrEmpty("Position");
        double x = posNbt.getDouble("X", 0.0);
        double y = posNbt.getDouble("Y", 0.0);
        double z = posNbt.getDouble("Z", 0.0);
        Vec3d position = new Vec3d(x, y, z);
        return new PositionAngle(position, yaw, pitch);
    }
}
