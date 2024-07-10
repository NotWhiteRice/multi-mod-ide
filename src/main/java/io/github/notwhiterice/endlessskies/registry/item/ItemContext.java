package io.github.notwhiterice.endlessskies.registry.item;

import io.github.notwhiterice.endlessskies.EndlessSkies;
import io.github.notwhiterice.endlessskies.registry.ModContext;
import io.github.notwhiterice.endlessskies.registry.item.data.ItemModelFactory;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class ItemContext extends ItemLikeContext<Item> {
    public static final List<ItemContext> instances = new ArrayList<>();

    public ItemConstructor<?> itemConstructor = null;
    public Item.Properties customProperties = null;
    public ItemModelFactory modelFactory = ItemModelFactory.ITEM_WITHOUT_MODEL;
    public Supplier<? extends Item> forcedSupplier = null;

    public ItemContext(ModContext parent, String name) {
        super(parent, name);

        if(registerInstance(instances)) parent.logger.info("ItemContext", "<init>", "Registering item '"+getID(":")+"'");
        else parent.logger.warn("ItemContext", "<init>", "Registered duplicate item for '"+getModID()+":"+name+"' as '"+getID(":")+"'");
    }

    public RegistryObject<Item> getRegistry() {
        if(!EndlessSkies.canRegisterObject()) return null;
        if(registry == null) {
            if(forcedSupplier != null) registry = parentMod.ITEMS.register(name, forcedSupplier);
            else {
                if(customProperties == null) customProperties = defaultItemProp;
                customProperties = buildItemProperties(customProperties);
                if(itemConstructor != null) registry = parentMod.ITEMS.register(name, () -> itemConstructor.create(customProperties));
                else registry = parentMod.ITEMS.register(name, () -> new Item(customProperties));
            }
        }
        return registry;
    }

    public static boolean doesContextExist(String modID, String name) { return doesInstanceExist(modID, name, instances); }
    public static ItemContext getContext(String modID, String name) { return getInstance(modID, name, instances); }

    public static List<ItemContext> getModItems(String modID) { return getModInstances(modID, instances); }
}
