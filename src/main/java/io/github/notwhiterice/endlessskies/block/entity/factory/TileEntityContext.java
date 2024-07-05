package io.github.notwhiterice.endlessskies.block.entity.factory;


import io.github.notwhiterice.endlessskies.block.factory.BlockContext;
import io.github.notwhiterice.endlessskies.datagen.tag.ItemModelProviderTag;
import io.github.notwhiterice.endlessskies.inventory.factory.MenuContext;
import io.github.notwhiterice.endlessskies.registry.object.ModContext;
import io.github.notwhiterice.endlessskies.registry.object.base.InnerContextBase;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.RegistryObject;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class TileEntityContext extends InnerContextBase<TileEntityContext> {
    //Private static variables
    private static final Map<String, Map<String, TileEntityContext>> instances = new HashMap<>();

    //Public static variables
    public static final TileEntityContext dummy = new TileEntityContext(ModContext.dummy, null, null, null);

    //Private variables
    private ItemModelProviderTag modelDataTag = ItemModelProviderTag.ITEM_WITHOUT_MODEL;

    //Public variables
    public RegistryObject<BlockEntityType<?>> rObject;
    public BlockEntityType.BlockEntitySupplier<?> factory;
    public BlockContext parentBlock;
    public MenuContext<?> menuContext;

    //Constructor
    public TileEntityContext(ModContext context, String name, BlockEntityType.BlockEntitySupplier<?> factory, BlockContext parentBlock) {
        super(context.getName(), name, "TileEntityContext");
        if(context.equals(ModContext.dummy) || name == null || factory == null || parentBlock == null) return;
        if(registerInstance(instances)) context.logger.info("TileEntityContext", "<init>(context, name, factory, parent)", "Registering tile entity context for tile entity: '" + context.getModID() + ":" + getName() + "'");
        else context.logger.info("TileEntityContext", "<init>(context, name, factory, parent)", "Registered duplicate tile entity context for tile entity: '" + context.getModID() + ":" + name + "' as '" + context.getModID() + ":" + getName() + "'");
        this.factory = factory;
        this.parentBlock = parentBlock;
    }

    //Getter functions
    public BlockEntityType<? extends BlockEntity> getEntityType() { return rObject.get(); }
    public RegistryObject<BlockEntityType<?>> getRegistryObject() {
        if(rObject == null) rObject = ModContext.getContext(getModID()).TILE_ENTITIES.register(getName(),
                () -> BlockEntityType.Builder.of(factory, parentBlock.getBlock()).build(null));
        return rObject;
    }


    /* Special getter functions
     * 1-increases in "depth"
     * 2-increases in "type strength"
     * 3-increases in "plurality"
     */
    public static boolean isModKnown(String modID) { return instances.containsKey(modID); }
    public static boolean doesContextExist(String modID, String name) { return isModKnown(modID) ? instances.get(modID).containsKey(name) : false; }
    public static TileEntityContext getContext(String modID, String name) { return isModKnown(modID) ? instances.get(modID).get(name) : null; }
    public static Collection<String> listModTileEnts(String modID) { return isModKnown(modID) ? instances.get(modID).values().stream().map(TileEntityContext::getID).toList() : Collections.emptyList(); }
    public static Collection<TileEntityContext> getModTileEnts(String modID) { return isModKnown(modID) ? instances.get(modID).values() : Collections.emptyList(); }
    public static Collection<String> listAllTileEnts() { return instances.values().stream().flatMap(modColl -> modColl.values().stream()).map(TileEntityContext::getID).toList(); }
    public static Collection<TileEntityContext> getAllTileEnts() { return instances.values().stream().flatMap(modColl -> modColl.values().stream()).toList(); }
}