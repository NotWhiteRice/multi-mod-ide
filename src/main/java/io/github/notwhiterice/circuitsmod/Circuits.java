package io.github.notwhiterice.circuitsmod;

import io.github.notwhiterice.circuitsmod.init.BlockInit;
import io.github.notwhiterice.circuitsmod.init.CreativeModeTabInit;
import io.github.notwhiterice.circuitsmod.init.ItemInit;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Circuits.modID)
public class Circuits {
    public static final String modID = "circuitsmod";

    public Circuits() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        CreativeModeTabInit.register(bus);

        ItemInit.register(bus);
        BlockInit.register(bus);

        bus.addListener(this::onClientSetup);
        bus.addListener(this::onBuildCreativeModeTabContents);
    }

    private void onClientSetup(FMLClientSetupEvent event) {

    }

    private void onBuildCreativeModeTabContents(BuildCreativeModeTabContentsEvent event) {

    }
}
