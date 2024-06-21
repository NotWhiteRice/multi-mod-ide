package io.github.notwhiterice.circuitsmod.creativetabs;

import io.github.notwhiterice.circuitsmod.Circuits;
import io.github.notwhiterice.nwrcorelib.registry.ModRegistry;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.registries.RegistryObject;

public class TabInit {
    public static RegistryObject<CreativeModeTab> tabCircuitsMod;

    public static void initTabs() {
        tabCircuitsMod = ModRegistry.registerTab(Circuits.modID, Circuits.modID, TabCircuitsMod::new);
    }
}
