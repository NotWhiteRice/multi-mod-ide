package io.github.notwhiterice.endlessskies.init;

import io.github.notwhiterice.endlessskies.item.factory.ItemContext;
import io.github.notwhiterice.endlessskies.item.factory.ItemFactory;
import io.github.notwhiterice.endlessskies.registry.object.ModContext;

public class ItemInit {
    public static ItemContext itemWrench;

    public static void registerItems(ModContext context) {
        ItemFactory factory = ItemFactory.init(context);
        itemWrench = factory.item("wrench")
                .setStackSize(1)
                .setName("Wrench")
                .close();
    }
}
