package io.github.notwhiterice.endlessskies.block.factory;

import io.github.notwhiterice.endlessskies.block.factory.data.BlockStateFactory;
import io.github.notwhiterice.endlessskies.block.factory.data.LootTableFactory;
import io.github.notwhiterice.endlessskies.registry.object.ModContext;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.network.IContainerFactory;

import java.util.function.Supplier;

public class BlockFactory {
    private ModContext parentMod;
    private BlockContext context;

    private BlockFactory(ModContext context) { this.parentMod = context; }
    private BlockFactory(BlockContext context, ModContext parent) { this(parent); this.context = context; }

    public static BlockFactory init(ModContext modContext) {
        return new BlockFactory(modContext);
    }
    public BlockFactory block(String name) {
        return new BlockFactory(new BlockContext(parentMod, name), parentMod);
    }
    public BlockFactory setParent(Class<? extends Block> item) {
        context.setClass(item, "BlockFactory.setParent");
        return this;
    }
    public BlockFactory forceSupplier(Supplier<? extends Block> supplier) {
        context.forceSupplier(supplier, "BlockFactory.forceSupplier");
        return this;
    }
    public BlockFactory toggleItem() {
        context.toggleItem("BlockFactory.toggleItem");
        return this;
    }
    public BlockFactory setStateFactory(BlockStateFactory factory) {
        context.setStateFactory(factory, "BlockFactory.setStateFactory");
        return this;
    }
    public BlockFactory setDrops(LootTableFactory factory) {
        context.setDropFactory(factory, "BlockFactory.setDrops");
        return this;
    }

    public BlockFactory setName(String name) { return this.applyTranslation(name, "en_us"); }
    public BlockFactory applyTranslation(String name, String locale) {
        context.applyTranslation(name, locale);
        return this;
    }
    public BlockFactory setStackSize(int size) {
        context.setStackSize(size, "BlockFactory.setStackSize");
        return this;
    }

    public BlockFactory setContainer(BlockEntityType.BlockEntitySupplier<? extends BlockEntity> factory) {
        context.setContainer(factory, "BlockFactory.setContainer");
        return this;
    }

    public <T extends AbstractContainerMenu> BlockFactory setMenu(IContainerFactory<T> factory, MenuScreens.ScreenConstructor<T, ? extends AbstractContainerScreen<T>> screen) {
        context.setMenu(factory, screen, "BlockFactory.setMenu");
        return this;
    }

    public BlockContext close() {
        context.close();
        BlockContext out = context;
        context = null;
        return out;
    }
}
