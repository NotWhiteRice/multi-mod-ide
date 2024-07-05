package io.github.notwhiterice.endlessskies.registry.object.base;

import io.github.notwhiterice.endlessskies.util.StringHelper;

import java.util.HashMap;
import java.util.Map;

public class InnerContextBase<T extends InnerContextBase<T>> {
    //Private members
    private String modID;
    private String name;
    private String parent;

    //Constructors
    public InnerContextBase(String modID, String name, String parent) {
        this.modID = modID;
        this.name = name;
        this.parent = parent;
    }

    //Getter functions
    public String getModID() { return modID; }
    public String getName() { return name; }
    public String getParent() { return parent; }

    //Custom functions
    /**
     * Returns true if the context originally had a unique id
     */
    public boolean registerInstance(Map<String, Map<String, T>> instances) {
        //if(!ModContext.isModKnown(modID)) throw new IllegalStateException("Attempted to create a " + parent + " for an unregistered mod--" + modID);
        String temp = name;
        if(instances.containsKey(modID))
            name = StringHelper.constructUniqueID(name, instances.get(modID).keySet());
        else instances.put(modID, new HashMap<>());
        instances.get(modID).put(name, (T) this);
        return temp.equals(name);
    }
}
