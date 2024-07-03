package io.github.notwhiterice.endlessskies.registries.object;

import io.github.notwhiterice.endlessskies.core.exception.DualRegistryException;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashMap;
import java.util.Map;

public class ModContext {
    public static final Map<String, ModContext> instances = new HashMap<>();
    public final DeferredRegister<Block> BLOCKS;
    public final DeferredRegister<Item> ITEMS;
    public final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS;
    public final String modID;
    private boolean lockRegisters = false;

    public ModContext(String modID) throws DualRegistryException {
        if(instances.containsKey(modID)) throw DualRegistryException.generate("ModContext instances", modID);
        this.modID = modID;
        BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, modID);
        ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, modID);
        CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, modID);

        instances.put(modID, this);
    }

    public BlockContext registerBlock(String name, Block.Properties prop) throws DualRegistryException {
        if(lockRegisters) throw new IllegalStateException("Attempted to register a block after registers had been finalized");
        BlockContext out = new BlockContext(modID, name, prop, true);
        return out;
    }

    public BlockContext registerBlock(String name, Class<? extends Block> bClass) throws DualRegistryException {
        if(lockRegisters) throw new IllegalStateException("Attempted to register a block after registers had been finalized");
        BlockContext out = new BlockContext(modID, name, bClass, true);
        return out;
    }

    public ItemContext registerItem(String name, Item.Properties prop) throws DualRegistryException {
        if(lockRegisters) throw new IllegalStateException("Attempted to register an item after registers had been finalized");
        ItemContext out = new ItemContext(modID, name, prop);
        return out;
    }

    public ItemContext registerItem(String name, Class<? extends Item> iClass) throws DualRegistryException {
        if(lockRegisters) throw new IllegalStateException("Attempted to register an item after registers had been finalized");
        ItemContext out = new ItemContext(modID, name, iClass);
        return out;
    }

    public CreativeModeTabContext generateCreativeModeTab(String name) throws DualRegistryException {
        if(lockRegisters) throw new IllegalStateException("Attempted to register a mod tab after registers had been finalized");
        return new CreativeModeTabContext(modID, name);
    }

    public CreativeModeTabContext generateModTab() throws DualRegistryException {
        if(lockRegisters) throw new IllegalStateException("Attempted to register a mod tab after registers had been finalized");
        return new CreativeModeTabContext(modID, modID);
    }

    public void finalizeRegisters(IEventBus bus) {
        lockRegisters = true;
        BLOCKS.register(bus);
        ITEMS.register(bus);
        for(CreativeModeTabContext context : CreativeModeTabContext.getContextsForMod(modID).values()) {
            context.generateRegistryObject();
        }
        CREATIVE_MODE_TABS.register(bus);
    }

    public boolean isLocked() { return lockRegisters; }
}
