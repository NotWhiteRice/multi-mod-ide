package io.github.notwhiterice.endlessskies.creativetabs;

import io.github.notwhiterice.endlessskies.EndlessSkies;
import io.github.notwhiterice.endlessskies.blocks.BlockInit;
import io.github.notwhiterice.endlessskies.core.creativetab.ICreativeTab;
import io.github.notwhiterice.endlessskies.items.ItemInit;
import net.minecraftforge.registries.RegistryObject;

public class EndlessSkiesModTab extends ICreativeTab {
    public EndlessSkiesModTab() {
        super(EndlessSkies.modID, BlockInit.blockTest, new RegistryObject[]{
                ItemInit.itemTest,
                BlockInit.blockPanel,
                BlockInit.blockDiPanel,
                BlockInit.blockTest
        });
    }
}
