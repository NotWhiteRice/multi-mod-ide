package io.github.notwhiterice.endlessskies.registry.object;

import io.github.notwhiterice.endlessskies.core.exception.ClosedContextException;
import io.github.notwhiterice.endlessskies.util.StringHelper;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class InnerContextv2<T extends InnerContextv2<T, P>, P> {
    protected String modID;
    protected String name;
    private String contextType;
    protected ModContext modContext;

    protected boolean isLocked = false;
    protected RegistryObject<P> rObject;

    protected InnerContextv2(ModContext context, String name, String type) {
        this.modID = context.getModID();
        this.name = name;
        this.contextType = type;
        this.modContext = context;
    }

    public String getModID() { return modID; }
    public String getName() { return name; }
    public String getType() { return contextType; }
    public String getID() { return modID + ":" + name; }

    public void close() { isLocked = true; }
    public boolean isLocked() { return isLocked || (rObject != null); }
    protected void onEdit(String src) { if(isLocked()) throw new ClosedContextException(src, contextType); }

    public abstract P get();
    public abstract RegistryObject<P> getRegistryObject();

    protected boolean registerInstance(Map<String, List<T>> instances) {
        String temp = name;

        if(instances.containsKey(modID)) name = StringHelper
                .constructUniqueID(name, instances.keySet());
        else instances.put(modID, new ArrayList<>());
        instances.get(modID).add((T) this);

        return temp.equals(name);
    }
}