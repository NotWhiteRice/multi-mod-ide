package io.github.deprecated.v2.endlessskies.event;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = io.github.deprecated.v1.endlessskies.EndlessSkies.modID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenHandler {
    @SubscribeEvent
    public static void onGatherData(GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();
        PackOutput pack = gen.getPackOutput();
        ExistingFileHelper fileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        gen.addProvider(event.includeServer(), new ModRecipeProvider(pack, lookupProvider));
        gen.addProvider(event.includeServer(), ModBlockLootProvider.create(pack, lookupProvider));

        //gen.addProvider(event.includeClient(), new ModBlockStateProvider(pack, fileHelper));
        //gen.addProvider(event.includeClient(), new ModItemModelProvider(pack, fileHelper));

        io.github.deprecated.v2.endlessskies.datagen.ModBlockTagsProvider blockTagGenerator = gen.addProvider(event.includeServer(),
                new io.github.deprecated.v2.endlessskies.datagen.ModBlockTagsProvider(pack, lookupProvider, fileHelper));
        gen.addProvider(event.includeServer(), new io.github.deprecated.v2.endlessskies.datagen.ModItemTagsProvider(pack, lookupProvider, blockTagGenerator.contentsGetter(), fileHelper));

    }
}
