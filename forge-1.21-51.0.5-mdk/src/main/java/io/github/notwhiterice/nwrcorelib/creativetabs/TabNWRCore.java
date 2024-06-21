package io.github.notwhiterice.nwrcorelib.creativetabs;

import io.github.notwhiterice.nwrcorelib.NWRCore;
import io.github.notwhiterice.nwrcorelib.blocks.BlockInit;
import io.github.notwhiterice.nwrcorelib.items.ItemInit;

import java.util.List;

public class TabNWRCore extends TabBase {
    public TabNWRCore() {
        super(NWRCore.modID, ItemInit.itemTest, List.of(
                ItemInit.itemTest,
                BlockInit.blockTest
        ));
    }
}
