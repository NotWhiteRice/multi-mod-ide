package io.github.notwhiterice.bountifulharvest;

import io.github.notwhiterice.bountifulharvest.blocks.BlockInit;
import io.github.notwhiterice.bountifulharvest.items.ItemInit;
import io.github.notwhiterice.bountifulharvest.creativetabs.TabInit;
import io.github.notwhiterice.nwrcorelib.registry.ModRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(BountifulHarvest.modID)
public class BountifulHarvest {

    public static final String modID = "bountifulharvest";

    public BountifulHarvest() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        ModRegistry.registerMod(modID);

        BlockInit.initBlocks();
        ItemInit.initItems();
        TabInit.initTabs();

        ModRegistry.registerEventBus(bus, modID);

        MinecraftForge.EVENT_BUS.register(this);
    }
}
