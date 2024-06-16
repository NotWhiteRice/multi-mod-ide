package io.github.notwhiterice.nwrcorelib.registry;

import io.github.notwhiterice.nwrcorelib.NWRCore;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.rmi.registry.Registry;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class ModRegistry {
        private static Map<String, RegisterBundle> modRegisters = new HashMap<>();

        public static void registerMod(String modID) {
            NWRCore.logger.info("[DEBUG: NWRCore--at ModRegistry in registerMod(modID)]: func called for modID: " + modID);
            RegisterBundle rBundle = new RegisterBundle(modID);
            if(rBundle == null) NWRCore.logger.info("[ERROR: NWRCore--at ModRegistry in registerMod(modID)]: Unable to create RegisterBundle for modID: " + modID);
            else NWRCore.logger.info("[DEBUG: NWRCore--at ModRegistry in registerMod(modID)]: RegisterBundle created for modID: " + modID);
            modRegisters.put(modID, rBundle);
            NWRCore.logger.info("[DEBUG: NWRCore--at ModRegistry in registerMod(modID)]: Stored RegisterBundle for modID: " + modID);
            if(modRegisters.get(modID) == null) NWRCore.logger.info("[ERROR: NWRCore--at ModRegistry in registerMod(modID)]: Error retrieving RegisterBundle for modID: " + modID);
            else NWRCore.logger.info("[DEBUG: NWRCore--at ModRegistry in registerMod(modID)]: Successfully retrieved RegisterBundle for modID: " + modID);
        }

    public static void registerEventBus(IEventBus bus, String modID) {
        NWRCore.logger.info("[DEBUG: NWRCore--at ModRegistry in registerEventBus(bus, modID)]: func called for modID: " + modID);
        RegisterBundle rBundle = modRegisters.get(modID);
        if(rBundle == null) NWRCore.logger.info("[ERROR: NWRCore--at ModRegistry in registerEventBus(bus, modID)]: Error retrieving RegisterBundle for modID: " + modID);
        else NWRCore.logger.info("[DEBUG: NWRCore--at ModRegistry in registerEventBus(bus, modID)]: Successfully retrieved RegisterBundle for modID: " + modID);
        assert rBundle != null;
        rBundle.blocks.register(bus);
        NWRCore.logger.info("[DEBUG: NWRCore--at ModRegistry in registerEventBus(bus, modID)]: Registered event bus in the block register for modID: " + modID);
        rBundle.items.register(bus);
        NWRCore.logger.info("[DEBUG: NWRCore--at ModRegistry in registerEventBus(bus, modID)]: Registered event bus in the item register for modID: " + modID);
        rBundle.tabs.register(bus);
        NWRCore.logger.info("[DEBUG: NWRCore--at ModRegistry in registerEventBus(bus, modID)]: Registered event bus in the creative tab register for modID: " + modID);
    }

    public static RegistryObject<Item> registerItem(String modID, String itemID, Supplier<? extends Item> supplier) {
        NWRCore.logger.info("[DEBUG: NWRCore--at ModRegistry in registerItem(modID, itemID, supplier)]: func called for itemID: " + itemID + ", modID: " + modID);
        RegisterBundle rBundle = modRegisters.get(modID);
        if(rBundle == null) NWRCore.logger.info("[ERROR: NWRCore--at ModRegistry in registerItem(modID, itemID, supplier)]: Error retrieving RegisterBundle for modID: " + modID);
        else NWRCore.logger.info("[DEBUG: NWRCore--at ModRegistry in registerItem(modID, itemID, supplier)]: Successfully retrieved RegisterBundle for modID: " + modID);
        assert rBundle != null;
        DeferredRegister<Item> dRegister = rBundle.items;
        if(dRegister == null) NWRCore.logger.info("[ERROR: NWRCore--at ModRegistry in registerItem(modID, itemID, supplier)]: Error retrieving item register for modID: " + modID);
        else NWRCore.logger.info("[DEBUG: NWRCore--at ModRegistry in registerItem(modID, itemID, supplier)]: Successfully retrieved item register for modID: " + modID);
        assert dRegister != null;
        RegistryObject<Item> out = dRegister.register(itemID, supplier);
        if(out == null) NWRCore.logger.info("[ERROR: NWRCore--at ModRegistry in registerItem(modID, itemID, supplier)]: Error registering itemID: " + itemID + ", modID: " + modID);
        else NWRCore.logger.info("[DEBUG: NWRCore--at ModRegistry in registerItem(modID, itemID, supplier)]: Successfully registered itemID: " + itemID + ", modID: " + modID);
        return out;
    }

    public static RegistryObject<Block> registerBlock(String modID, String blockID, Supplier<? extends Block> supplier) {
        NWRCore.logger.info("[DEBUG: NWRCore--at ModRegistry in registerBlock(modID, blockID, supplier)]: func called for blockID: " + blockID + ", modID: " + modID);
        RegisterBundle rBundle = modRegisters.get(modID);
        if(rBundle == null) NWRCore.logger.info("[ERROR: NWRCore--at ModRegistry in registerBlock(modID, blockID, supplier)]: Error retrieving RegisterBundle for modID: " + modID);
        else NWRCore.logger.info("[DEBUG: NWRCore--at ModRegistry in registerBlock(modID, blockID, supplier)]: Successfully retrieved RegisterBundle for modID: " + modID);
        assert rBundle != null;
        DeferredRegister<Block> dRegister0 = rBundle.blocks;
        if(dRegister0 == null) NWRCore.logger.info("[ERROR: NWRCore--at ModRegistry in registerBlock(modID, blockID, supplier)]: Error retrieving block register for modID: " + modID);
        else NWRCore.logger.info("[DEBUG: NWRCore--at ModRegistry in registerBlock(modID, blockID, supplier)]: Successfully retrieved block register for modID: " + modID);
        assert dRegister0 != null;
        RegistryObject<Block> out = dRegister0.register(blockID, supplier);
        if(out == null) NWRCore.logger.info("[ERROR: NWRCore--at ModRegistry in registerBlock(modID, blockID, supplier)]: Error registering block for blockID: " + blockID + ", modID: " + modID);
        else NWRCore.logger.info("[DEBUG: NWRCore--at ModRegistry in registerBlock(modID, blockID, supplier)]: Successfully registered block for blockID: " + blockID + ", modID: " + modID);
        DeferredRegister<Item> dRegister1 = rBundle.items;
        if(dRegister1 == null) NWRCore.logger.info("[ERROR: NWRCore--at ModRegistry in registerBlock(modID, blockID, supplier)]: Error retrieving item register for modID: " + modID);
        else NWRCore.logger.info("[DEBUG: NWRCore--at ModRegistry in registerBlock(modID, blockID, supplier)]: Successfully retrieved item register for modID: " + modID);
        assert dRegister1 != null;
        RegistryObject<Item> rObject1 = dRegister1.register(blockID, () -> new BlockItem(out.get(), new Item.Properties()));
        if(rObject1 == null) NWRCore.logger.info("[ERROR: NWRCore--at ModRegistry in registerBlock(modID, blockID, supplier)]: Error registering item for blockID: " + blockID + ", modID: " + modID);
        else NWRCore.logger.info("[DEBUG: NWRCore--at ModRegistry in registerBlock(modID, blockID, supplier)]: Successfully registered item for blockID: " + blockID + ", modID: " + modID);
        return out;
    }

    public static RegistryObject<CreativeModeTab> registerTab(String modID, String tabID, Supplier<? extends CreativeModeTab> supplier) {
        NWRCore.logger.info("[DEBUG: NWRCore--at ModRegistry in registerTab(modID, tabID, supplier)]: func called for tabID: " + tabID + ", modID: " + modID);
        RegisterBundle rBundle = modRegisters.get(modID);
        if(rBundle == null) NWRCore.logger.info("[ERROR: NWRCore--at ModRegistry in registerTab(modID, tabID, supplier)]: Error retrieving RegisterBundle for modID: " + modID);
        else NWRCore.logger.info("[DEBUG: NWRCore--at ModRegistry in registerTab(modID, tabID, supplier)]: Successfully retrieved RegisterBundle for modID: " + modID);
        assert rBundle != null;
        DeferredRegister<CreativeModeTab> dRegister = rBundle.tabs;
        if(dRegister == null) NWRCore.logger.info("[ERROR: NWRCore--at ModRegistry in registerTab(modID, tabID, supplier)]: Error retrieving creative tab register for modID: " + modID);
        else NWRCore.logger.info("[DEBUG: NWRCore--at ModRegistry in registerTab(modID, tabID, supplier)]: Successfully retrieved creative tab register for modID: " + modID);
        assert dRegister != null;
        RegistryObject<CreativeModeTab> out = dRegister.register(tabID, supplier);
        if(out == null) NWRCore.logger.info("[ERROR: NWRCore--at ModRegistry in registerTab(modID, tabID, supplier)]: Error registering tabID: " + tabID + ", modID: " + modID);
        else NWRCore.logger.info("[DEBUG: NWRCore--at ModRegistry in registerTab(modID, tabID, supplier)]: Successfully registered tabID: " + tabID + ", modID: " + modID);
        return out;
    }
}
