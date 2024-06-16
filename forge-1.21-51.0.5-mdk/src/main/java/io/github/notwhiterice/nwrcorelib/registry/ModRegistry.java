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
import java.util.function.Supplier;

public class ModRegistry {
    private static Map<String, RegisterBundle> modRegisters = new HashMap<>();

    public static void registerMod(String modID) {
        NWRCore.logger.info("[INFO: NWRCore--at ModRegistry in registerMod(modID)]: func called for modID: " + modID);
        modRegisters.put(modID, new RegisterBundle(modID));
        if(modRegisters.get(modID) == null) NWRCore.logger.info("[ERROR: NWRCore--at ModRegistry in registerMod(modID)]: RegisterBundle is null for modID: " + modID);
    }

    public static void registerEventBus(IEventBus bus, String modID) {
        NWRCore.logger.info("[INFO: NWRCore--at ModRegistry in registerEventBus(bus, modID)]: func called for modID: " + modID);
        RegisterBundle rBundle = modRegisters.get(modID);
        rBundle.blocks.register(bus);
        rBundle.items.register(bus);
        rBundle.tabs.register(bus);
    }

    public static RegistryObject<Item> registerItem(String modID, String itemID, Supplier<? extends Item> supplier) {
        NWRCore.logger.info("[INFO: NWRCore--at ModRegistry in registerItem(modID, itemID, supplier)]: func called for modID: " + modID + ", itemID: " + itemID);
        return modRegisters.get(modID).items.register(itemID, supplier);
    }

    public static RegistryObject<Item> _registerItem(RegisterBundle rBundle, String itemID, Supplier<? extends Item> supplier) {
        NWRCore.logger.info("[INFO: NWRCore--at ModRegistry in _registerItem(rBundle, itemID, supplier)]: func called for itemID: " + itemID);
        return rBundle.items.register(itemID, supplier);
    }

    public static RegistryObject<Block> registerBlock(String modID, String blockID, Supplier<? extends Block> supplier) {
        NWRCore.logger.info("[INFO: NWRCore--at ModRegistry in registerBlock(modID, blockID, supplier)]: func called for modID: " + modID + ", blockID: " + blockID);
        RegistryObject<Block> out = modRegisters.get(modID).blocks.register(blockID, supplier);
        modRegisters.get(modID).items.register(blockID, () -> new BlockItem(out.get(), new Item.Properties()));
        return out;
    }

    public static RegistryObject<Block> _registerBlock(RegisterBundle rBundle, String blockID, Supplier<? extends Block> supplier) {
        NWRCore.logger.info("[INFO: NWRCore--at ModRegistry in _registerBlock(rBundle, blockID, supplier)]: func called for blockID: " + blockID);
        RegistryObject<Block> out = rBundle.blocks.register(blockID, supplier);
        rBundle.items.register(blockID, () -> new BlockItem(out.get(), new Item.Properties()));
        return out;
    }

    public static RegistryObject<CreativeModeTab> registerTab(String modID, String tabID, Supplier<? extends CreativeModeTab> supplier) {
        NWRCore.logger.info("[INFO: NWRCore--at ModRegistry in registerTab(modID, tabID, supplier)]: func called for modID: " + modID + ", tabID: " + tabID);
        return modRegisters.get(modID).tabs.register(tabID, supplier);
    }

    public static RegistryObject<CreativeModeTab> _registerTab(RegisterBundle rBundle, String tabID, Supplier<? extends CreativeModeTab> supplier) {
        NWRCore.logger.info("[INFO: NWRCore--at ModRegistry in _registerTab(rBundle, tabID, supplier)]: func called for tabID: " + tabID);
        return rBundle.tabs.register(tabID, supplier);
    }
}
