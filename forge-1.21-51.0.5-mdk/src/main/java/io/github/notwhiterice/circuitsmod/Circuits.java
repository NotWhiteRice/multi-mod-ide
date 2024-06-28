package io.github.notwhiterice.circuitsmod;

import io.github.notwhiterice.circuitsmod.blocks.BlockInit;
import io.github.notwhiterice.circuitsmod.creativetabs.TabInit;
import io.github.notwhiterice.circuitsmod.items.ItemInit;
import io.github.notwhiterice.endlessskies.registry.ModRegistry;
import net.minecraftforge.fml.common.Mod;

@Mod(Circuits.modID)
public class Circuits {
    public static final String modID = "circuitsmod";



    public Circuits() {
        ModRegistry.registerMod(modID);

        BlockInit.initBlocks();
        ItemInit.initItems();
        TabInit.initTabs();
    }
}
