package io.github.notwhiterice.endlessskies.registry.block;

import io.github.notwhiterice.endlessskies.EndlessSkies;
import io.github.notwhiterice.endlessskies.registry.block.data.BlockStateFactory;
import io.github.notwhiterice.endlessskies.registry.block.data.LootTableFactory;
import io.github.notwhiterice.endlessskies.datagen.ModLanguageProvider;
import io.github.notwhiterice.endlessskies.registry.block.entity.TileEntityContext;
import io.github.notwhiterice.endlessskies.registry.inventory.MenuContext;
import io.github.notwhiterice.endlessskies.registry.item.ItemLikeContext;
import io.github.notwhiterice.endlessskies.registry.ModContext;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.RegistryObject;

import java.util.*;
import java.util.function.Supplier;

public class BlockContext extends ItemLikeContext<Block> {
    public static final List<BlockContext> instances = new ArrayList<>();

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

    BlockContext(ModContext context, String name) {
        super(context, name, "BlockContext");

        registerInstance(instances);
    }

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
        container = new TileEntityContext(ModContext.getContext(getModID()), name, factory, this);
    }
    protected <T extends BlockEntity> void setContainer(BlockEntityType.BlockEntitySupplier<T> factory, BlockEntityRendererProvider<T> renderer, String src) {
        onEdit(src);
        if(hasContainer()) throw new IllegalStateException("Attempted to add a second container to a block");
        container = new TileEntityContext(ModContext.getContext(getModID()), name, factory, renderer, this);
    }
    protected <T extends AbstractContainerMenu> void setMenu(IContainerFactory<T> factory, MenuScreens.ScreenConstructor<T, ? extends AbstractContainerScreen<T>> screen, String src) {
        onEdit(src);
        if(hasMenu()) throw new IllegalStateException("Attempted to add a second menu to a block");
        menu = new MenuContext<>(ModContext.getContext(getModID()), name, factory, screen);
    }
    protected void forceSupplier(Supplier<? extends Block> supplier, String src) { onEdit(src); forcedSupplier = supplier; }
    public BlockStateFactory getStateFactory() { return stateFactory; }
    public LootTableFactory getDropFactory() { return dropFactory; }
    protected void setStateFactory(BlockStateFactory factory, String src) { onEdit(src); stateFactory = factory; }
    protected void setDropFactory(LootTableFactory factory, String src) { onEdit(src); dropFactory = factory; }

    public RegistryObject<Block> getRegistry() {
        if(!EndlessSkies.canRegisterObject()) return null;
        if(registry == null) {
            if(forcedSupplier != null) registry = ModContext.getContext(getModID()).BLOCKS.register(name, forcedSupplier);
            else {
                if(bProp == null) bProp = Block.Properties.of();
                if(hardness != 0.0F || resistance != 0.0F) bProp = bProp.strength(hardness, resistance);
                if(mapColor != MapColor.NONE) bProp = bProp.mapColor(mapColor);
                if(requiresTool) bProp = bProp.requiresCorrectToolForDrops();
                if(constructor != null) registry = parentMod.BLOCKS.register(name, () -> constructor.create(bProp));
                else registry = ModContext.getContext(getModID()).BLOCKS.register(name, () -> new Block(bProp));
            }
            if(makeItem) ModContext.getContext(getModID()).ITEMS.register(name, () -> new BlockItem(registry.get(), new Item.Properties().stacksTo(stackSize)));
        }
        return registry;
    }

    public void setName(String name) { applyTranslation(name, "en_us"); }
    public void applyTranslation(String name, String locale) {
        ModLanguageProvider.translations.computeIfAbsent(locale, v -> new HashMap<>());
        ModLanguageProvider.translations.get(locale)
                .computeIfAbsent(getModID(), v -> new HashMap<>());
        ModLanguageProvider.translations.get(locale)
                .get(getModID()).computeIfAbsent(name, v -> name);
    }

    public static boolean doesContextExist(String modID, String name) { return doesInstanceExist(modID, name, instances); }
    public static BlockContext getContext(String modID, String name) { return getInstance(modID, name, instances); }

    public static List<BlockContext> getModBlocks(String modID) { return getModInstances(modID, instances); }
}
