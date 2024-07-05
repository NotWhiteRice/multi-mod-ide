package io.github.notwhiterice.endlessskies.item.factory;

import io.github.notwhiterice.endlessskies.registry.object.ItemLikeContext;
import io.github.notwhiterice.endlessskies.registry.object.ModContext;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;

import java.util.*;

public class ItemContext extends ItemLikeContext<ItemContext> {
    //Private static variables
    private static final Map<String, Map<String, ItemContext>> instances = new HashMap<>();

    //Public static variables
    public static final ItemContext dummy = new ItemContext(ModContext.dummy, null, null);

    //Public variables
    public RegistryObject<Item> rObject;

    //Constructor
    public ItemContext(ModContext context, String name, ItemFactory factory) {
        super(context.getName(), name, "ItemContext");
        if(context.equals(ModContext.dummy) || name == null || factory == null) return;
        if(registerInstance(instances)) context.logger.info("ItemContext", "<init>(context, name, factory)", "Registering item context for item: '" + context.getModID() + ":" + getName() + "'");
        else context.logger.info("ItemContext", "<init>(context, name, factory)", "Registered duplicate item context for item: '" + context.getModID() + ":" + name + "' as '" + context.getModID() + ":" + getName() + "'");
        rObject = context.ITEMS.register(getName(), factory.generate());
    }

    //Getter functions
    public String getItemID() { return getModID() + ":" + getName(); }
    public Item getItem() { return rObject.get(); }

    /* Special getter functions
     * 1-increases in "depth"
     * 2-increases in "type strength"
     * 3-increases in "plurality"
     */
    public static boolean isModKnown(String modID) { return instances.containsKey(modID); }
    public static boolean doesContextExist(String modID, String name) { return isModKnown(modID) ? instances.get(modID).containsKey(name) : false; }
    public static ItemContext getItemContext(String modID, String name) { return isModKnown(modID) ? instances.get(modID).get(name) : null; }
    public static Collection<String> listModItems(String modID) { return isModKnown(modID) ? instances.get(modID).values().stream().map(ItemContext::getItemID).toList() : Collections.emptyList(); }
    public static Collection<ItemContext> getModItems(String modID) { return isModKnown(modID) ? instances.get(modID).values() : Collections.emptyList(); }
    public static Collection<String> listAllItems() { return instances.values().stream().flatMap(modColl -> modColl.values().stream()).map(ItemContext::getItemID).toList(); }
    public static Collection<ItemContext> getAllItems() { return instances.values().stream().flatMap(modColl -> modColl.values().stream()).toList(); }


}
