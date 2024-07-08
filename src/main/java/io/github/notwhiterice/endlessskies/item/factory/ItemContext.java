package io.github.notwhiterice.endlessskies.item.factory;

import io.github.notwhiterice.endlessskies.EndlessSkies;
import io.github.notwhiterice.endlessskies.item.factory.data.ItemModelFactory;
import io.github.notwhiterice.endlessskies.registry.object.ItemLikeContext;
import io.github.notwhiterice.endlessskies.registry.object.ModContext;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;

import java.util.*;
import java.util.function.Supplier;

public class ItemContext extends ItemLikeContext<ItemContext, Item> {
    private static final Map<String, List<ItemContext>> instances = new HashMap<>();

    private static Item.Properties defProp = new Item.Properties();

    private Class<? extends Item> iClass;
    private Item.Properties prop;
    private ItemModelFactory modelFactory = ItemModelFactory.ITEM_WITHOUT_MODEL;
    private Supplier<? extends Item> forcedSupplier;

    ItemContext(ModContext context, String name) { super(context, name, "ItemContext", instances); }

    protected void setStackSize(int size, String src) { super.setStackSize(size, src); }
    protected void setClass(Class<? extends Item> item, String src) { onEdit(src); iClass = item; }
    protected void forceSupplier(Supplier<? extends Item> supplier, String src) { onEdit(src); forcedSupplier = supplier; }
    public ItemModelFactory getModelFactory() { return modelFactory; }
    protected void setModelFactory(ItemModelFactory factory, String src) { onEdit(src); modelFactory = factory; }

    public Item get() {
        if(rObject == null) throw new IllegalStateException(".get was called for a ItemContext before its registry object is registered");
        return rObject.get();
    }

    public RegistryObject<Item> getRegistryObject() {
        if(!EndlessSkies.canRegisterObject()) return null;
        if(rObject == null) {
            if (forcedSupplier != null) rObject = modContext.ITEMS.register(name, forcedSupplier);
            else {
                if (prop == null) prop = defProp;
                if (stackSize != 64) prop = prop.stacksTo(stackSize);

                if (iClass != null) {
                    rObject = modContext.ITEMS.register(name, () -> {
                        try {
                            if (prop == defProp)
                                return iClass.newInstance();
                            else
                                return iClass.getDeclaredConstructor(Item.Properties.class)
                                        .newInstance(prop);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    });
                } else
                    rObject = modContext.ITEMS.register(name, () -> new Item(new Item.Properties().stacksTo(stackSize)));
            }
        }
        return rObject;
    }

    public static boolean isModKnown(String modID) { return instances.containsKey(modID); }
    public static boolean doesContextExist(String modID, String name) { return isModKnown(modID) ? (!instances.get(modID).stream().filter(v -> v.getName().equals(name)).toList().isEmpty()) : false; }
    public static ItemContext getContext(String modID, String name) { return isModKnown(modID) ? (instances.get(modID).stream().filter(v -> v.getName().equals(name)).toList().get(0)) : null; }
    public static Collection<String> listModItems(String modID) { return isModKnown(modID) ? instances.get(modID).stream().map(ItemContext::getID).toList() : Collections.emptyList(); }
    public static Collection<ItemContext> getModItems(String modID) { return isModKnown(modID) ? instances.get(modID) : Collections.emptyList(); }
    public static Collection<String> listAllItems() { return instances.values().stream().flatMap(modColl -> modColl.stream()).map(ItemContext::getID).toList(); }
    public static Collection<ItemContext> getAllItems() { return instances.values().stream().flatMap(Collection::stream).toList(); }
}
