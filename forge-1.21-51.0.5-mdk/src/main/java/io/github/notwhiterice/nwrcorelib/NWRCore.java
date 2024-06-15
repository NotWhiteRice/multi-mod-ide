package io.github.notwhiterice.nwrcorelib;

import com.mojang.logging.LogUtils;
import io.github.notwhiterice.nwrcorelib.registry.ModRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(NWRCore.modID)
public class NWRCore {
    public static final String modID = "nwrcorelib";
    public static final Logger logger = LogUtils.getLogger();

    public NWRCore() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        bus.addListener(this::onCommonSetup);

        //ModRegistry.registerEventBus(bus);

        MinecraftForge.EVENT_BUS.register(this);
        bus.addListener(this::onBuildCreativeTabContents);
    }

    private void onCommonSetup(final FMLCommonSetupEvent event) {

    }

    private void onBuildCreativeTabContents(BuildCreativeModeTabContentsEvent event) {

    }
}
