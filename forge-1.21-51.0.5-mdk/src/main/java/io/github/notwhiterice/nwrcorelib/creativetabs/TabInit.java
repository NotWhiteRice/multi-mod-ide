package io.github.notwhiterice.nwrcorelib.creativetabs;

import io.github.notwhiterice.nwrcorelib.NWRCore;
import io.github.notwhiterice.nwrcorelib.registry.ModRegistry;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.registries.RegistryObject;

public class TabInit {
    public static RegistryObject<CreativeModeTab> tabNWRCore;

    public static void initTabs() {
        tabNWRCore = ModRegistry.registerTab(NWRCore.modID, NWRCore.modID, TabNWRCore::new);
    }
}
