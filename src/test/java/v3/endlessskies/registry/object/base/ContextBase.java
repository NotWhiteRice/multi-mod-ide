package io.github.deprecated.v3.endlessskies.registry.object.base;

import io.github.deprecated.v3.endlessskies.core.exception.DualRegistryException;

import java.util.Map;

public abstract class ContextBase<T extends ContextBase<T>> {
    private String name;
    private String parent;

    public ContextBase() {}
    public ContextBase(String name, String parent) {
        this.name = name;
        this.parent = parent;
    }

    public String getName() { return name; }
    public String getParent() { return parent; }

    protected abstract Map<String, T> getInstanceMap() throws IllegalAccessException;
    protected abstract T getReference();

    public abstract boolean doesContextExist(String name);

    public abstract T getContext(String name) throws IllegalAccessException;

    protected void registerInstance() throws IllegalAccessException, DualRegistryException {
        if(doesContextExist(name)) throw new DualRegistryException(parent + ".getInstanceMap()", name);
        getInstanceMap().put(name, getReference());
    }
}
