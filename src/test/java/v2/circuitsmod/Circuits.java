package io.github.deprecated.v2.circuitsmod;

import io.github.deprecated.v2.circuitsmod.init.BlockInit;
import io.github.deprecated.v2.circuitsmod.init.ItemInit;
import io.github.deprecated.v2.circuitsmod.init.CreativeModeTabInit;
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
