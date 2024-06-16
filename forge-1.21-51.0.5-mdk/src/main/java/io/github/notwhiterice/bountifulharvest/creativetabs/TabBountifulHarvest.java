package io.github.notwhiterice.bountifulharvest.creativetabs;

import io.github.notwhiterice.bountifulharvest.BountifulHarvest;
import io.github.notwhiterice.nwrcorelib.creativetabs.TabBase;
import io.github.notwhiterice.nwrcorelib.items.ItemInit;

import java.util.List;

public class TabBountifulHarvest extends TabBase {
    protected TabBountifulHarvest() {
        super(BountifulHarvest.modID, ItemInit.itemTest, List.of());
    }
}
