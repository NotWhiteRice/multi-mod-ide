package io.github.notwhiterice.endlessskies;

import com.mojang.logging.LogUtils;
import io.github.notwhiterice.endlessskies.init.*;
import io.github.notwhiterice.endlessskies.screen.MineraInfuserScreen;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(EndlessSkies.modID)
public class EndlessSkies {
    public static final String modID = "endlessskies";
    public static final Logger logger = LogUtils.getLogger();

    public EndlessSkies() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        CreativeModeTabInit.register(bus);

        ItemInit.register(bus);
        BlockInit.register(bus);

        BlockEntityInit.register(bus);
        MenuTypeInit.register(bus);

        bus.addListener(this::onBuildCreativeModeTabContents);
    }

    @Mod.EventBusSubscriber(modid = modID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            MenuScreens.register(MenuTypeInit.menuMineralInfuser.get(), MineraInfuserScreen::new);
            //ItemBlockRenderTypes.setRenderLayer(BlockInit.blockClearGlass.get(), RenderType.cutout());
        }
    }



    private void onBuildCreativeModeTabContents(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            event.accept(ItemInit.itemHandWrench.get());
        } else if (event.getTabKey() == CreativeModeTabs.FUNCTIONAL_BLOCKS) {
            event.accept(BlockInit.blockMineralInfuser.get());
        }
    }
}
