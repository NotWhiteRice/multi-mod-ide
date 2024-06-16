package io.github.notwhiterice.circuitsmod;

import com.mojang.logging.LogUtils;
import io.github.notwhiterice.circuitsmod.blocks.BlockInit;
import io.github.notwhiterice.circuitsmod.creativetabs.TabInit;
import io.github.notwhiterice.circuitsmod.items.ItemInit;
import io.github.notwhiterice.nwrcorelib.registry.ModRegistry;
import io.github.notwhiterice.nwrcorelib.registry.RegisterBundle;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(Circuits.modID)
public class Circuits {
    public static final String modID = "circuitsmod";
    public static final RegisterBundle rBundle = new RegisterBundle(modID);

    public Circuits() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        //ModRegistry.registerMod(modID);

        BlockInit.initBlocks();
        ItemInit.initItems();
        TabInit.initTabs();

        //ModRegistry.registerEventBus(bus, modID);
        rBundle.items.register(bus);
        rBundle.tabs.register(bus);
    }
}
