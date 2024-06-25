package io.github.notwhiterice.circuitsmod.creativetabs;

import io.github.notwhiterice.circuitsmod.Circuits;
import io.github.notwhiterice.circuitsmod.items.ItemInit;
import io.github.notwhiterice.endlessskies.creativetabs.BasicTab;

import java.util.List;

public class CircuitsModTab extends BasicTab {
    public CircuitsModTab() {
        super(Circuits.modID, ItemInit.itemBasicCircuitBoard, List.of(
                ItemInit.itemBasicCircuitBoard
        ));
    }
}
