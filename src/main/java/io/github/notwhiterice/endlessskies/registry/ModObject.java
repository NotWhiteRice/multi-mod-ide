package io.github.notwhiterice.endlessskies.registry;

import io.github.notwhiterice.endlessskies.util.StringHelper;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

public abstract class ModObject<T> {
    protected RegistryObject<T> registry;
    public ModContext parentMod;
    public String name;

    public ModObject(ModContext parent, String name) {
        this.parentMod = parent;
        this.name = name;
    }

    public String getModID() { return parentMod.modID; }
    public String getID(String separator) { return getModID()+separator+name; }

    public boolean isRegistered() { return registry != null; }

    public T get() { return isRegistered() ? registry.get() : null; }

    public abstract RegistryObject<T> getRegistry();

    public static <U extends ModObject> boolean doesInstanceExist(String modID, String name, List<U> instances) {
        for(U instance : instances) if(modID.equals(instance.getModID()) && name.equals(instance.name)) return true;
        return false;
    }

    public static <U extends ModObject> U getInstance(String modID, String name, List<U> instances) {
        for(U instance : instances) if(modID.equals(instance.getModID()) && name.equals(instance.name)) return instance;
        return null;
    }

    public static <U extends ModObject> List<U> getModInstances(String modID, List<U> instances) { return instances.stream().filter(v->v.getModID().equals(modID)).toList(); }

    public <U extends ModObject> boolean registerInstance(List<U> instances) {
        String temp = name;

        if(doesInstanceExist(getModID(), name, instances)) name = StringHelper
                .constructUniqueID(name, instances.stream().map(v->v.name).toList());

        instances.add((U) this);

        return temp.equals(name);
    }
}
