package io.github.notwhiterice.endlessskies.block.factory;

import io.github.notwhiterice.endlessskies.block.MineralInfuserBlock;
import io.github.notwhiterice.endlessskies.block.entity.factory.TileEntityContext;
import io.github.notwhiterice.endlessskies.datagen.tag.BlockStateProviderTag;
import io.github.notwhiterice.endlessskies.datagen.tag.LootTableProviderTag;
import io.github.notwhiterice.endlessskies.inventory.factory.MenuContext;
import io.github.notwhiterice.endlessskies.registry.object.ItemLikeContext;
import io.github.notwhiterice.endlessskies.registry.object.ModContext;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.RegistryObject;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class BlockContext extends ItemLikeContext<BlockContext> {
    //Private static variables
    private static final Map<String, Map<String, BlockContext>> instances = new HashMap<>();

    //Public static variables
    public static final BlockContext dummy = new BlockContext(ModContext.dummy, null, null, false);

    //Private variables
    private BlockStateProviderTag blockStateDataTag = BlockStateProviderTag.BLOCK_WITHOUT_MODEL;
    private LootTableProviderTag lootTableDataTag = LootTableProviderTag.DROP_SELF;

    //Public variables
    public TileEntityContext entityContext;
    //public RegistryObject<Block> rObject;

    //Constructor
    public BlockContext(ModContext context, String name, BlockFactory factory, boolean doItem) {
        super(context.getName(), name, "ItemContext");
        if(context.equals(ModContext.dummy) || name == null || factory == null) return;
        if(registerInstance(instances)) context.logger.info("BlockContext", "<init>(context, name, factory, doItem)", "Registering block context for block: '" + context.getModID() + ":" + getName() + "'");
        else context.logger.info("BlockContext", "<init>(context, name, factory, doItem)", "Registered duplicate block context for block: '" + context.getModID() + ":" + name + "' as '" + context.getModID() + ":" + getName() + "'");
        rObject = context.BLOCKS.register(getName(), factory.generate());
        if(doItem) context.ITEMS.register(getName(), () -> new BlockItem((Block) rObject.get(), new Item.Properties()));
    }

    //Getter functions
    public Block getBlock() { return (Block) rObject.get(); }
    public MenuContext<? extends AbstractContainerMenu> getMenuType() { return hasMenu() ? entityContext.menuContext : null; }
    public BlockStateProviderTag getStateDataTag() { return blockStateDataTag; }
    public LootTableProviderTag getLootDataTag() { return lootTableDataTag; }
    public BlockContext setStateDataTag(BlockStateProviderTag tag) {
        blockStateDataTag = tag;
        return this;
    }
    public BlockContext setLootDataTag(LootTableProviderTag tag) {
        lootTableDataTag = tag;
        return this;
    }


    /* Special getter functions
     * 1-increases in "depth"
     * 2-increases in "type strength"
     * 3-increases in "plurality"
     */
    public static boolean isModKnown(String modID) { return instances.containsKey(modID); }
    public static boolean doesContextExist(String modID, String name) { return isModKnown(modID) ? instances.get(modID).containsKey(name) : false; }
    public static BlockContext getContext(String modID, String name) { return isModKnown(modID) ? instances.get(modID).get(name) : null; }
    public static Collection<String> listModBlocks(String modID) { return isModKnown(modID) ? instances.get(modID).values().stream().map(BlockContext::getID).toList() : Collections.emptyList(); }
    public static Collection<BlockContext> getModBlocks(String modID) { return isModKnown(modID) ? instances.get(modID).values() : Collections.emptyList(); }
    public static Collection<String> listAllBlocks() { return instances.values().stream().flatMap(modColl -> modColl.values().stream()).map(BlockContext::getID).toList(); }
    public static Collection<BlockContext> getAllBlocks() { return instances.values().stream().flatMap(modColl -> modColl.values().stream()).toList(); }

    //Custom functions
    public boolean hasTileEntity() { return entityContext != null; }
    public <T extends BlockEntity> BlockContext addTileEntity(BlockEntityType.BlockEntitySupplier<T> factory) {
        if(rObject == null) throw new IllegalStateException("The registry object disappeared");
        if(hasTileEntity()) throw new IllegalStateException("Attempted to add a second tile entity to a block");
        entityContext = new TileEntityContext(ModContext.getContext(getModID()), getName(), factory, getBlock());
        return this;
    }
    public boolean hasMenu() {
        if (!hasTileEntity()) return false;
        return entityContext.menuContext == null;
    }
    public <T extends AbstractContainerMenu> BlockContext addMenu(IContainerFactory<T> factory, MenuScreens.ScreenConstructor<T, ? extends AbstractContainerScreen<T>> screen) {
        if(!hasTileEntity()) throw new IllegalStateException("Attempted to add a menu to a block with no tile entity");
        if(hasMenu()) throw new IllegalStateException("Attempted to add a second menu to a block");
        entityContext.menuContext = new MenuContext<>(ModContext.getContext(getModID()), getName(), factory, screen);
        return this;
    }
}