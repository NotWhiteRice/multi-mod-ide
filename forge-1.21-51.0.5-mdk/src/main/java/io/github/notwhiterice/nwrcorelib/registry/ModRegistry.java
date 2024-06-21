package io.github.notwhiterice.nwrcorelib.registry;

import io.github.notwhiterice.nwrcorelib.NWRCore;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
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

    public static void registerEventBus() {
        NWRCore.logger.info("[DEBUG: NWRCore--at ModRegistry in registerEventBus()]: func called");
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        for(Map.Entry<String, RegisterBundle> entry : modRegisters.entrySet()) {
            String modID = entry.getKey();
            RegisterBundle rBundle = entry.getValue();
            if(rBundle == null) NWRCore.logger.info("[ERROR: NWRCore--at ModRegistry in registerEventBus(bus)]: Error retrieving RegisterBundle for modID: " + modID);
            else NWRCore.logger.info("[DEBUG: NWRCore--at ModRegistry in registerEventBus(bus)]: Successfully retrieved RegisterBundle for modID: " + modID);
            assert rBundle != null;
            rBundle.blocks.register(bus);
            NWRCore.logger.info("[DEBUG: NWRCore--at ModRegistry in registerEventBus(bus)]: Registered event bus in the block register for modID: " + modID);
            rBundle.items.register(bus);
            NWRCore.logger.info("[DEBUG: NWRCore--at ModRegistry in registerEventBus(bus)]: Registered event bus in the item register for modID: " + modID);
            rBundle.tabs.register(bus);
            NWRCore.logger.info("[DEBUG: NWRCore--at ModRegistry in registerEventBus(bus)]: Registered event bus in the creative tab register for modID: " + modID);
        }
    }

    public static RegistryObject<Item> registerItem(String modID, String itemID, Supplier<? extends Item> supplier) {
        NWRCore.logger.info("[DEBUG: NWRCore--at ModRegistry in registerItem(modID, itemID, supplier)]: func called for itemID: " + itemID + ", modID: " + modID);
        RegisterBundle rBundle = modRegisters.get(modID);
        if(rBundle == null) NWRCore.logger.info("[ERROR: NWRCore--at ModRegistry in registerItem(modID, itemID, supplier)]: Error retrieving RegisterBundle for modID: " + modID);
        else NWRCore.logger.info("[DEBUG: NWRCore--at ModRegistry in registerItem(modID, itemID, supplier)]: Successfully retrieved RegisterBundle for modID: " + modID);
        assert rBundle != null;
        RegistryObject<Item> out = rBundle.items.register(itemID, supplier);
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
        RegistryObject<Block> out = rBundle.blocks.register(blockID, supplier);
        if(out == null) NWRCore.logger.info("[ERROR: NWRCore--at ModRegistry in registerBlock(modID, blockID, supplier)]: Error registering block for blockID: " + blockID + ", modID: " + modID);
        else NWRCore.logger.info("[DEBUG: NWRCore--at ModRegistry in registerBlock(modID, blockID, supplier)]: Successfully registered block for blockID: " + blockID + ", modID: " + modID);
        RegistryObject<Item> rObject1 = rBundle.items.register(blockID, () -> new BlockItem(out.get(), new Item.Properties()));
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
        RegistryObject<CreativeModeTab> out = rBundle.tabs.register(tabID, supplier);
        if(out == null) NWRCore.logger.info("[ERROR: NWRCore--at ModRegistry in registerTab(modID, tabID, supplier)]: Error registering tabID: " + tabID + ", modID: " + modID);
        else NWRCore.logger.info("[DEBUG: NWRCore--at ModRegistry in registerTab(modID, tabID, supplier)]: Successfully registered tabID: " + tabID + ", modID: " + modID);
        return out;
    }
}
