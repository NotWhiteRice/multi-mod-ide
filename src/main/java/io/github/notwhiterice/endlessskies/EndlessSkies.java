package io.github.notwhiterice.endlessskies;

import com.mojang.logging.LogUtils;
import io.github.notwhiterice.endlessskies.core.exception.DualRegistryException;
import io.github.notwhiterice.endlessskies.registries.object.CreativeModeTabContext;
import io.github.notwhiterice.endlessskies.init.*;
import io.github.notwhiterice.endlessskies.registries.ModRegistry;
import io.github.notwhiterice.endlessskies.registries.object.ModContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;

@Mod(EndlessSkies.modID)
public class EndlessSkies {
    public static final String modID = "endlessskies";
    public static final Logger logger = LogUtils.getLogger();

    public static ModContext context;

    public EndlessSkies() throws DualRegistryException {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        context = ModRegistry.registerMod(modID);

        ItemInit.registerItems();
        BlockInit.registerBlocks();

        context.generateModTab().setIcon(ItemInit.itemHandWrench);

        HiddenInit.registerDevObjects();

        CreativeModeTabInit.registerTabs();

        BlockEntityInit.register(bus);
        MenuTypeInit.register(bus);

        context.finalizeRegisters(bus);

        bus.addListener(this::onBuildCreativeModeTabContents);
    }

    private void onBuildCreativeModeTabContents(BuildCreativeModeTabContentsEvent event) {
        ResourceKey<CreativeModeTab> tabKey = event.getTabKey();
        if(CreativeModeTabContext.vanillaCTabMap.containsKey(tabKey))
            for(RegistryObject<? extends ItemLike> rObj : CreativeModeTabContext.vanillaCTabMap.get(tabKey))
                event.accept(rObj.get());
    }
}
