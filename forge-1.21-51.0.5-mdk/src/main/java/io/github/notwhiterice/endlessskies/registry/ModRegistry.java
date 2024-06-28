package io.github.notwhiterice.endlessskies.registry;

import io.github.notwhiterice.endlessskies.EndlessSkies;
import io.github.notwhiterice.endlessskies.registry.assets.ModAssets;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class ModRegistry {
        public static Map<String, ModAssets> mAssetsTable = new HashMap<>();

        public static void registerMod(String modID) {
            EndlessSkies.logger.info("[DEBUG: Endless Skies--at ModRegistry in registerMod(modID)]: func called for modID: " + modID);
            ModAssets mAssets = new ModAssets(modID);
            if(mAssets == null) EndlessSkies.logger.info("[ERROR: Endless Skies--at ModRegistry in registerMod(modID)]: Unable to create mod assets for modID: " + modID);
            else EndlessSkies.logger.info("[DEBUG: Endless Skies--at ModRegistry in registerMod(modID)]: Mod assets created for modID: " + modID);
            mAssetsTable.put(modID, mAssets);
            EndlessSkies.logger.info("[DEBUG: Endless Skies--at ModRegistry in registerMod(modID)]: Stored mod assets for modID: " + modID);
            if(mAssetsTable.get(modID) == null) EndlessSkies.logger.info("[ERROR: EndlessSkies--at ModRegistry in registerMod(modID)]: Error retrieving mod assets for modID: " + modID);
            else EndlessSkies.logger.info("[DEBUG: Endless Skies--at ModRegistry in registerMod(modID)]: Successfully retrieved mod assets for modID: " + modID);
        }

    public static void registerEventBus() {
        EndlessSkies.logger.info("[DEBUG: Endless Skies--at ModRegistry in registerEventBus()]: func called");
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        for(Map.Entry<String, ModAssets> entry : mAssetsTable.entrySet()) {
            String modID = entry.getKey();
            ModAssets mAssets = entry.getValue();
            if(mAssets == null) EndlessSkies.logger.info("[ERROR: Endless Skies--at ModRegistry in registerEventBus(bus)]: Error retrieving mod assets for modID: " + modID);
            else EndlessSkies.logger.info("[DEBUG: Endless Skies--at ModRegistry in registerEventBus(bus)]: Successfully retrieved mod assets for modID: " + modID);
            assert mAssets != null;
            mAssets.registers.blocks.register(bus);
            EndlessSkies.logger.info("[DEBUG: Endless Skies--at ModRegistry in registerEventBus(bus)]: Registered event bus in the block register for modID: " + modID);
            mAssets.registers.items.register(bus);
            EndlessSkies.logger.info("[DEBUG: Endless Skies--at ModRegistry in registerEventBus(bus)]: Registered event bus in the item register for modID: " + modID);
            mAssets.registers.tabs.register(bus);
            EndlessSkies.logger.info("[DEBUG: Endless Skies--at ModRegistry in registerEventBus(bus)]: Registered event bus in the creative tab register for modID: " + modID);
        }
    }

    public static RegistryObject<Item> registerItem(String modID, String itemID, Supplier<? extends Item> supplier) {
        EndlessSkies.logger.info("[DEBUG: Endless Skies--at ModRegistry in registerItem(modID, itemID, supplier)]: func called for itemID: " + itemID + ", modID: " + modID);
        ModAssets mAssets = mAssetsTable.get(modID);
        if(mAssets == null) EndlessSkies.logger.info("[ERROR: Endless Skies--at ModRegistry in registerItem(modID, itemID, supplier)]: Error retrieving mod assets for modID: " + modID);
        else EndlessSkies.logger.info("[DEBUG: Endless Skies--at ModRegistry in registerItem(modID, itemID, supplier)]: Successfully retrieved mod assets for modID: " + modID);
        assert mAssets != null;
        RegistryObject<Item> out = mAssets.registers.items.register(itemID, supplier);
        if(out == null) EndlessSkies.logger.info("[ERROR: Endless Skies--at ModRegistry in registerItem(modID, itemID, supplier)]: Error registering itemID: " + itemID + ", modID: " + modID);
        else EndlessSkies.logger.info("[DEBUG: Endless Skies--at ModRegistry in registerItem(modID, itemID, supplier)]: Successfully registered itemID: " + itemID + ", modID: " + modID);
        return out;
    }

    public static RegistryObject<Block> registerBlock(String modID, String blockID, Supplier<? extends Block> supplier) {
        EndlessSkies.logger.info("[DEBUG: Endless Skies--at ModRegistry in registerBlock(modID, blockID, supplier)]: func called for blockID: " + blockID + ", modID: " + modID);
        ModAssets mAssets = mAssetsTable.get(modID);
        if(mAssets == null) EndlessSkies.logger.info("[ERROR: Endless Skies--at ModRegistry in registerBlock(modID, blockID, supplier)]: Error retrieving mod assets for modID: " + modID);
        else EndlessSkies.logger.info("[DEBUG: Endless Skies--at ModRegistry in registerBlock(modID, blockID, supplier)]: Successfully retrieved mod assets for modID: " + modID);
        assert mAssets != null;
        RegistryObject<Block> out = mAssets.registers.blocks.register(blockID, supplier);
        if(out == null) EndlessSkies.logger.info("[ERROR: Endless Skies--at ModRegistry in registerBlock(modID, blockID, supplier)]: Error registering block for blockID: " + blockID + ", modID: " + modID);
        else EndlessSkies.logger.info("[DEBUG: Endless Skies--at ModRegistry in registerBlock(modID, blockID, supplier)]: Successfully registered block for blockID: " + blockID + ", modID: " + modID);
        RegistryObject<Item> rObject1 = mAssets.registers.items.register(blockID, () -> new BlockItem(out.get(), new Item.Properties()));
        if(rObject1 == null) EndlessSkies.logger.info("[ERROR: Endless Skies--at ModRegistry in registerBlock(modID, blockID, supplier)]: Error registering item for blockID: " + blockID + ", modID: " + modID);
        else EndlessSkies.logger.info("[DEBUG: Endless Skies--at ModRegistry in registerBlock(modID, blockID, supplier)]: Successfully registered item for blockID: " + blockID + ", modID: " + modID);
        return out;
    }

    public static RegistryObject<CreativeModeTab> registerTab(String modID, String tabID, Supplier<? extends CreativeModeTab> supplier) {
        EndlessSkies.logger.info("[DEBUG: Endless Skies--at ModRegistry in registerTab(modID, tabID, supplier)]: func called for tabID: " + tabID + ", modID: " + modID);
        ModAssets mAssets = mAssetsTable.get(modID);
        if(mAssets == null) EndlessSkies.logger.info("[ERROR: Endless Skies--at ModRegistry in registerTab(modID, tabID, supplier)]: Error retrieving mod assets for modID: " + modID);
        else EndlessSkies.logger.info("[DEBUG: Endless Skies--at ModRegistry in registerTab(modID, tabID, supplier)]: Successfully retrieved mod assets for modID: " + modID);
        assert mAssets != null;
        RegistryObject<CreativeModeTab> out = mAssets.registers.tabs.register(tabID, supplier);
        if(out == null) EndlessSkies.logger.info("[ERROR: Endless Skies--at ModRegistry in registerTab(modID, tabID, supplier)]: Error registering tabID: " + tabID + ", modID: " + modID);
        else EndlessSkies.logger.info("[DEBUG: Endless Skies--at ModRegistry in registerTab(modID, tabID, supplier)]: Successfully registered tabID: " + tabID + ", modID: " + modID);
        return out;
    }
}
