package io.github.deprecated.v3.endlessskies.registry.object;

import io.github.deprecated.v3.endlessskies.core.exception.DualRegistryException;
import io.github.deprecated.v3.endlessskies.datagen.tag.ItemModelProviderTag;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class ItemContext extends ItemLikeContext<ItemContext> {
    private static final Map<String, Map<String, ItemContext>> instances = new HashMap<>();
    public static final ItemContext dummy = new ItemContext();
    private RegistryObject<Item> rObject;

    private ItemModelProviderTag modelProviderTag = ItemModelProviderTag.ITEM_WITHOUT_MODEL;

    public ItemContext() {}
    public ItemContext(ModContext context, String name, Item.Properties prop) throws DualRegistryException, IllegalAccessException {
        super(context, name, "BlockContext");
        rObject = context.ITEMS.register(name, () -> new Item(prop));
        registerInstance();
    }
    public ItemContext(ModContext context, String name, Supplier<? extends Item> supplier) throws DualRegistryException, IllegalAccessException {
        super(context, name, "BlockContext");
        rObject = context.ITEMS.register(name, supplier);
        registerInstance();
    }
    public ItemContext(ModContext context, String name, Class<? extends Item> iClass) throws DualRegistryException, IllegalAccessException {
        super(context, name, "BlockContext");
        rObject = context.ITEMS.register(name, () -> {
            try {
                return iClass.newInstance();
            } catch(Exception e) {
                throw new RuntimeException(e);
            }
        });
        registerInstance();
    }

    @Override
    public Map<String, Map<String, ItemContext>> getInstanceMap() { return instances; }

    @Override
    protected ItemContext getReference() { return this; }

    public boolean isModKnown(String modID) { return instances.containsKey(modID); }

    public boolean doesContextExist(String modID, String name) {
        if(!instances.containsKey(modID)) return false;
        return instances.get(modID).containsKey(name);
    }

    public List<ItemContext> getContextsForMod(String modID) {
        if(!isModKnown(modID)) return new ArrayList<>();
        return instances.get(modID).values().stream().toList();
    }

    public ItemContext getContext(String modID, String name) {
        if(!instances.containsKey(modID)) return null;
        return instances.get(modID).get(name);
    }

    public Item asItem() { return rObject.get(); }

    public ItemModelProviderTag getModelProviderTag() { return modelProviderTag; }

    public ItemContext setModelProviderTag(ItemModelProviderTag tag) {
        modelProviderTag = tag;
        return this;
    }
}
