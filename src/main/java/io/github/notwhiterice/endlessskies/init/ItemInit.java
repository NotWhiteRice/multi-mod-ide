package io.github.notwhiterice.endlessskies.init;

import io.github.notwhiterice.endlessskies.registry.item.ItemContext;
import io.github.notwhiterice.endlessskies.registry.item.ItemFactory;
import io.github.notwhiterice.endlessskies.registry.ModContext;
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
