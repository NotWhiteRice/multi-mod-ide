package io.github.deprecated.v3.endlessskies.init;

import io.github.deprecated.v3.endlessskies.core.exception.DualRegistryException;
import io.github.deprecated.v3.endlessskies.registry.object.CreativeModeTabContext;
import io.github.deprecated.v3.endlessskies.registry.object.ModContext;

public class CreativeModeTabInit {
    public static CreativeModeTabContext tabDev;

    public static void registerTabs(ModContext context) throws DualRegistryException, IllegalAccessException {
        tabDev = context.generateCreativeModeTab("dev_items")
                .setIcon(HiddenInit.itemTest)
                .addEntry(HiddenInit.itemTest)
                .addEntry(HiddenInit.blockTest);
    }
}
