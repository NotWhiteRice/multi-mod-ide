package io.github.notwhiterice.nwrcorelib.creativetabs;

import io.github.notwhiterice.nwrcorelib.NWRCore;
import io.github.notwhiterice.nwrcorelib.blocks.BlockInit;
import io.github.notwhiterice.nwrcorelib.items.ItemInit;

import java.util.List;

public class NWRCoreModTab extends BasicTab {
    public NWRCoreModTab() {
        super(NWRCore.modID, BlockInit.blockTest, List.of(
                ItemInit.itemTest,
                BlockInit.blockStonePanel,
                BlockInit.blockArrowPanel,
                BlockInit.blockTest
        ));
    }
}
