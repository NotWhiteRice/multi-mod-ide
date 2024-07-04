package io.github.deprecated.v3.endlessskies.registry.object;

import io.github.deprecated.v3.endlessskies.core.exception.DualRegistryException;
import io.github.deprecated.v3.endlessskies.registry.ModRegistry;
import io.github.deprecated.v3.endlessskies.registry.object.base.ContextBase;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class ModContext extends ContextBase<ModContext> {
    private static final Map<String, ModContext> instances = new HashMap<>();
    public static final ModContext dummy = new ModContext();
    public DeferredRegister<Block> BLOCKS;
    public DeferredRegister<Item> ITEMS;
    public DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS;
    public DeferredRegister<BlockEntityType<?>> TILE_ENTITIES;
    public DeferredRegister<MenuType<?>> MENUS;


    public ModContext() {}
    public ModContext(String modID) throws IllegalAccessException, DualRegistryException {
        super(modID, "ModContext");
        StackTraceElement caller = Thread.currentThread().getStackTrace()[2];
        if(!caller.getClassName().equals(ModRegistry.class.getName()))
            throw new IllegalAccessException("Unable to verify ownership of 'new ModContext(modID)'--detected '" + caller.getClassName() + "." + caller.getMethodName() + "'.");
        BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, modID);
        ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, modID);
        CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, modID);
        TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, modID);
        MENUS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, modID);
        registerInstance();
    }

    public String getModID() { return getName(); }

    @Override
    protected Map<String, ModContext> getInstanceMap() throws IllegalAccessException {
        StackTraceElement caller = Thread.currentThread().getStackTrace()[2];
        if(!caller.getClassName().equals(ContextBase.class.getName()))
            throw new IllegalAccessException("Unable to verify ownership of 'ModContext.instances'--detected '" + caller.getClassName() + "." + caller.getMethodName() + "'.");
        return instances;
    }

    @Override
    protected ModContext getReference() { return this; }

    public boolean doesContextExist(String name) { return instances.containsKey(name); }

    public ModContext getContext(String name) throws IllegalAccessException {
        StackTraceElement caller = Thread.currentThread().getStackTrace()[2];
        if(!caller.getClassName().equals(ContextBase.class.getName()))
            throw new IllegalAccessException("Unable to verify ownership of 'ModContext.instances'--detected '" + caller.getClassName() + "." + caller.getMethodName() + "'.");
        return instances.get(name);
    }

    public List<String> listKnownMods() { return instances.keySet().stream().toList(); }

    public BlockContext registerBlock(String name, Block.Properties prop) throws DualRegistryException, IllegalAccessException {
        return new BlockContext(this, name, prop, true);
    }
    public BlockContext registerBlock(String name, Supplier<? extends Block> supplier) throws DualRegistryException, IllegalAccessException {
        return new BlockContext(this, name, supplier, true);
    }
    public BlockContext registerBlock(String name, Class<? extends Block> bClass) throws DualRegistryException, IllegalAccessException {
        return new BlockContext(this, name, bClass, true);
    }
    public ItemContext registerItem(String name, Item.Properties prop) throws DualRegistryException, IllegalAccessException {
        return new ItemContext(this, name, prop);
    }
    public ItemContext registerItem(String name, Supplier<? extends Item> supplier) throws DualRegistryException, IllegalAccessException {
        return new ItemContext(this, name, supplier);
    }
    public ItemContext registerItem(String name, Class<? extends Item> bClass) throws DualRegistryException, IllegalAccessException {
        return new ItemContext(this, name, bClass);
    }

    public CreativeModeTabContext generateCreativeModeTab(String name) throws DualRegistryException, IllegalAccessException {
        return new CreativeModeTabContext(this, name);
    }

    public CreativeModeTabContext generateModSpecificTab() throws DualRegistryException, IllegalAccessException {
        return new CreativeModeTabContext(this, getModID());
    }

    public void finalizeRegisters(IEventBus bus) {
        BLOCKS.register(bus);
        ITEMS.register(bus);
        CREATIVE_MODE_TABS.register(bus);
        TILE_ENTITIES.register(bus);
        MENUS.register(bus);
    }
}
