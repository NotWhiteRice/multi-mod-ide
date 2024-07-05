package io.github.notwhiterice.endlessskies.init;

import io.github.notwhiterice.endlessskies.creativetab.factory.CreativeModeTabContext;
import io.github.notwhiterice.endlessskies.registry.object.ModContext;

public class CreativeModeTabInit {
    public static CreativeModeTabContext tabDev;

    public static void registerTabs(ModContext context) {
        tabDev = context.registerCreativeTab("dev_items")
                .setIcon(DevInit.itemTest)
                .addEntry(DevInit.itemTest)
                .addEntry(DevInit.blockTest);
    }
}
