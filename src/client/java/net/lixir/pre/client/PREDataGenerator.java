package net.lixir.pre.client;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.lixir.pre.client.datagen.*;
import org.jetbrains.annotations.NotNull;

public class PREDataGenerator implements DataGeneratorEntrypoint {

    @Override
    public void onInitializeDataGenerator(@NotNull FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
        pack.addProvider(PRERecipeProvider::new);
        pack.addProvider(PREVanillaRecipeProvider::new);
        pack.addProvider(PREBlockTagProvider::new);
        pack.addProvider(PREItemTagProvider::new);
        pack.addProvider(PRERecyclableItemProvider::new);
        pack.addProvider(PRESoundsProvider::new);
        pack.addProvider(PREBiomeTagProvider::new);
        pack.addProvider(PREFishingLootTableProvider::new);
        pack.addProvider(PRERecyclingLootTableProvider::new);
        pack.addProvider(PREEnglishLangProvider::new);
        pack.addProvider(PREEnchantmentTagProvider::new);
        pack.addProvider(PREDamageTypeTagProvider::new);
        pack.addProvider(PREEntityTypeTagProvider::new);
        pack.addProvider(PREModelProvider::new);
    }
}
