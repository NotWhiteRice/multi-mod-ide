package io.github.notwhiterice.endlessskies.registry.item;

import io.github.notwhiterice.endlessskies.creativetab.factory.CreativeModeTabContext;
import io.github.notwhiterice.endlessskies.datagen.ModLanguageProvider;
import io.github.notwhiterice.endlessskies.registry.ModContext;
import io.github.notwhiterice.endlessskies.registry.ModObject;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.flag.FeatureFlag;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.ItemLike;

import java.util.HashMap;

public abstract class ItemLikeContext<T extends ItemLike> extends ModObject<T> {
    public final Item.Properties defaultItemProp = new Item.Properties();

    public int stackSize = 64;
    public int durability = 0;
    public Rarity rarity = Rarity.COMMON;
    public boolean isFireResistant = false;
    public ResourceKey<JukeboxSong> jukeboxSong = null;
    public FeatureFlag[] requiredFeatures = null;
    public ItemAttributeModifiers attributes = null;

    public ItemLikeContext(ModContext parent, String name) {
        super(parent, name);
    }

    public ItemStack getItemStack() { return isRegistered() ? new ItemStack(get()) : ItemStack.EMPTY; }

    public Item.Properties buildItemProperties(Item.Properties builder) {
        if(stackSize != 64) builder.stacksTo(stackSize);
        if(durability != 0) builder.durability(durability);
        if(rarity != Rarity.COMMON) builder.rarity(rarity);
        if(isFireResistant) builder.fireResistant();
        if(jukeboxSong != null) builder.jukeboxPlayable(jukeboxSong);
        if(requiredFeatures != null) builder.requiredFeatures(requiredFeatures);
        if(attributes != null) builder.attributes(attributes);
        return builder;
    }

    public void setName(String name) { this.applyTranslation(name, "en_us"); }
    public void applyTranslation(String name, String locale) {
        ModLanguageProvider.translations.computeIfAbsent(locale, v -> new HashMap<>());
        ModLanguageProvider.translations.get(locale)
                .computeIfAbsent(parentMod.modID, v -> new HashMap<>());
        ModLanguageProvider.translations.get(locale)
                .get(parentMod.modID).computeIfAbsent(this.name, v -> name);
    }

    public <T extends ItemLikeContext> T setCreativeTab(ResourceKey<CreativeModeTab> tabKey) {
        CreativeModeTabContext.submitEntry(tabKey, this);
        return (T) this;
    }
    public <T extends ItemLikeContext> T setCreativeTab(CreativeModeTabContext tab) {
        tab.addEntry(this);
        return (T) this;
    }
}
