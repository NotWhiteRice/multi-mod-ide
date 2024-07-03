package io.github.notwhiterice.endlessskies.registries.object;

import io.github.notwhiterice.endlessskies.core.exception.DualRegistryException;
import io.github.notwhiterice.endlessskies.datagen.tag.BlockStateProviderTag;
import io.github.notwhiterice.endlessskies.registries.ModRegistry;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.HashMap;
import java.util.Map;

public class BlockContext extends ItemLikeContext {
    private static final Map<String, Map<String, BlockContext>> instances = new HashMap<>();

    private BlockStateProviderTag stateProviderTag = BlockStateProviderTag.BLOCKS_WITHOUT_MODEL;

    public BlockContext(String modID, String name, Block.Properties prop, boolean doRegisterItem) throws DualRegistryException {
        if(doesContextExist(modID, name)) throw DualRegistryException.generate(modID + ":" + name, "BlockContext.instances");
        this.modID = modID;
        this.name = name;

        if(!ModRegistry.isModKnown(modID)) throw new IllegalStateException("Attempted to create a BlockContext for an unregistered mod");
        rObject = ModContext.instances.get(modID).BLOCKS.register(name, () -> new Block(prop));
        if(doRegisterItem) ModContext.instances.get(modID).ITEMS.register(name, () -> new BlockItem((Block) rObject.get(), new Item.Properties()));

        if(!isModKnown(modID)) instances.put(modID, new HashMap<>());
        instances.get(modID).put(name, this);
    }

    public BlockContext(String modID, String name, Class<? extends Block> bClass, boolean doRegisterItem) throws DualRegistryException {
        if(doesContextExist(modID, name)) throw DualRegistryException.generate(modID + ":" + name, "BlockContext.instances");
        this.modID = modID;
        this.name = name;

        if(!ModRegistry.isModKnown(modID)) throw new IllegalStateException("Attempted to create a BlockContext for an unregistered mod");
        rObject = ModContext.instances.get(modID).BLOCKS.register(name, () -> {
            try {
                return bClass.newInstance();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        if(doRegisterItem) ModContext.instances.get(modID).ITEMS.register(name, () -> new BlockItem((Block) rObject.get(), new Item.Properties()));

        if(!isModKnown(modID)) instances.put(modID, new HashMap<>());
        instances.get(modID).put(name, this);
    }

    public static boolean isModKnown(String modID) {
        return instances.containsKey(modID);
    }

    public static boolean doesContextExist(String modID, String name) {
        if(!isModKnown(modID)) return false;
        return instances.get(modID).containsKey(name);
    }

    public static Map<String, BlockContext> getContextsForMod(String modID) {
        if(!isModKnown(modID)) throw new IllegalArgumentException("Attempted to gather BlockContexts for a mod without any registered blocks");
        return instances.get(modID);
    }

    public Block asBlock() { return (Block) rObject.get(); }

    public BlockStateProviderTag getStateProviderTag() { return stateProviderTag; }

    public BlockContext setStateProviderTag(BlockStateProviderTag tag) {
        stateProviderTag = tag;
        return this;
    }
}
