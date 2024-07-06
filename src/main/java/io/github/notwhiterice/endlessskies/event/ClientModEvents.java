package io.github.notwhiterice.endlessskies.event;

import io.github.notwhiterice.endlessskies.Reference;
import io.github.notwhiterice.endlessskies.inventory.factory.MenuContext;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = Reference.modID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModEvents {
    @SubscribeEvent
    public static <M extends AbstractContainerMenu> void onClientSetup(FMLClientSetupEvent event) throws NoSuchMethodException {
        for(MenuContext context : MenuContext.getAllMenus()) {
            MenuScreens.register(context.getMenuType(), context.screen);
        }
    }
}
