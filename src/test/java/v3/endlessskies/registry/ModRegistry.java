package io.github.deprecated.v3.endlessskies.registry;

import io.github.deprecated.v3.endlessskies.event.DataGenEvents;
import io.github.notwhiterice.endlessskies.core.exception.DualRegistryException;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.ArrayList;
import java.util.List;

public abstract class ModRegistry {
    public static ModContext registerMod(String modID) throws DualRegistryException, IllegalAccessException {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(DataGenEvents::onGatherData);
        return new ModContext(modID);
    }

    public static boolean isModKnown(String modID) { return ModContext.dummy.doesContextExist(modID); }

    public static List<String> listKnownMods() { return ModContext.dummy.listKnownMods(); }

    public static List<BlockContext> getModBlocks(String modID) { return BlockContext.dummy.getContextsForMod(modID); }
    public static List<ItemContext> getModItems(String modID) { return ItemContext.dummy.getContextsForMod(modID); }
    public static List<CreativeModeTabContext> getModTabs(String modID) { return CreativeModeTabContext.dummy.getContextsForMod(modID); }

    public static List<BlockContext> getAllBlocks() {
        List<BlockContext> out = new ArrayList<>();
        for(String modID : listKnownMods()) out.addAll(getModBlocks(modID));
        return out;
    }
    public static List<ItemContext> getAllItems() {
        List<ItemContext> out = new ArrayList<>();
        for(String modID : listKnownMods()) out.addAll(getModItems(modID));
        return out;
    }
    public static List<CreativeModeTabContext> getAllTabs() {
        List<CreativeModeTabContext> out = new ArrayList<>();
        for(String modID : listKnownMods()) out.addAll(getModTabs(modID));
        return out;
    }
}
