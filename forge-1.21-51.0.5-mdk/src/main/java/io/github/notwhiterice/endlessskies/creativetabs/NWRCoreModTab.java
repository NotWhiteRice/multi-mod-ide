package io.github.notwhiterice.endlessskies.creativetabs;

import io.github.notwhiterice.endlessskies.EndlessSkies;
import io.github.notwhiterice.endlessskies.blocks.BlockInit;
import io.github.notwhiterice.endlessskies.items.ItemInit;

import java.util.List;

public class NWRCoreModTab extends BasicTab {
    public NWRCoreModTab() {
        super(EndlessSkies.modID, BlockInit.blockTest, List.of(
                ItemInit.itemTest,
                BlockInit.blockPanelSample,
                BlockInit.blockAimedPanelSample,
                BlockInit.blockTest
        ));
    }
}
