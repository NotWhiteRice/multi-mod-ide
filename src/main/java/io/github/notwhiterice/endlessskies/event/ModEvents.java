package io.github.notwhiterice.endlessskies.event;

import io.github.notwhiterice.endlessskies.Reference;
import io.github.notwhiterice.endlessskies.creativetab.factory.CreativeModeTabContext;
import io.github.notwhiterice.endlessskies.creativetab.factory.TabEntryFactory;
import io.github.notwhiterice.endlessskies.datagen.ModBlockStateProvider;
import io.github.notwhiterice.endlessskies.datagen.ModItemModelProvider;
import io.github.notwhiterice.endlessskies.datagen.ModLanguageProvider;
import io.github.notwhiterice.endlessskies.registry.object.ModContext;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = Reference.modID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEvents {
    @SubscribeEvent
    public static void onBuildCreativeModeTabContents(BuildCreativeModeTabContentsEvent event) {
        ResourceKey<CreativeModeTab> tab = event.getTabKey();
        if(CreativeModeTabContext.customVanillaEntries.containsKey(tab))
            for(TabEntryFactory<?> entry : CreativeModeTabContext.customVanillaEntries.get(tab))
                event.accept(entry.getEntry());
    }

    @SubscribeEvent
    public static void onGatherData(GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();
        PackOutput pack = gen.getPackOutput();
        ExistingFileHelper fileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        ModContext.getContext(Reference.modID).logger.debug("DataGenEvents", "onGatherData", "Beginning data generation");

        for(String locale : ModLanguageProvider.translations.keySet())
            for(String modID : ModLanguageProvider.translations.get(locale).keySet()) {
                gen.addProvider(event.includeClient(), new ModLanguageProvider(pack, modID, locale));
            }

        for(String modID : ModContext.getModList()) {
            ModContext.getContext(modID).logger.debug("DataGenEvents", "onGatherData", "Beginning mod-specific data generation for '" + modID + "'");

            gen.addProvider(event.includeClient(), new ModBlockStateProvider(pack, modID, fileHelper));
            gen.addProvider(event.includeClient(), new ModItemModelProvider(pack, modID, fileHelper));
        }
    }
}