package io.github.notwhiterice.endlessskies.registries;

import io.github.notwhiterice.endlessskies.core.exception.DualRegistryException;
import io.github.notwhiterice.endlessskies.registries.object.ModContext;

public abstract class ModRegistry {
    public static ModContext registerMod(String modID) throws DualRegistryException {
        return new ModContext(modID);
    }

    public static boolean isModKnown(String modID) { return ModContext.instances.containsKey(modID); }
    public static boolean isModLocked(String modID) {
        if(!isModKnown(modID)) return true;
        return ModContext.instances.get(modID).isLocked();
    }
}
