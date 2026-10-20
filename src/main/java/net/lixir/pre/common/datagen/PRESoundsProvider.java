package net.lixir.pre.common.datagen;

import net.fabricmc.fabric.api.client.datagen.v1.builder.SoundTypeBuilder;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricSoundsProvider;
import net.lixir.pre.common.ProgressionReEnvisioned;
import net.lixir.pre.common.init.PRESoundEvents;
import net.minecraft.data.DataOutput;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.sound.SoundCategory;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class PRESoundsProvider extends FabricSoundsProvider {
    public PRESoundsProvider(DataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup, @NotNull SoundExporter soundExporter) {
        soundExporter.add(PRESoundEvents.MISC_TYPE,
                SoundTypeBuilder.of()
                        .sound(SoundTypeBuilder.EntryBuilder.ofFile(ProgressionReEnvisioned.id("misc/type")))
                        .category(SoundCategory.NEUTRAL)
        );
        soundExporter.add(PRESoundEvents.ITEM_SPEAR_USE,
                SoundTypeBuilder.of()
                        .sound(SoundTypeBuilder.EntryBuilder.ofFile(ProgressionReEnvisioned.id("item/spear/use")))
                        .category(SoundCategory.PLAYERS)
                        .subtitle("subtitles.item.spear.use")
        );
        soundExporter.add(PRESoundEvents.ITEM_SPEAR_LUNGE,
                SoundTypeBuilder.of()
                        .sound(SoundTypeBuilder.EntryBuilder.ofFile(ProgressionReEnvisioned.id("item/spear/lunge")))
                        .category(SoundCategory.PLAYERS)
                        .subtitle("subtitles.item.spear.lunge")
        );
        soundExporter.add(PRESoundEvents.ITEM_SPEAR_JAB,
                SoundTypeBuilder.of()
                        .sound(SoundTypeBuilder.EntryBuilder.ofFile(ProgressionReEnvisioned.id("item/spear/jab")))
                        .category(SoundCategory.PLAYERS)
                        .subtitle("subtitles.item.spear.jab")
        );
        soundExporter.add(PRESoundEvents.ITEM_TRANSENDER_TELEPORT_START,
                SoundTypeBuilder.of()
                        .sound(SoundTypeBuilder.EntryBuilder.ofFile(ProgressionReEnvisioned.id("item/ender_eye/teleport_start")))
                        .category(SoundCategory.PLAYERS)
                        .subtitle("subtitles.item.transender.teleport_start")
        );
        soundExporter.add(PRESoundEvents.ITEM_ENDER_EYE_TELEPORT_START,
                SoundTypeBuilder.of()
                        .sound(SoundTypeBuilder.EntryBuilder.ofFile(ProgressionReEnvisioned.id("item/ender_eye/teleport_start")))
                        .category(SoundCategory.PLAYERS)
                        .subtitle("subtitles.item.ender_eye.teleport_start")
        );
        soundExporter.add(PRESoundEvents.ITEM_RECOVERY_COMPASS_RESONANCE,
                SoundTypeBuilder.of()
                        .sound(SoundTypeBuilder.EntryBuilder.ofFile(ProgressionReEnvisioned.id("item/recovery_compass/resonance")))
                        .category(SoundCategory.PLAYERS)
                        .subtitle("subtitles.item.recovery_compass.resonance")
        );
        soundExporter.add(PRESoundEvents.ITEM_TRANSENDER_AMBIENT,
                SoundTypeBuilder.of()
                        .sound(SoundTypeBuilder.EntryBuilder.ofFile(ProgressionReEnvisioned.id("item/ender_eye/teleport_ambient")))
                        .category(SoundCategory.PLAYERS)
                        .subtitle("subtitles.item.transender.teleport_ambient")
        );
        soundExporter.add(PRESoundEvents.ITEM_ENDER_EYE_TELEPORT_AMBIENT,
                SoundTypeBuilder.of()
                        .sound(SoundTypeBuilder.EntryBuilder.ofFile(ProgressionReEnvisioned.id("item/ender_eye/teleport_ambient")))
                        .category(SoundCategory.PLAYERS)
                        .subtitle("subtitles.item.ender_eye.teleport_ambient")
        );
        soundExporter.add(PRESoundEvents.ITEM_TRANSENDER_CHARGED,
                SoundTypeBuilder.of()
                        .sound(SoundTypeBuilder.EntryBuilder.ofFile(ProgressionReEnvisioned.id("item/transender/charged")))
                        .category(SoundCategory.PLAYERS)
                        .subtitle("subtitles.item.transender.charged")
        );
        soundExporter.add(PRESoundEvents.ITEM_TRANSENDER_TELEPORT,
                SoundTypeBuilder.of()
                        .sound(SoundTypeBuilder.EntryBuilder.ofFile(ProgressionReEnvisioned.id("item/transender/teleport")))
                        .category(SoundCategory.PLAYERS)
                        .subtitle("subtitles.item.transender.teleport")
        );
        soundExporter.add(PRESoundEvents.ITEM_ENDER_EYE_TELEPORT,
                SoundTypeBuilder.of()
                        .sound(SoundTypeBuilder.EntryBuilder.ofFile(ProgressionReEnvisioned.id("item/ender_eye/teleport")))
                        .category(SoundCategory.PLAYERS)
                        .subtitle("subtitles.item.ender_eye.teleport")
        );
        soundExporter.add(PRESoundEvents.BLOCK_RECYCLER_INSERT,
                SoundTypeBuilder.of()
                        .sound(SoundTypeBuilder.EntryBuilder.ofFile(ProgressionReEnvisioned.id("block/recycler/insert")))
                        .category(SoundCategory.BLOCKS)
                        .subtitle("subtitles.block.recycler.insert")
        );
        soundExporter.add(PRESoundEvents.BLOCK_RECYCLER_FAIL,
                SoundTypeBuilder.of()
                        .sound(SoundTypeBuilder.EntryBuilder.ofFile(ProgressionReEnvisioned.id("block/recycler/fail")))
                        .category(SoundCategory.BLOCKS)
                        .subtitle("subtitles.block.recycler.fail")
        );
        soundExporter.add(PRESoundEvents.BLOCK_RECYCLER_EMPTY,
                SoundTypeBuilder.of()
                        .sound(SoundTypeBuilder.EntryBuilder.ofFile(ProgressionReEnvisioned.id("block/recycler/empty")))
                        .category(SoundCategory.BLOCKS)
                        .subtitle("subtitles.block.recycler.empty")
        );
    }

    @Override
    public String getName() {
        return "PRESoundsProvider";
    }
}
