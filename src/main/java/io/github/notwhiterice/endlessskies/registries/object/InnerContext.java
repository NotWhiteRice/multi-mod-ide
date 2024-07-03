package io.github.notwhiterice.endlessskies.registries.object;

import java.util.Map;

public abstract class InnerContext {
    protected final String modID;
    protected final String name;

    public InnerContext(String modID, String name, String parent) {
        this.modID = modID;
        this.name = name;
    }

    protected abstract Map<String, Map<String, ? extends InnerContext>> getInstanceMap();
    public String getModID() { return modID; }
    public String getName() { return name; }

    public boolean isModKnown(String modID) { return getInstanceMap().containsKey(modID); }


}
