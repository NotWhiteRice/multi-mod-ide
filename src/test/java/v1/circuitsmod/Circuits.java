package io.github.deprecated.v1.circuitsmod;

import io.github.deprecated.v1.circuitsmod.block.BlockInit;
import io.github.deprecated.v1.circuitsmod.creativetabs.TabInit;
import io.github.deprecated.v1.circuitsmod.item.ItemInit;
import io.github.deprecated.v1.endlessskies.registry.ModRegistry;

//@Mod(Circuits.modID)
public class Circuits {
    public static final String modID = "circuitsmod";
    public Circuits() {
        ModRegistry.registerMod(modID);

        BlockInit.initBlocks();
        ItemInit.initItems();
        TabInit.initTabs();

        ModRegistry.registerEventBus();
    }
}
