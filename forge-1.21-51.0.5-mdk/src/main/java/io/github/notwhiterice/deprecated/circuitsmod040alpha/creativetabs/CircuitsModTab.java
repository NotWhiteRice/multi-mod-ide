package io.github.notwhiterice.deprecated.circuitsmod040alpha.creativetabs;

import io.github.notwhiterice.deprecated.circuitsmod040alpha.Circuits;
import io.github.notwhiterice.deprecated.endlessskies030alpha.core.creativetab.ICreativeTab;
import net.minecraftforge.registries.RegistryObject;

public class CircuitsModTab extends ICreativeTab {
    public CircuitsModTab() {
        super(Circuits.modID, io.github.notwhiterice.deprecated.circuitsmod040alpha.item.ItemInit.itemHandWrench, new RegistryObject[]{
                io.github.notwhiterice.deprecated.circuitsmod040alpha.item.ItemInit.itemHandWrench
                //ItemInit.itemBasicCircuitBoard,
                //BlockInit.blockRedstoneBridge,
                //BlockInit.blockNotGate,
                //BlockInit.blockAndGate,
                //BlockInit.blockOrGate,
                //BlockInit.blockXorGate,
                //BlockInit.blockNandGate,
                //BlockInit.blockNorGate,
                //BlockInit.blockXnorGate
        });
    }
}
