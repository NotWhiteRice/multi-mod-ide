package io.github.notwhiterice.deprecated.circuitsmod040alpha;

import io.github.notwhiterice.deprecated.circuitsmod040alpha.block.BlockInit;
import io.github.notwhiterice.deprecated.circuitsmod040alpha.creativetabs.TabInit;
import io.github.notwhiterice.deprecated.circuitsmod040alpha.item.ItemInit;
import io.github.notwhiterice.deprecated.endlessskies030alpha.registry.ModRegistry;

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
