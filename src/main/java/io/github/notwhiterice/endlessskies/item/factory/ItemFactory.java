package io.github.notwhiterice.endlessskies.item.factory;

import net.minecraft.world.item.Item;

import java.util.function.Supplier;

public class ItemFactory {
    private Item.Properties iProp;
    private Class<? extends Item> iClass;
    private Supplier<? extends Item> iSupplier;

    public ItemFactory() { iProp = new Item.Properties(); }
    public ItemFactory(Item.Properties prop) { iProp = prop; }
    public ItemFactory(Class<? extends Item> classObj) { iClass = classObj; }
    public ItemFactory(Supplier<? extends Item> supplier) { iSupplier = supplier; }
    public ItemFactory(Item.Properties prop, Class<? extends Item> classObj) {
        iProp = prop;
        iClass = classObj;
    }
    
    public Supplier<? extends Item> generate() {
        if(iSupplier != null) return iSupplier;
        if(iClass != null) {
            if(iProp == null) {
                return () -> {
                    try {
                        return iClass.newInstance();
                    } catch (Exception e) { throw new RuntimeException(e); }
                };
            }
            return () -> {
                try {
                    return iClass.getDeclaredConstructor(Item.Properties.class).newInstance(iProp);
                } catch (Exception e) { throw new RuntimeException(e); }
            };
        }
        return () -> new Item(iProp);
    }
}
