package io.github.notwhiterice.endlessskies.block.factory;

import io.github.notwhiterice.endlessskies.block.entity.factory.TileEntityContext;
import io.github.notwhiterice.endlessskies.block.factory.data.BlockStateFactory;
import io.github.notwhiterice.endlessskies.block.factory.data.LootTableFactory;
import io.github.notwhiterice.endlessskies.datagen.ModLanguageProvider;
import io.github.notwhiterice.endlessskies.inventory.factory.MenuContext;
import io.github.notwhiterice.endlessskies.registry.object.ItemLikeContextv2;
import io.github.notwhiterice.endlessskies.registry.object.ModContext;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.RegistryObject;

import java.util.*;
import java.util.function.Supplier;

public class BlockContext extends ItemLikeContextv2<BlockContext, Block> {
    private static final Map<String, List<BlockContext>> instances = new HashMap<>();

    public static final Block.Properties defProp = BlockBehaviour.Properties.ofFullCopy(Blocks.ORANGE_WOOL);

    private Class<? extends Block> bClass;
    private Block.Properties bProp;
    private boolean makeItem = true;
    private BlockStateFactory stateFactory = BlockStateFactory.BLOCK_WITHOUT_MODEL;
    private LootTableFactory dropFactory = LootTableFactory.DROP_SELF;
    private Supplier<? extends Block> forcedSupplier;

    public TileEntityContext container;
    public MenuContext<?> menu;

    BlockContext(ModContext context, String name) { super(context, name, "BlockContext", instances); }

    protected void setStackSize(int size, String src) { super.setStackSize(size, src); }
    protected void setClass(Class<? extends Block> block, String src) { onEdit(src); bClass = block; }
    public boolean hasItem() { return makeItem; }
    public boolean hasContainer() { return container != null; }
    public boolean hasMenu() { return menu != null; }
    protected void toggleItem(String src) { onEdit(src); makeItem = !makeItem; }
    protected void setContainer(BlockEntityType.BlockEntitySupplier<? extends BlockEntity> factory, String src) {
        onEdit(src);
        if(hasContainer()) throw new IllegalStateException("Attempted to add a second container to a block");
        container = new TileEntityContext(ModContext.getContext(getModID()), getName(), factory, this);
    }
    protected <T extends AbstractContainerMenu> void setMenu(IContainerFactory<T> factory, MenuScreens.ScreenConstructor<T, ? extends AbstractContainerScreen<T>> screen, String src) {
        onEdit(src);
        if(hasMenu()) throw new IllegalStateException("Attempted to add a second menu to a block");
        menu = new MenuContext<>(ModContext.getContext(getModID()), getName(), factory, screen);
    }
    protected void forceSupplier(Supplier<? extends Block> supplier, String src) { onEdit(src); forcedSupplier = supplier; }
    public BlockStateFactory getStateFactory() { return stateFactory; }
    public LootTableFactory getDropFactory() { return dropFactory; }
    protected void setStateFactory(BlockStateFactory factory, String src) { onEdit(src); stateFactory = factory; }
    protected void setDropFactory(LootTableFactory factory, String src) { onEdit(src); dropFactory = factory; }

    public Block get() {
        if(rObject == null) throw new IllegalStateException(".get was called for a BlockContext before its registry object is registered");
        return rObject.get();
    }
    public RegistryObject<Block> getRegistryObject() {
        if(rObject == null) {
            if (forcedSupplier != null) rObject = modContext.BLOCKS.register(name, forcedSupplier);
            else {
                if (bProp == null) bProp = defProp;
                if (bClass != null) {
                    rObject = modContext.BLOCKS.register(name, () -> {
                        try {
                            if (bProp == defProp)
                                return bClass.newInstance();
                            else
                                return bClass.getDeclaredConstructor(Block.Properties.class)
                                        .newInstance(bProp);
                        } catch (Exception e) { throw new RuntimeException(e); }
                    });
                } else
                    rObject = modContext.BLOCKS.register(name, () -> new Block(defProp));
            }
            if(makeItem) modContext.ITEMS.register(name, () -> new BlockItem(rObject.get(), new Item.Properties().stacksTo(stackSize)));
        }
        return rObject;
    }

    public void setName(String name) { applyTranslation(name, "en_us"); }
    public void applyTranslation(String name, String locale) {
        ModLanguageProvider.translations.computeIfAbsent(locale, v -> new HashMap<>());
        ModLanguageProvider.translations.get(locale)
                .computeIfAbsent(getModID(), v -> new HashMap<>());
        ModLanguageProvider.translations.get(locale)
                .get(getModID()).computeIfAbsent(getName(), v -> name);
    }

    public static boolean isModKnown(String modID) { return instances.containsKey(modID); }
    public static boolean doesContextExist(String modID, String name) { return isModKnown(modID) ? (!instances.get(modID).stream().filter(v -> v.getName().equals(name)).toList().isEmpty()) : false; }
    public static BlockContext getContext(String modID, String name) { return isModKnown(modID) ? (instances.get(modID).stream().filter(v -> v.getName().equals(name)).toList().get(0)) : null; }
    public static Collection<String> listModBlocks(String modID) { return isModKnown(modID) ? instances.get(modID).stream().map(BlockContext::getID).toList() : Collections.emptyList(); }
    public static Collection<BlockContext> getModBlocks(String modID) { return isModKnown(modID) ? instances.get(modID) : Collections.emptyList(); }
    public static Collection<String> listAllBlocks() { return instances.values().stream().flatMap(modColl -> modColl.stream()).map(BlockContext::getID).toList(); }
    public static Collection<BlockContext> getAllBlocks() { return instances.values().stream().flatMap(Collection::stream).toList(); }
}
