package io.github.notwhiterice.endlessskies.registry.init;

import io.github.notwhiterice.endlessskies.item.factory.CraftingItem;
import io.github.notwhiterice.endlessskies.registry.ModContext;
import io.github.notwhiterice.endlessskies.registry.item.ItemContext;
import io.github.notwhiterice.endlessskies.registry.item.ItemFactory;
import io.github.notwhiterice.endlessskies.registry.item.data.ItemModelFactory;

public class ItemInit {
    //Materials
    public static ItemContext itemCopperPlate;
    public static ItemContext itemGoldPlate;
    public static ItemContext itemIronPlate;
    public static ItemContext itemNetheritePlate;
    public static ItemContext itemStoneTablet;

    //Tools and Utilities
    public static ItemContext itemHammer;
    public static ItemContext itemMortarPestle;
    //public static ItemContext itemWrench;

    public static void registerItems(ModContext context) {
        ItemFactory factory = ItemFactory.init(context);
        itemCopperPlate = factory.item("copper_plate").setModelFactory(ItemModelFactory.SIMPLE_ITEM).setName("Copper Plate").close();
        itemGoldPlate = factory.item("gold_plate").setModelFactory(ItemModelFactory.SIMPLE_ITEM).setName("Gold Plate").close();
        itemIronPlate = factory.item("iron_plate").setModelFactory(ItemModelFactory.SIMPLE_ITEM).setName("Iron Plate").close();
        itemNetheritePlate = factory.item("netherite_plate").setModelFactory(ItemModelFactory.SIMPLE_ITEM).setName("Netherite Plate").close();
        itemStoneTablet = factory.item("stone_tablet").setModelFactory(ItemModelFactory.SIMPLE_ITEM).setName("Stone Tablet").close();
        itemHammer = factory.item("hammer").setParent(CraftingItem::new).setStackSize(1).setModelFactory(ItemModelFactory.HANDHELD_ITEM).setName("Hammer").close();
        itemMortarPestle = factory.item("mortar_and_pestle").setParent(CraftingItem::new).setStackSize(1).setModelFactory(ItemModelFactory.SIMPLE_ITEM).setName("Mortar and Pestle").close();
        //itemWrench = factory.item("wrench").setStackSize(1).setName("Wrench").setModelFactory(ItemModelFactory.HANDHELD_ITEM).setCreativeTab(CreativeModeTabs.TOOLS_AND_UTILITIES).close();
    }
}
