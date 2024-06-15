package io.github.notwhiterice.circuitsmod.items;

import io.github.notwhiterice.circuitsmod.Circuits;
import io.github.notwhiterice.nwrcorelib.registry.ModRegistry;
import net.minecraft.world.item.Item;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ItemInit {
    //Materials
    public static RegistryObject<Item> itemBasicCircuitBoard;

    //Dev items
    public static RegistryObject<Item> itemTest;

    public static void initItems() {
        itemTest = ModRegistry.registerItem(Circuits.modID, "test_item", () -> new Item(new Item.Properties()));
        itemBasicCircuitBoard = ModRegistry.registerItem(Circuits.modID, "basic_circuit_board", () -> new Item(new Item.Properties()));
    }
}
