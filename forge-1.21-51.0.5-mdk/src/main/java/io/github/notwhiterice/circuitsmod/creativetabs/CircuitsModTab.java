package io.github.notwhiterice.circuitsmod.creativetabs;

import io.github.notwhiterice.circuitsmod.Circuits;
import io.github.notwhiterice.circuitsmod.blocks.BlockInit;
import io.github.notwhiterice.circuitsmod.items.ItemInit;
import io.github.notwhiterice.endlessskies.core.creativetab.ICreativeTab;
import net.minecraftforge.registries.RegistryObject;

public class CircuitsModTab extends ICreativeTab {
    public CircuitsModTab() {
        super(Circuits.modID, ItemInit.itemBasicCircuitBoard, new RegistryObject[]{
                ItemInit.itemBasicCircuitBoard,
                BlockInit.blockRedstoneBridge,
                BlockInit.blockNotGate,
                BlockInit.blockAndGate,
                BlockInit.blockOrGate,
                BlockInit.blockXorGate,
                BlockInit.blockNandGate,
                BlockInit.blockNorGate,
                BlockInit.blockXnorGate
        });
    }
}
