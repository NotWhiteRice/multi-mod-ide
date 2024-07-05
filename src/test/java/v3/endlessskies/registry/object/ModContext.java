package io.github.deprecated.v3.endlessskies.registry.object;

import io.github.deprecated.v3.endlessskies.registry.ModRegistry;
import io.github.deprecated.v3.endlessskies.registry.object.base.ContextBase;
import io.github.notwhiterice.endlessskies.core.exception.DualRegistryException;
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
    public io.github.deprecated.v3.endlessskies.registry.object.BlockContext registerBlock(String name, Block.Properties prop) throws DualRegistryException, IllegalAccessException {
        return new io.github.deprecated.v3.endlessskies.registry.object.BlockContext(this, name, prop, true);
    }
    public io.github.deprecated.v3.endlessskies.registry.object.BlockContext registerBlock(String name, Supplier<? extends Block> supplier) throws DualRegistryException, IllegalAccessException {
        return new io.github.deprecated.v3.endlessskies.registry.object.BlockContext(this, name, supplier, true);
    }
    public io.github.deprecated.v3.endlessskies.registry.object.BlockContext registerBlock(String name, Class<? extends Block> bClass) throws DualRegistryException, IllegalAccessException {
        return new io.github.deprecated.v3.endlessskies.registry.object.BlockContext(this, name, bClass, true);
    }
    public io.github.deprecated.v3.endlessskies.registry.object.ItemContext registerItem(String name, Item.Properties prop) throws DualRegistryException, IllegalAccessException {
        return new io.github.deprecated.v3.endlessskies.registry.object.ItemContext(this, name, prop);
    }
    public io.github.deprecated.v3.endlessskies.registry.object.ItemContext registerItem(String name, Supplier<? extends Item> supplier) throws DualRegistryException, IllegalAccessException {
        return new io.github.deprecated.v3.endlessskies.registry.object.ItemContext(this, name, supplier);
    }
    public io.github.deprecated.v3.endlessskies.registry.object.ItemContext registerItem(String name, Class<? extends Item> bClass) throws DualRegistryException, IllegalAccessException {
        return new io.github.deprecated.v3.endlessskies.registry.object.ItemContext(this, name, bClass);
    }

    public io.github.deprecated.v3.endlessskies.registry.object.CreativeModeTabContext generateCreativeModeTab(String name) throws DualRegistryException, IllegalAccessException {
        return new io.github.deprecated.v3.endlessskies.registry.object.CreativeModeTabContext(this, name);
    }

    public io.github.deprecated.v3.endlessskies.registry.object.CreativeModeTabContext generateModSpecificTab() throws DualRegistryException, IllegalAccessException {
        return new io.github.deprecated.v3.endlessskies.registry.object.CreativeModeTabContext(this, getModID());
    }
}
