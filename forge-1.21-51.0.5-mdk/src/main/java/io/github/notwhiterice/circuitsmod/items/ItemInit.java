package io.github.notwhiterice.circuitsmod.items;

import io.github.notwhiterice.circuitsmod.Circuits;
import io.github.notwhiterice.nwrcorelib.registry.ModRegistry;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ItemInit {
    //Dev items
    public static RegistryObject<Item> itemTest;

    public static void initItems() {
        itemTest = ModRegistry.registerItem(Circuits.modID, "test_item", () -> new Item(new Item.Properties()));
    }
}
