package net.lixir.pre.common.item;

import lombok.val;
import net.lixir.pre.common.init.PREEntityComponents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class OldClockItem extends Item {
    public OldClockItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        val rewindData = PREEntityComponents.REWIND_DATA.get(user);
        if (!rewindData.isRewinding() && !rewindData.getTimeline().isEmpty()) {
            user.setVelocity(Vec3d.ZERO);
            rewindData.setRewinding(true);
        } else if (rewindData.isRewinding()) {
            rewindData.setRewinding(false);
        }
        return super.use(world, user, hand);
    }
}
