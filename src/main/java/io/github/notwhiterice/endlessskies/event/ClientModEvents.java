package io.github.notwhiterice.endlessskies.event;

import io.github.notwhiterice.endlessskies.EndlessSkies;
import io.github.notwhiterice.endlessskies.init.MenuTypeInit;
import io.github.notwhiterice.endlessskies.screen.MineraInfuserScreen;
import io.github.notwhiterice.endlessskies.screen.RockCrusherScreen;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = EndlessSkies.modID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModEvents {

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        MenuScreens.register(MenuTypeInit.menuMineralInfuser.get(), MineraInfuserScreen::new);
        MenuScreens.register(MenuTypeInit.menuRockCrusher.get(), RockCrusherScreen::new);
        //ItemBlockRenderTypes.setRenderLayer(BlockInit.blockClearGlass.get(), RenderType.cutout());
    }
}
