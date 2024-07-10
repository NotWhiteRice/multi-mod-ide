package io.github.notwhiterice.endlessskies.registry.item;

import io.github.notwhiterice.endlessskies.creativetab.factory.CreativeModeTabContext;
import io.github.notwhiterice.endlessskies.registry.ModContext;
import io.github.notwhiterice.endlessskies.registry.item.data.ItemModelFactory;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.flag.FeatureFlag;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.JukeboxSong;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.component.ItemAttributeModifiers;

import java.util.function.Supplier;

public class ItemFactory {
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
    public ItemFactory setParent(ItemConstructor<? extends Item> item) {
        context.itemConstructor = item;
        return this;
    }
    public ItemFactory forceSupplier(Supplier<? extends Item> item) {
        context.forcedSupplier = item;
        return this;
    }
    public ItemFactory setModelFactory(ItemModelFactory model) {
        context.modelFactory = model;
        return this;
    }
    public ItemFactory setName(String name) { return this.applyTranslation(name, "en_us"); }
    public ItemFactory applyTranslation(String name, String locale) {
        context.applyTranslation(name, locale);
        return this;
    }
    public ItemFactory setStackSize(int size) {
        context.stackSize = size;
        return this;
    }
    public ItemFactory setDurability(int durability) {
        context.durability = durability;
        return this;
    }

    public ItemFactory setRarity(Rarity rarity) {
        context.rarity = rarity;
        return this;
    }

    public ItemFactory setFireResistant(boolean resistant) {
        context.isFireResistant = resistant;
        return this;
    }

    public ItemFactory setJukeboxSong(ResourceKey<JukeboxSong> song) {
        context.jukeboxSong = song;
        return this;
    }

    public ItemFactory setRequiredFeatures(FeatureFlag... features) {
        context.requiredFeatures = features;
        return this;
    }

    public ItemFactory setAttributes(ItemAttributeModifiers attributes) {
        context.attributes = attributes;
        return this;
    }

    public ItemFactory setCreativeTab(ResourceKey<CreativeModeTab> tab) {
        context.setCreativeTab(tab);
        return this;
    }

    public ItemFactory setCreativeTab(CreativeModeTabContext tab) {
        context.setCreativeTab(tab);
        return this;
    }

    public ItemContext close() {
        ItemContext out = context;
        context = null;
        return out;
    }
}
