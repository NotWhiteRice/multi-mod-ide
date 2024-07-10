package io.github.notwhiterice.endlessskies.registry.init;

import io.github.notwhiterice.endlessskies.creativetab.factory.CreativeModeTabContext;
import io.github.notwhiterice.endlessskies.registry.ModContext;

public class CreativeModeTabInit {
    public static CreativeModeTabContext tabDev;

    public static void registerTabs(ModContext context) {
        tabDev = context.registerCreativeTab("dev_items")
                .setIcon(DevInit.itemTest)
                .setName("E. Skies--Dev items")
                .addEntry(DevInit.itemTest)
                .addEntry(DevInit.blockTest);
    }
}
