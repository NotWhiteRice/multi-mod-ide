package v2.circuitsmod.datagen;


import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = "endlessskies", bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput pack = generator.getPackOutput();
        ExistingFileHelper fileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        generator.addProvider(event.includeServer(), new io.github.deprecated.v2.circuitsmod.datagen.ModRecipeProvider(pack, lookupProvider));
        //generator.addProvider(event.includeServer(), ModLootTableProvider.create(pack, lookupProvider));

        //generator.addProvider(event.includeClient(), new ModBlockStateProvider(pack, fileHelper));
        //generator.addProvider(event.includeClient(), new ModItemModelProvider(pack, fileHelper));

        io.github.deprecated.v2.circuitsmod.datagen.ModBlockTagGenerator blockTagGenerator = generator.addProvider(event.includeServer(),
                new io.github.deprecated.v2.circuitsmod.datagen.ModBlockTagGenerator(pack, lookupProvider, fileHelper));
        generator.addProvider(event.includeServer(), new io.github.deprecated.v2.circuitsmod.datagen.ModItemTagGenerator(pack, lookupProvider, blockTagGenerator.contentsGetter(), fileHelper));

    }



}
