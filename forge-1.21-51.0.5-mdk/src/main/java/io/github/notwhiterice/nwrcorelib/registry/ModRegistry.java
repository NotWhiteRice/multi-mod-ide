package io.github.notwhiterice.nwrcorelib.registry;

import io.github.notwhiterice.nwrcorelib.NWRCore;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Supplier;

public class ModRegistry {
    private static Map<String, RegisterBundle> modRegisters = new TreeMap<>();

    public static void registerMod(String modID) {
        NWRCore.logger.info("[NWRCore--at ModRegistry]: registerMod() called for modID: " + modID);
        modRegisters.put(modID, new RegisterBundle(modID));
    }

    public static void registerEventBus(IEventBus bus, String modID) {
        RegisterBundle rBundle = modRegisters.get(modID);
        rBundle.blocks.register(bus);
        rBundle.items.register(bus);
        rBundle.tabs.register(bus);
    }

    public static RegistryObject<Item> registerItem(String modID, String name, Supplier<? extends Item> supplier) {
        return modRegisters.get(modID).items.register(name, supplier);
    }

    public static RegistryObject<Block> registerBlock(String modID, String name, Supplier<? extends Block> supplier) {
        RegistryObject<Block> out = modRegisters.get(modID).blocks.register(name, supplier);
        modRegisters.get(modID).items.register(name, () -> new BlockItem(out.get(), new Item.Properties()));
        return out;
    }

    public static RegistryObject<CreativeModeTab> registerTab(String modID, String name, Supplier<? extends CreativeModeTab> supplier) {
        return modRegisters.get(modID).tabs.register(name, supplier);
    }
}
