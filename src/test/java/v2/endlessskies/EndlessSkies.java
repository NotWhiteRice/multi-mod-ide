package io.github.deprecated.v2.endlessskies;

import com.mojang.logging.LogUtils;
import io.github.deprecated.v3.endlessskies.core.exception.DualRegistryException;
import io.github.notwhiterice.endlessskies.init.*;
import io.github.notwhiterice.endlessskies.registries.object.ModContext;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(EndlessSkies.modID)
public class EndlessSkies {
    public static final String modID = "endlessskies";
    public static final Logger logger = LogUtils.getLogger();

    public static ModContext context;

    public EndlessSkies() throws DualRegistryException {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        //context = ModRegistry.registerMod(modID);

        //ItemInit.registerItems();
        //BlockInit.registerBlocks();

        //context.generateModTab().setIcon(ItemInit.itemHandWrench);

        //HiddenInit.registerDevObjects();

        //CreativeModeTabInit.registerTabs();

        BlockEntityInit.register(bus);
        MenuTypeInit.register(bus);

        //context.finalizeRegisters(bus);
    }
}