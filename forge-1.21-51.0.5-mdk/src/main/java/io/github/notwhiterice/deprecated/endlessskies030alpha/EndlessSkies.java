package io.github.notwhiterice.deprecated.endlessskies030alpha;

import com.mojang.logging.LogUtils;
import io.github.notwhiterice.deprecated.endlessskies030alpha.creativetabs.TabInit;
import io.github.notwhiterice.deprecated.endlessskies030alpha.items.ItemInit;
import io.github.notwhiterice.deprecated.endlessskies030alpha.registry.ModRegistry;
import io.github.notwhiterice.deprecated.endlessskies030alpha.blocks.BlockInit;
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
