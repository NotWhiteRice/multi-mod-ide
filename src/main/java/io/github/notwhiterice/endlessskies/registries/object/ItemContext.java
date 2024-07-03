package io.github.notwhiterice.endlessskies.registries.object;

import io.github.notwhiterice.endlessskies.core.exception.DualRegistryException;
import io.github.notwhiterice.endlessskies.datagen.tag.ItemModelProviderTag;
import net.minecraft.world.item.Item;

import java.util.HashMap;
import java.util.Map;

public class ItemContext extends ItemLikeContext {
    private static final Map<String, Map<String, ItemContext>> instances = new HashMap<>();
    private final String modID;
    private final String name;

    private ItemModelProviderTag modelProviderTag = ItemModelProviderTag.ITEM_WITHOUT_MODEL;

    public ItemContext(String modID, String name, Item.Properties prop) throws DualRegistryException {
        if(doesContextExist(modID, name)) throw DualRegistryException.generate(modID + ":" + name, "ItemContext.instances");
        this.modID = modID;
        this.name = name;

        if(!ModContext.instances.containsKey(modID)) throw new IllegalStateException("Attempted to create a ItemContext for an unregistered mod");
        rObject = ModContext.instances.get(modID).ITEMS.register(name, () -> new Item(prop));

        if(!isModKnown(modID)) instances.put(modID, new HashMap<>());
        instances.get(modID).put(name, this);
    }

    public ItemContext(String modID, String name, Class<? extends Item> iClass) throws DualRegistryException {
        if(doesContextExist(modID, name)) throw DualRegistryException.generate(modID + ":" + name, "ItemContext.instances");
        this.modID = modID;
        this.name = name;

        if(!ModContext.instances.containsKey(modID)) throw new IllegalStateException("Attempted to create a ItemContext for an unregistered mod");
        rObject = ModContext.instances.get(modID).ITEMS.register(name, () -> {
            try {
                return iClass.newInstance();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        if(!isModKnown(modID)) instances.put(modID, new HashMap<>());
        instances.get(modID).put(name, this);
    }

    public String getModID() { return modID; }

    public static boolean isModKnown(String modID) {
        return instances.containsKey(modID);
    }

    public static boolean doesContextExist(String modID, String name) {
        if(!instances.containsKey(modID)) return false;
        return instances.get(modID).containsKey(name);
    }

    public static Map<String, ItemContext> getContextsForMod(String modID) {
        if(!isModKnown(modID)) throw new IllegalArgumentException("Attempted to gather ItemContexts for a mod without any registered items");
        return instances.get(modID);
    }

    public Item asItem() { return (Item) rObject.get(); }

    public ItemModelProviderTag getModelProviderTag() { return modelProviderTag; }

    public ItemContext setModelProviderTag(ItemModelProviderTag tag) {
        modelProviderTag = tag;
        return this;
    }
}
