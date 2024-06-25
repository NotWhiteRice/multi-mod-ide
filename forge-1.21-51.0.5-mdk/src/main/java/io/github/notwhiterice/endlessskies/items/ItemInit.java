package io.github.notwhiterice.endlessskies.items;

import io.github.notwhiterice.endlessskies.EndlessSkies;
import io.github.notwhiterice.endlessskies.registry.ModRegistry;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;

public class ItemInit {
    public static RegistryObject<Item> itemTest;

    public static void initItems() {
        itemTest = ModRegistry.registerItem(EndlessSkies.modID, "test_item", TestItem::new);
    }
}
