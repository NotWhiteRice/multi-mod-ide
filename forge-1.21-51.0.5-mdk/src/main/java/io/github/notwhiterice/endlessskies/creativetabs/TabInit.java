package io.github.notwhiterice.endlessskies.creativetabs;

import io.github.notwhiterice.endlessskies.EndlessSkies;
import io.github.notwhiterice.endlessskies.registry.ModRegistry;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.registries.RegistryObject;

public class TabInit {
    public static RegistryObject<CreativeModeTab> tabNWRCore;

    public static void initTabs() {
        tabNWRCore = ModRegistry.registerTab(EndlessSkies.modID, EndlessSkies.modID, EndlessSkiesModTab::new);
    }
}
