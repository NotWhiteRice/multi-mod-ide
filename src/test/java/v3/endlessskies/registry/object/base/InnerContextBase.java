package io.github.deprecated.v3.endlessskies.registry.object.base;

import io.github.deprecated.v3.endlessskies.registry.ModRegistry;
import io.github.deprecated.v3.endlessskies.registry.object.ModContext;
import io.github.notwhiterice.endlessskies.core.exception.DualRegistryException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class InnerContextBase<T extends InnerContextBase<T>> {
    private String modID;
    private String name;
    private String parent;

    public InnerContextBase() {}
    public InnerContextBase(ModContext context, String name, String parent) {
        if(context == null) return;
        this.modID = context.getModID();
        this.name = name;
        this.parent = parent;
    }

    public String getModID() { return modID; }
    public String getName() { return name; }
    public String getParent() { return parent; }

    protected abstract Map<String, Map<String, T>> getInstanceMap() throws IllegalAccessException;
    protected abstract T getReference();

    public abstract boolean isModKnown(String modID);
    public abstract boolean doesContextExist(String modID, String name);

    public abstract List<T> getContextsForMod(String modID);
    public abstract T getContext(String modID, String name);

    protected void registerInstance() throws DualRegistryException, IllegalAccessException {
        if(doesContextExist(modID, name))
            throw new DualRegistryException(parent + ".getInstanceMap()", modID + ":" + name);
        if(!ModRegistry.isModKnown(modID))
            throw new IllegalStateException("Attempted to create a " + parent + " for an unregistered mod");

        getInstanceMap().computeIfAbsent(modID, k -> new HashMap<>());
        getInstanceMap().get(modID).put(name, getReference());
    }
}
