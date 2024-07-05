package io.github.notwhiterice.endlessskies.registry.object;

import io.github.notwhiterice.endlessskies.registry.object.base.ContextBase;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ModContext extends ContextBase<ModContext> {
    //Private static variables
    private static final Map<String, ModContext> instances = new HashMap<>();

    //Public static variables
    public static final ModContext dummy = new ModContext(null);

    //Public variables
    public DeferredRegister<Block> BLOCKS;
    public DeferredRegister<Item> ITEMS;
    public DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS;
    public DeferredRegister<BlockEntityType<?>> TILE_ENTITIES;
    public DeferredRegister<MenuType<?>> MENUS;
    public ModLogger logger;

    //Constructor
    public ModContext(String modID) {
        super(modID, "ModContext");
        if(modID == null) return;
        logger = new ModLogger(modID);

        if(registerInstance(instances)) logger.info("ModContext", "<init>(modID)", "Registering mod context for modID: '" + modID + "'");
        else logger.fatal("ModContext", "<init>(modID)", "Registered duplicate mod context for modID: '" + modID + "'",
                new IllegalStateException("ModContext does not support duplicate ids, found duplicate id '" + modID + "'--this won't change in the foreseeable future"));

        BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, modID);
        ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, modID);
        CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, modID);
        TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, modID);
        MENUS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, modID);
    }

    /* Special getter functions
    * 1-increases in "depth"
    * 2-increases in "type strength"
    * 3-increases in "plurality"
    */
    public static boolean isModKnown(String modID) { return instances.containsKey(modID); }
    public static ModContext getModContext(String modID) { return instances.get(modID); }
    public static Collection<String> getModList() { return instances.keySet(); }
    public static Collection<ModContext> getAllMods() { return instances.values(); }

    //Custom functions
    //registerBlock()
    //registerItem()
    //registerCreativeTab()

    public void finalizeRegisters(IEventBus bus) {
        BLOCKS.register(bus);
        ITEMS.register(bus);
        CREATIVE_MODE_TABS.register(bus);
        TILE_ENTITIES.register(bus);
        MENUS.register(bus);
    }
}
