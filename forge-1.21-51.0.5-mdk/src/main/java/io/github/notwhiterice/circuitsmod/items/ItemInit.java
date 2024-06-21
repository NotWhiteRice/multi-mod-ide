package io.github.notwhiterice.circuitsmod.items;

import io.github.notwhiterice.circuitsmod.Circuits;
import io.github.notwhiterice.nwrcorelib.items.ItemBase;
import io.github.notwhiterice.nwrcorelib.registry.ModRegistry;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ItemInit {
    //Materials
    public static RegistryObject<Item> itemBasicCircuitBoard;

    public static void initItems() {
        itemBasicCircuitBoard = ModRegistry.registerItem(Circuits.modID, "basic_circuit_board", ItemBase::new);
    }
}
