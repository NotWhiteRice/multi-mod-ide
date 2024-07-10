package io.github.notwhiterice.endlessskies.event;

import io.github.notwhiterice.endlessskies.Reference;
import io.github.notwhiterice.endlessskies.registry.block.entity.TileEntityContext;
import io.github.notwhiterice.endlessskies.registry.inventory.MenuContext;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = Reference.modID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModEvents {
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) throws NoSuchMethodException {
        for(MenuContext context : MenuContext.instances)
            MenuScreens.register(context.get(), context.screen);
    }

    @SubscribeEvent
    public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
        for(TileEntityContext context : TileEntityContext.instances)
            if(context.renderer != null) event.registerBlockEntityRenderer(context.get(), context.renderer);
    }
}
