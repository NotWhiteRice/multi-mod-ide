package io.github.deprecated.v3.endlessskies.event;

import io.github.deprecated.v3.endlessskies.datagen.ModBlockStateProvider;
import io.github.deprecated.v3.endlessskies.datagen.ModItemModelProvider;
import io.github.deprecated.v3.endlessskies.registry.ModRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

public class DataGenEvents {


    public static void onGatherData(final GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();
        PackOutput pack = gen.getPackOutput();
        ExistingFileHelper fileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        for(String modID : ModRegistry.listKnownMods()) {
            gen.addProvider(event.includeClient(), new ModBlockStateProvider(pack, modID, fileHelper));
            gen.addProvider(event.includeClient(), new ModItemModelProvider(pack, modID, fileHelper));
        }
    }

}
