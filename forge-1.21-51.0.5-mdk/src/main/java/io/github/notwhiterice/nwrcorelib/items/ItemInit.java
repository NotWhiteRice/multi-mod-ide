package io.github.notwhiterice.nwrcorelib.items;

import io.github.notwhiterice.nwrcorelib.NWRCore;
import io.github.notwhiterice.nwrcorelib.registry.ModRegistry;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;

public class ItemInit {
    public static RegistryObject<Item> itemTest;

    public static void initItems() {
        itemTest = ModRegistry.registerItem(NWRCore.modID, "test_item", () -> new Item(new Item.Properties()));
    }
}
