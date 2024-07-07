package io.github.notwhiterice.endlessskies.init;

import io.github.notwhiterice.endlessskies.creativetab.factory.CreativeModeTabContext;
import io.github.notwhiterice.endlessskies.item.factory.ItemContext;
import io.github.notwhiterice.endlessskies.item.factory.ItemFactory;
import io.github.notwhiterice.endlessskies.registry.object.ModContext;
import net.minecraft.world.item.CreativeModeTabs;

public class ItemInit {
    //Materials

    //Tools and Utilities
    public static ItemContext itemWrench;

    public static void registerItems(ModContext context) {
        ItemFactory factory = ItemFactory.init(context);
        itemWrench = factory.item("wrench")
                .setStackSize(1)
                .setName("Wrench")
                .setCreativeTab(CreativeModeTabs.TOOLS_AND_UTILITIES)
                .close();
    }
}
