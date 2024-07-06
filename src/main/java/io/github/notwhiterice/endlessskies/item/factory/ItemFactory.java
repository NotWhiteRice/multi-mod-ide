package io.github.notwhiterice.endlessskies.item.factory;

import io.github.notwhiterice.endlessskies.datagen.ModLanguageProvider;
import io.github.notwhiterice.endlessskies.item.factory.data.ItemModelFactory;
import io.github.notwhiterice.endlessskies.registry.object.ModContext;
import net.minecraft.world.item.Item;

import java.util.*;
import java.util.function.Supplier;

public class ItemFactory {
    public static Item.Properties defProp = new Item.Properties();

    private ModContext parentMod;
    private ItemContext context;

    private ItemFactory(ModContext context) { this.parentMod = context; }
    private ItemFactory(ItemContext context, ModContext parent) { this(parent); this.context = context; }

    public static ItemFactory init(ModContext modContext) {
        return new ItemFactory(modContext);
    }
    public ItemFactory item(String name) {
        return new ItemFactory(new ItemContext(parentMod, name), parentMod);
    }
    public ItemFactory setParent(Class<? extends Item> item) {
        context.setClass(item, "ItemFactory.setParent");
        return this;
    }
    public ItemFactory forceSupplier(Supplier<? extends Item> supplier) {
        context.forceSupplier(supplier, "ItemFactory.forceSupplier");
        return this;
    }
    public ItemFactory setModelFactory(ItemModelFactory factory) {
        context.setModelFactory(factory, "ItemFactory.setModelFactory");
        return this;
    }
    public ItemFactory setName(String name) { return this.applyTranslation(name, "en_us"); }
    public ItemFactory applyTranslation(String name, String locale) {
        ModLanguageProvider.translations.computeIfAbsent(locale, v -> new HashMap<>());
        ModLanguageProvider.translations.get(locale)
                .computeIfAbsent(parentMod.getModID(), v -> new HashMap<>());
        ModLanguageProvider.translations.get(locale)
                .get(parentMod.getModID()).computeIfAbsent(context.getName(), v -> name);
        return this;
    }
    public ItemFactory setStackSize(int size) {
        context.setStackSize(size, "ItemFactory.setStackSize");
        return this;
    }

    public ItemContext close() {
        context.close();
        ItemContext out = context;
        context = null;
        return out;
    }
}
