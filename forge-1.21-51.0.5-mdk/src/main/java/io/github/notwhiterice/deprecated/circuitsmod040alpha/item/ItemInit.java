package io.github.notwhiterice.deprecated.circuitsmod040alpha.item;

import io.github.notwhiterice.deprecated.circuitsmod040alpha.Circuits;
import io.github.notwhiterice.deprecated.endlessskies030alpha.registry.ModRegistry;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;

public class ItemInit {
    //Materials
    //public static RegistryObject<Item> itemBasicCircuitBoard;

    //Tools and utility items
    public static RegistryObject<Item> itemHandWrench;

    public static void initItems() {
        //itemBasicCircuitBoard = ModRegistry.registerItem(Circuits.modID, "basic_circuit_board", BasicItem::new);

        itemHandWrench = ModRegistry.registerItem(Circuits.modID, "hand_wrench", HandWrenchItem::new);
    }
}
