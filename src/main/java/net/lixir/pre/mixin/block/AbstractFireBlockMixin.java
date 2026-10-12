package net.lixir.pre.mixin.block;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.block.AbstractFireBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(AbstractFireBlock.class)
public abstract class AbstractFireBlockMixin {

    @WrapOperation(
            method = "canPlaceAt",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/block/AbstractFireBlock;shouldLightPortalAt(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/Direction;)Z"
            )
    )
    private static boolean preventLightingPortal(World world, BlockPos pos, Direction direction, Operation<Boolean> original) {
        return false;
    }

    @WrapOperation(
            method = "onBlockAdded",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/block/AbstractFireBlock;isOverworldOrNether(Lnet/minecraft/world/World;)Z"
            )
    )
    private boolean invalidNetherPortalConditions(World world, Operation<Boolean> original) {
        return false;
    }
}
