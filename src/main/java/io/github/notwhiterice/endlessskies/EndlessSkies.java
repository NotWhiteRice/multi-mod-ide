package io.github.notwhiterice.endlessskies;

import io.github.notwhiterice.endlessskies.init.BlockInit;
//import io.github.notwhiterice.endlessskies.init.CreativeModeTabInit;
//import io.github.notwhiterice.endlessskies.init.ItemInit;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(EndlessSkies.modID)
public class EndlessSkies {
    public static final String modID = "endlessskies";

    public EndlessSkies() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        //CreativeModeTabInit.register(bus);

        //ItemInit.register(bus);
        BlockInit.register(bus);

        bus.addListener(this::onClientSetup);
        bus.addListener(this::onBuildCreativeModeTabContents);
    }

    private void onClientSetup(FMLClientSetupEvent event) {
        
    }

    private void onBuildCreativeModeTabContents(BuildCreativeModeTabContentsEvent event) {
        
    }
}
