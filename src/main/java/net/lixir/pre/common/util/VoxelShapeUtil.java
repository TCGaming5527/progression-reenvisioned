package net.lixir.pre.common.util;

import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;

public class VoxelShapeUtil {

    public static VoxelShape rotateShape(Direction facing, VoxelShape shape) {
        if (facing == Direction.NORTH) return shape; // default
        VoxelShape rotated = VoxelShapes.empty();

        for (Box part : shape.getBoundingBoxes()) {
            double minX = part.minX;
            double minY = part.minY;
            double minZ = part.minZ;
            double maxX = part.maxX;
            double maxY = part.maxY;
            double maxZ = part.maxZ;

            double newMinX = minX;
            double newMaxX = maxX;
            double newMinZ = minZ;
            double newMaxZ = maxZ;

            // Rotate around Y axis
            switch(facing) {
                case EAST -> {
                    newMinX = 1 - maxZ;
                    newMaxX = 1 - minZ;
                    newMinZ = minX;
                    newMaxZ = maxX;
                }
                case SOUTH -> {
                    newMinX = 1 - maxX;
                    newMaxX = 1 - minX;
                    newMinZ = 1 - maxZ;
                    newMaxZ = 1 - minZ;
                }
                case WEST -> {
                    newMinX = minZ;
                    newMaxX = maxZ;
                    newMinZ = 1 - maxX;
                    newMaxZ = 1 - minX;
                }
            }

            rotated = VoxelShapes.combine(rotated, VoxelShapes.cuboid(newMinX, minY, newMinZ, newMaxX, maxY, newMaxZ), BooleanBiFunction.OR);
        }

        return rotated;
    }
}
