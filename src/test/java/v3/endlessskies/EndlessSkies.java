package io.github.deprecated.v3.endlessskies;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(EndlessSkies.modID)
public class EndlessSkies {
    public static final String modID = "endlessskies";

    public EndlessSkies() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        //ModContext context = ModRegistry.registerMod(modID);

        //BlockInit.registerBlocks(context);

        //context.generateModSpecificTab().setIcon(Items.DIAMOND);

        //HiddenInit.registerHidden(context);

        //CreativeModeTabInit.registerTabs(context);

        //context.finalizeRegisters(bus);
    }
}