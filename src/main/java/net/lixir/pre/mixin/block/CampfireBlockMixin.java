package net.lixir.pre.mixin.block;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.block.BlockState;
import net.minecraft.block.CampfireBlock;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.property.Property;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(CampfireBlock.class)
public class CampfireBlockMixin {

    @WrapOperation(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockState;with(Lnet/minecraft/state/property/Property;Ljava/lang/Comparable;)Ljava/lang/Object;"))
    private Object pre$noLitPlacement(BlockState instance, Property<?> property, Comparable<?> comparable, Operation<Object> original) {
        if (property == CampfireBlock.LIT) {
            return original.call(instance, CampfireBlock.LIT, false);
        }
        return original.call(instance, property, comparable);
    }

    @WrapOperation(method = "getPlacementState", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockState;with(Lnet/minecraft/state/property/Property;Ljava/lang/Comparable;)Ljava/lang/Object;"))
    private Object pre$noLitPlacement(BlockState instance, Property<?> property, Comparable<?> comparable, Operation<Object> original, ItemPlacementContext ctx) {
        if (property == CampfireBlock.LIT) {
            return original.call(instance, CampfireBlock.LIT, false);
        }
        return original.call(instance, property, comparable);
    }
}
