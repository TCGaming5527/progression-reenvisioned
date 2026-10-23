package net.lixir.pre.client.sound;

import net.minecraft.client.sound.MovingSoundInstance;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.random.Random;

public class ItemUseSoundInstance extends MovingSoundInstance {
    private final LivingEntity holder;
    private final Item item;

    public ItemUseSoundInstance(SoundEvent soundEvent, SoundCategory soundCategory, Random random, LivingEntity holder, Item item) {
        super(soundEvent, soundCategory, random);
        this.holder = holder;
        this.item = item;

        this.repeat = true;
    }

    @Override
    public void tick() {
        if (this.holder != null && this.holder.isAlive()
                && this.holder.isUsingItem() && this.holder.getActiveItem().isOf(this.item)) {
            this.x = this.holder.getX();
            this.y = this.holder.getEyeY();
            this.z = this.holder.getZ();
        } else {
            this.setDone();
        }
    }
}
