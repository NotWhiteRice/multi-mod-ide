package io.github.notwhiterice.nwrcorelib.creativetabs;

import io.github.notwhiterice.nwrcorelib.NWRCore;
import io.github.notwhiterice.nwrcorelib.blocks.BlockInit;
import io.github.notwhiterice.nwrcorelib.items.ItemInit;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

public class TabNWRCore extends TabBase {
    public TabNWRCore() {
        super(NWRCore.modID, ItemInit.itemTest, List.of(
                ItemInit.itemTest,
                BlockInit.blockTest
        ));
    }
}
