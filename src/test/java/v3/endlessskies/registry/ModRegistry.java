package io.github.deprecated.v3.endlessskies.registry;

import io.github.notwhiterice.endlessskies.registry.object.ModContext;

import java.util.ArrayList;
import java.util.List;

public abstract class ModRegistry {
    public static List<io.github.deprecated.v3.endlessskies.registry.object.BlockContext> getModBlocks(String modID) { return io.github.deprecated.v3.endlessskies.registry.object.BlockContext.dummy.getContextsForMod(modID); }
    public static List<io.github.deprecated.v3.endlessskies.registry.object.CreativeModeTabContext> getModTabs(String modID) { return io.github.deprecated.v3.endlessskies.registry.object.CreativeModeTabContext.dummy.getContextsForMod(modID); }

    public static List<io.github.deprecated.v3.endlessskies.registry.object.BlockContext> getAllBlocks() {
        List<io.github.deprecated.v3.endlessskies.registry.object.BlockContext> out = new ArrayList<>();
        for(String modID : listKnownMods()) out.addAll(getModBlocks(modID));
        return out;
    }
    public static List<io.github.deprecated.v3.endlessskies.registry.object.CreativeModeTabContext> getAllTabs() {
        List<io.github.deprecated.v3.endlessskies.registry.object.CreativeModeTabContext> out = new ArrayList<>();
        for(String modID : listKnownMods()) out.addAll(getModTabs(modID));
        return out;
    }
}
