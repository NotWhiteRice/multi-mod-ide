package io.github.notwhiterice.endlessskies.init;

import io.github.notwhiterice.endlessskies.EndlessSkies;
import io.github.notwhiterice.endlessskies.core.exception.DualRegistryException;
import io.github.notwhiterice.endlessskies.registries.object.CreativeModeTabContext;

public class CreativeModeTabInit {
    public static CreativeModeTabContext tabDev;

    public static void registerTabs() throws DualRegistryException {
        tabDev = EndlessSkies.context.generateCreativeModeTab("dev_items")
                .setIcon(HiddenInit.itemTest)
                .displayAdd(HiddenInit.itemTest)
                .displayAdd(HiddenInit.blockTest);
    }
}
