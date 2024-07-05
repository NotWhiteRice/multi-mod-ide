package io.github.notwhiterice.endlessskies.registry.object.base;

import io.github.notwhiterice.endlessskies.util.StringHelper;

import java.util.Map;

public abstract class ContextBase<T extends ContextBase<T>> {
    //Private members
    private String name;
    private String parent;

    //Constructors
    public ContextBase(String name, String parent) {
        this.name = name;
        this.parent = parent;
    }

    //Getters and setters for private members
    public String getName() { return name; }
    public String getParent() { return parent; }

    //Custom functions
    /**
     * Returns true if the context originally had a unique id
     */
    public boolean registerInstance(Map<String, T> instances) {
        String temp = name;
        name = StringHelper.constructUniqueID(name, instances.keySet());
        instances.put(name, (T) this);
        return temp.equals(name);
    }
}
