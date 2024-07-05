package io.github.deprecated.v2.endlessskies.event;

import io.github.deprecated.v2.endlessskies.EndlessSkies;
import io.github.deprecated.v2.endlessskies.init.MenuTypeInit;
import io.github.notwhiterice.endlessskies.inventory.screen.MineralInfuserScreen;
import io.github.notwhiterice.endlessskies.inventory.screen.RockCrusherScreen;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = EndlessSkies.modID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModEvents {

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        MenuScreens.register(MenuTypeInit.menuMineralInfuser.get(), MineralInfuserScreen::new);
        MenuScreens.register(MenuTypeInit.menuRockCrusher.get(), RockCrusherScreen::new);
        //ItemBlockRenderTypes.setRenderLayer(BlockInit.blockClearGlass.get(), RenderType.cutout());
    }
}
