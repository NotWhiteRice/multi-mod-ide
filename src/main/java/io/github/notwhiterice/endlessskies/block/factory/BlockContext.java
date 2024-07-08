package io.github.notwhiterice.endlessskies.block.factory;

import io.github.notwhiterice.endlessskies.EndlessSkies;
import io.github.notwhiterice.endlessskies.block.entity.factory.TileEntityContext;
import io.github.notwhiterice.endlessskies.block.factory.data.BlockStateFactory;
import io.github.notwhiterice.endlessskies.block.factory.data.LootTableFactory;
import io.github.notwhiterice.endlessskies.datagen.ModLanguageProvider;
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
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.RegistryObject;

import java.util.*;
import java.util.function.Supplier;

public class BlockContext extends ItemLikeContext<BlockContext, Block> {
    private static final Map<String, List<BlockContext>> instances = new HashMap<>();

    private BlockConstructor<? extends Block> constructor;
    private Block.Properties bProp;
    private float hardness = 0.0F;
    private float resistance = 0.0F;
    private boolean requiresTool = false;
    private MapColor mapColor = MapColor.NONE;
    private boolean makeItem = true;
    private BlockStateFactory stateFactory = BlockStateFactory.BLOCK_WITHOUT_MODEL;
    private LootTableFactory dropFactory = LootTableFactory.DROP_SELF;
    private Supplier<? extends Block> forcedSupplier;

    public TileEntityContext container;
    public MenuContext<?> menu;

    BlockContext(ModContext context, String name) { super(context, name, "BlockContext", instances); }

    protected void setStackSize(int size, String src) { super.setStackSize(size, src); }
    protected void setHardness(float hardness, String src) { onEdit(src); this.hardness = hardness; }
    protected void setResistance(float resistance, String src) { onEdit(src); this.resistance = resistance; }
    protected void setMapColor(MapColor color, String src) { onEdit(src); this.mapColor = color; }
    protected void requiresTool(String src) { onEdit(src); this.requiresTool = true; }
    protected void setParent(BlockConstructor<? extends Block> parent, String src) { onEdit(src); constructor = parent; }
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
        if(!EndlessSkies.canRegisterObject()) return null;
        if(rObject == null) {
            if(forcedSupplier != null) rObject = modContext.BLOCKS.register(name, forcedSupplier);
            else {
                if(bProp == null) bProp = Block.Properties.of();
                if(hardness != 0.0F || resistance != 0.0F) bProp = bProp.strength(hardness, resistance);
                if(mapColor != MapColor.NONE) bProp = bProp.mapColor(mapColor);
                if(requiresTool) bProp = bProp.requiresCorrectToolForDrops();
                if(constructor != null) rObject = modContext.BLOCKS.register(name, () -> constructor.create(bProp));
                else rObject = modContext.BLOCKS.register(name, () -> new Block(bProp));
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
    public static boolean doesContextExist(String modID, String name) { return isModKnown(modID) && (!instances.get(modID).stream().filter(v -> v.getName().equals(name)).toList().isEmpty()); }
    public static BlockContext getContext(String modID, String name) { return isModKnown(modID) ? (instances.get(modID).stream().filter(v -> v.getName().equals(name)).toList().getFirst()) : null; }
    public static Collection<String> listModBlocks(String modID) { return isModKnown(modID) ? instances.get(modID).stream().map(BlockContext::getID).toList() : Collections.emptyList(); }
    public static Collection<BlockContext> getModBlocks(String modID) { return isModKnown(modID) ? instances.get(modID) : Collections.emptyList(); }
    public static Collection<String> listAllBlocks() { return instances.values().stream().flatMap(Collection::stream).map(BlockContext::getID).toList(); }
    public static Collection<BlockContext> getAllBlocks() { return instances.values().stream().flatMap(Collection::stream).toList(); }
}
