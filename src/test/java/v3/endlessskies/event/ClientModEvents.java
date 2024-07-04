package io.github.deprecated.v3.endlessskies.event;


import io.github.deprecated.v2.endlessskies.EndlessSkies;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = EndlessSkies.modID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModEvents {


    @SubscribeEvent
    public static <M extends AbstractContainerMenu, U extends AbstractContainerScreen<M>> void onClientSetup(FMLClientSetupEvent event) throws NoSuchMethodException {
        //for(MenuContext<M, U> context : ModRegistry.getAllMenus()) {
            //MenuScreens.register(context.asType(), (menu, inv, title) -> {
                //try {
                    //return (U)context.getScreenClass().getDeclaredConstructor(context.getMenuClass(), Inventory.class, Component.class).newInstance(menu, inv, title);
                //} catch(Exception e) {
                   // throw new RuntimeException(e);
                //}
            //});
        //}
        //MenuScreens.register(BlockInit.blockMineralInfuser.getMenu().asType(), MineralInfuserScreen::new);
    }
}
