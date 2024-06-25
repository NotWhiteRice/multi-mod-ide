package io.github.notwhiterice.endlessskies;

import com.mojang.logging.LogUtils;
import io.github.notwhiterice.endlessskies.blocks.BlockInit;
import io.github.notwhiterice.endlessskies.creativetabs.TabInit;
import io.github.notwhiterice.endlessskies.items.ItemInit;
import io.github.notwhiterice.endlessskies.registry.ModRegistry;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;

@Mod(EndlessSkies.modID)
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
