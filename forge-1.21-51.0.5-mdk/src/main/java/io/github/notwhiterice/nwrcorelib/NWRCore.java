package io.github.notwhiterice.nwrcorelib;

import com.mojang.logging.LogUtils;
import io.github.notwhiterice.nwrcorelib.blocks.BlockInit;
import io.github.notwhiterice.nwrcorelib.creativetabs.TabInit;
import io.github.notwhiterice.nwrcorelib.items.ItemInit;
import io.github.notwhiterice.nwrcorelib.registry.ModRegistry;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(NWRCore.modID)
public class NWRCore {
    public static final String modID = "nwrcorelib";
    public static final Logger logger = LogUtils.getLogger();

    public NWRCore() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        ModRegistry.registerMod(modID);

        BlockInit.initBlocks();
        ItemInit.initItems();
        TabInit.initTabs();

        ModRegistry.registerEventBus(bus);
    }
}
