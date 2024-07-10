package io.github.notwhiterice.endlessskies.registry;

import io.github.notwhiterice.endlessskies.creativetab.factory.CreativeModeTabContext;
import io.github.notwhiterice.endlessskies.logger.ModLogger;
import io.github.notwhiterice.endlessskies.util.StringHelper;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;

public class ModContext {
    public static final List<ModContext> instances = new ArrayList<>();
    public String modID;


    public DeferredRegister<Block> BLOCKS;
    public DeferredRegister<Item> ITEMS;
    public DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS;
    public DeferredRegister<BlockEntityType<?>> TILE_ENTITIES;
    public DeferredRegister<MenuType<?>> MENUS;
    public ModLogger logger;

    //Constructor
    public ModContext(String modID) {
        if(modID == null) return;
        logger = new ModLogger(modID);

        this.modID = modID;

        if(registerInstance()) logger.info("ModContext", "<init>(modID)", "Registering mod context for modID: '" + modID + "'");
        else new IllegalStateException("ModContext does not support duplicate ids, found duplicate id '" + modID + "'--this won't change in the foreseeable future");

        BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, modID);
        ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, modID);
        CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, modID);
        TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, modID);
        MENUS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, modID);
    }

    public static ModContext getContext(String modID) {
        for(ModContext instance : instances) if(instance.modID.equals(modID)) return instance;
        return null;
    }

    public static List<String> getModList() { return instances.stream().map(v -> v.modID).toList(); }

    public boolean registerInstance() {
        String temp = modID;

        modID = StringHelper.constructUniqueID(modID, instances.stream().map(v->v.modID).toList());
        instances.add(this);

        return temp.equals(modID);
    }

    //Custom functions
    public CreativeModeTabContext registerCreativeTab(String name) { return new CreativeModeTabContext(this, name); }
    public CreativeModeTabContext generateModSpecificTab() { return new CreativeModeTabContext(this, this.modID); }

    public void finalizeRegisters(IEventBus bus) {
        BLOCKS.register(bus);
        ITEMS.register(bus);
        CREATIVE_MODE_TABS.register(bus);
        TILE_ENTITIES.register(bus);
        MENUS.register(bus);
    }
}
