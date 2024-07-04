package io.github.deprecated.v3.endlessskies.event;

import io.github.deprecated.v2.endlessskies.EndlessSkies;
import io.github.deprecated.v3.endlessskies.registry.object.CreativeModeTabContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = EndlessSkies.modID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEvents {
    @SubscribeEvent
    public void onBuildCreativeModeTabContents(BuildCreativeModeTabContentsEvent event) {
        ResourceKey<CreativeModeTab> tab = event.getTabKey();
        if(CreativeModeTabContext.vanillaEntries.containsKey(tab))
            for(CreativeModeTabContext.TabEntryContext<?> entry : CreativeModeTabContext.vanillaEntries.get(tab))
                event.accept(entry.getEntry());
    }
}
