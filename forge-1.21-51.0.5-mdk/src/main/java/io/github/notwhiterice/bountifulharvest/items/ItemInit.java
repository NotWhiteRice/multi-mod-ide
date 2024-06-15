package io.github.notwhiterice.bountifulharvest.items;

import io.github.notwhiterice.bountifulharvest.BountifulHarvest;
import io.github.notwhiterice.nwrcorelib.registry.ModRegistry;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;

public class ItemInit {
    //Materials
    public static RegistryObject<Item> itemCinnamon;

    public static void initItems() {
        itemCinnamon = ModRegistry.registerItem(BountifulHarvest.modID, "cinnamon", () -> new Item(new Item.Properties()));
    }

}
