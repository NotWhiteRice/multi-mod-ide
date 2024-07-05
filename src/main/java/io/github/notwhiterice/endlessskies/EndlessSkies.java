package io.github.notwhiterice.endlessskies;

import io.github.notwhiterice.endlessskies.item.factory.ItemFactory;
import io.github.notwhiterice.endlessskies.registry.object.ModContext;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Reference.modID)
public class EndlessSkies {
    public EndlessSkies() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        ModContext context = new ModContext(Reference.modID);
        //ModContext err_1 = new ModContext(Reference.modID);

        context.registerItem("test", new ItemFactory());
        context.registerItem("test", new ItemFactory());

        context.finalizeRegisters(bus);
    }
}
