package io.github.notwhiterice.circuitsmod.creativetabs;

import io.github.notwhiterice.circuitsmod.Circuits;
import io.github.notwhiterice.circuitsmod.blocks.BlockInit;
import io.github.notwhiterice.circuitsmod.items.ItemInit;
import io.github.notwhiterice.nwrcorelib.creativetabs.TabBase;

import java.util.List;

public class TabCircuitsMod extends TabBase {
    public TabCircuitsMod() {
        super(Circuits.modID, ItemInit.itemBasicCircuitBoard, List.of(
                ItemInit.itemBasicCircuitBoard,
                BlockInit.blockDirectionalPanel
        ));
    }
}
