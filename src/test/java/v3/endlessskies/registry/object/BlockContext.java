package io.github.deprecated.v3.endlessskies.registry.object;

import io.github.notwhiterice.endlessskies.core.exception.DualRegistryException;
import io.github.deprecated.v3.endlessskies.datagen.tag.BlockStateProviderTag;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class BlockContext extends ItemLikeContext<BlockContext> {
    private static final Map<String, Map<String, BlockContext>> instances = new HashMap<>();
    public static final BlockContext dummy = new BlockContext();
    private RegistryObject<Block> rObject = null;

    private BlockStateProviderTag stateProviderTag = BlockStateProviderTag.BLOCK_WITHOUT_MODEL;

    public BlockContext() { super(); }
    public BlockContext(ModContext context, String name, Block.Properties prop, boolean doRegisterItem) throws DualRegistryException, IllegalAccessException {
        super(context, name, "BlockContext");
        rObject = context.BLOCKS.register(name, () -> new Block(prop));
        if(doRegisterItem) context.ITEMS.register(name, () -> new BlockItem(rObject.get(), new Item.Properties()));
        registerInstance();
    }
    public BlockContext(ModContext context, String name, Supplier<? extends Block> supplier, boolean doRegisterItem) throws DualRegistryException, IllegalAccessException {
        super(context, name, "BlockContext");
        rObject = context.BLOCKS.register(name, supplier);
        if(doRegisterItem) context.ITEMS.register(name, () -> new BlockItem(rObject.get(), new Item.Properties()));
        registerInstance();
    }
    public BlockContext(ModContext context, String name, Class<? extends Block> bClass, boolean doRegisterItem) throws DualRegistryException, IllegalAccessException {
        super(context, name, "BlockContext");
        rObject = context.BLOCKS.register(name, () -> {
            try {
                return bClass.newInstance();
            } catch(Exception e) {
                throw new RuntimeException(e);
            }
        });
        if(doRegisterItem) context.ITEMS.register(name, () -> new BlockItem(rObject.get(), new Item.Properties()));
        registerInstance();
    }

    @Override
    public Map<String, Map<String, BlockContext>> getInstanceMap() { return instances; }

    @Override
    protected BlockContext getReference() { return this; }

    public boolean isModKnown(String modID) { return instances.containsKey(modID); }

    public boolean doesContextExist(String modID, String name) {
        if(!instances.containsKey(modID)) return false;
        return instances.get(modID).containsKey(name);
    }

    public List<BlockContext> getContextsForMod(String modID) {
        if(!isModKnown(modID)) return new ArrayList<>();
        return instances.get(modID).values().stream().toList();
    }

    public BlockContext getContext(String modID, String name) {
        if(!instances.containsKey(modID)) return null;
        return instances.get(modID).get(name);
    }



    public Block asBlock() { return rObject.get(); }

    public BlockStateProviderTag getStateProviderTag() { return stateProviderTag; }

    public BlockContext setStateProviderTag(BlockStateProviderTag tag) {
        stateProviderTag = tag;
        return this;
    }
}
