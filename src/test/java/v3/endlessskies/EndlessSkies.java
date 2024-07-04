package io.github.deprecated.v3.endlessskies;

import io.github.deprecated.v3.endlessskies.core.exception.DualRegistryException;
import io.github.deprecated.v3.endlessskies.init.CreativeModeTabInit;
import io.github.deprecated.v3.endlessskies.init.HiddenInit;
import io.github.deprecated.v3.endlessskies.registry.ModRegistry;
import io.github.deprecated.v3.endlessskies.registry.object.ModContext;
import io.github.deprecated.v3.endlessskies.init.BlockInit;
import net.minecraft.world.item.Items;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.lang.reflect.InvocationTargetException;

@Mod(EndlessSkies.modID)
public class EndlessSkies {
    public static final String modID = "endlessskies";

    public EndlessSkies() throws IllegalAccessException, DualRegistryException, NoSuchMethodException, InvocationTargetException {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        ModContext context = ModRegistry.registerMod(modID);

        BlockInit.registerBlocks(context);

        context.generateModSpecificTab().setIcon(Items.DIAMOND);

        HiddenInit.registerHidden(context);

        CreativeModeTabInit.registerTabs(context);

        context.finalizeRegisters(bus);
    }
}