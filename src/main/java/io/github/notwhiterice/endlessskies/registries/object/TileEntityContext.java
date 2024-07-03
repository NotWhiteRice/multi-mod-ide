package io.github.notwhiterice.endlessskies.registries.object;

import io.github.notwhiterice.endlessskies.core.exception.DualRegistryException;
import io.github.notwhiterice.endlessskies.registries.ModRegistry;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.HashMap;
import java.util.Map;

public class TileEntityContext {
    private static final Map<String, Map<String, TileEntityContext>> instances = new HashMap<>();
    private final String modID;
    private final String name;


    public TileEntityContext(String modID, String name, Class<? extends BlockEntity> bClass) throws DualRegistryException {
        //if(doesContextExist(modID, name)) throw DualRegistryException.generate(modID + ":" + name, "BlockContext.instances");
        this.modID = modID;
        this.name = name;

        if(!ModRegistry.isModKnown(modID)) throw new IllegalStateException("Attempted to create a BlockContext for an unregistered mod");

        //if(!isModKnown(modID)) instances.put(modID, new HashMap<>());
        instances.get(modID).put(name, this);
    }
}
