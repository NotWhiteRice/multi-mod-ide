package io.github.deprecated.v1.endlessskies;

import com.mojang.logging.LogUtils;
import io.github.deprecated.v1.endlessskies.blocks.BlockInit;
import io.github.deprecated.v1.endlessskies.creativetabs.TabInit;
import io.github.deprecated.v1.endlessskies.items.ItemInit;
import io.github.deprecated.v1.endlessskies.registry.ModRegistry;
import org.slf4j.Logger;

//@Mod(EndlessSkies.modID)
public class EndlessSkies {
    public static final String modID = "endlessskies";
    public static final Logger logger = LogUtils.getLogger();

    public EndlessSkies() {
        ModRegistry.registerMod(modID);

        BlockInit.initBlocks();
        ItemInit.initItems();
        TabInit.initTabs();

        ModRegistry.registerEventBus();
    }
}
