package io.github.deprecated.v1.circuitsmod.creativetabs;

import io.github.deprecated.v1.endlessskies.core.creativetab.ICreativeTab;
import io.github.deprecated.v1.circuitsmod.Circuits;
import io.github.deprecated.v1.circuitsmod.item.ItemInit;
import net.minecraftforge.registries.RegistryObject;

public class CircuitsModTab extends ICreativeTab {
    public CircuitsModTab() {
        super(Circuits.modID, ItemInit.itemHandWrench, new RegistryObject[]{
                ItemInit.itemHandWrench
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
