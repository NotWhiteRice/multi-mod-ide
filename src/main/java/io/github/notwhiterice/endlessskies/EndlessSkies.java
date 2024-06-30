package io.github.notwhiterice.endlessskies;

import net.minecraftforge.fml.common.Mod;

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
