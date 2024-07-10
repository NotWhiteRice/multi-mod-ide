package io.github.notwhiterice.endlessskies.registry.item;

import io.github.notwhiterice.endlessskies.EndlessSkies;
import io.github.notwhiterice.endlessskies.registry.item.data.ItemModelFactory;
import io.github.notwhiterice.endlessskies.registry.ModContext;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class ItemContext extends ItemLikeContext<Item> {
    public static final List<ItemContext> instances = new ArrayList<>();

    private static Item.Properties defProp = new Item.Properties();

    private Class<? extends Item> iClass;
    private Item.Properties prop;
    private ItemModelFactory modelFactory = ItemModelFactory.ITEM_WITHOUT_MODEL;
    private Supplier<? extends Item> forcedSupplier;

    ItemContext(ModContext context, String name) {
        super(context, name, "ItemContext");

        registerInstance(instances);
    }

    protected void setStackSize(int size, String src) { super.setStackSize(size, src); }
    protected void setClass(Class<? extends Item> item, String src) { onEdit(src); iClass = item; }
    protected void forceSupplier(Supplier<? extends Item> supplier, String src) { onEdit(src); forcedSupplier = supplier; }
    public ItemModelFactory getModelFactory() { return modelFactory; }
    protected void setModelFactory(ItemModelFactory factory, String src) { onEdit(src); modelFactory = factory; }

    public RegistryObject<Item> getRegistry() {
        if(!EndlessSkies.canRegisterObject()) return null;
        if(registry == null) {
            if (forcedSupplier != null) registry = ModContext.getContext(getModID()).ITEMS.register(name, forcedSupplier);
            else {
                if (prop == null) prop = defProp;
                if (stackSize != 64) prop = prop.stacksTo(stackSize);

                if (iClass != null) {
                    registry = ModContext.getContext(getModID()).ITEMS.register(name, () -> {
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
                    registry = ModContext.getContext(getModID()).ITEMS.register(name, () -> new Item(new Item.Properties().stacksTo(stackSize)));
            }
        }
        return registry;
    }

    public static boolean doesContextExist(String modID, String name) { return doesInstanceExist(modID, name, instances); }
    public static ItemContext getContext(String modID, String name) { return getInstance(modID, name, instances); }


    public static List<ItemContext> getModItems(String modID) { return getModInstances(modID, instances); }
}
