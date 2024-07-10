package io.github.notwhiterice.endlessskies.creativetab.factory;

import io.github.notwhiterice.endlessskies.registry.block.BlockContext;
import io.github.notwhiterice.endlessskies.datagen.ModLanguageProvider;
import io.github.notwhiterice.endlessskies.registry.item.ItemContext;
import io.github.notwhiterice.endlessskies.registry.ModObject;
import io.github.notwhiterice.endlessskies.registry.item.ItemLikeContext;
import io.github.notwhiterice.endlessskies.registry.ModContext;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.RegistryObject;

import java.util.*;

public class CreativeModeTabContext extends ModObject<CreativeModeTab> {
    public static final List<CreativeModeTabContext> instances = new ArrayList<>();

    public static final Map<ResourceKey<CreativeModeTab>, List<TabEntryFactory<?>>> customVanillaEntries = new HashMap<>();

    private final List<TabEntryFactory<?>> entries = new ArrayList<>();
    private TabEntryFactory<?> icon;

    public CreativeModeTabContext(ModContext context, String name) {
        super(context, name);
        if(context == null || name == null) return;
        registerInstance(instances);
        if(isModSpecificTab()) {
            for(BlockContext bContext : BlockContext.getModBlocks(context.modID))
                if(bContext.hasItem()) entries.add(new TabEntryFactory<>(bContext));
            for(ItemContext iContext : ItemContext.getModItems(context.modID))
                entries.add(new TabEntryFactory<>(iContext));
        }
    }

    public String getID() { return getModID() + (isModSpecificTab() ? "" : (":" + name)); }
    public CreativeModeTab get() { return super.get(); }
    public RegistryObject<CreativeModeTab> getRegistry() {
        if(!isRegistered()) registry = ModContext.getContext(getModID()).CREATIVE_MODE_TABS
                .register(name, () -> generateBuilder().build());
        return registry;
    }

    public boolean isModSpecificTab() { return getModID().equals(name); }

    public CreativeModeTab.Builder generateBuilder() {
        Component title = Component.translatable("creativetab." + getID().replaceAll(":", "."));
        return CreativeModeTab.builder()
                .icon(() -> new ItemStack(icon == null ? Items.AIR : icon.getEntry()))
                .title(title)
                .displayItems((param, out) -> {
                    for(TabEntryFactory<?> entry : entries)
                        out.accept(entry.getEntry());
                });
    }

    public CreativeModeTabContext addEntry(ItemLike itemLike) {
        if(isRegistered()) throw new IllegalStateException("Attempted to add an entry to a tab that has already been generated");
        if(isModSpecificTab()) throw new IllegalStateException("Attempted to add an entry to a mod-specific tab");
        entries.add(new TabEntryFactory<>(itemLike));
        return this;
    }
    public CreativeModeTabContext addEntry(ItemLikeContext context) {
        if(isRegistered()) throw new IllegalStateException("Attempted to add an entry to a tab that has already been generated");
        if(isModSpecificTab()) throw new IllegalStateException("Attempted to add an entry to a mod-specific tab");
        entries.add(new TabEntryFactory<>(context));
        return this;
    }

    public CreativeModeTabContext setIcon(ItemLike itemLike) {
        if(isRegistered()) throw new IllegalStateException("Attempted to set the icon for a tab that has already been generated");
        if(icon != null) throw new IllegalStateException("Attempted to set the icon for a creative tab whose icon had already been set");
        icon = new TabEntryFactory<>(itemLike);
        return this;
    }
    public CreativeModeTabContext setIcon(ItemLikeContext context) {
        if(isRegistered()) throw new IllegalStateException("Attempted to set the icon for a tab that has already been generated");
        if(icon != null) throw new IllegalStateException("Attempted to set the icon for a creative tab whose icon had already been set");
        icon = new TabEntryFactory<>(context);
        return this;
    }

    public CreativeModeTabContext setName(String name) { return applyTranslation(name, "en_us"); }
    public CreativeModeTabContext applyTranslation(String name, String locale) {
        ModLanguageProvider.translations.computeIfAbsent(locale, v -> new HashMap<>());
        ModLanguageProvider.translations.get(locale)
                .computeIfAbsent("creativetab.", v -> new HashMap<>());
        ModLanguageProvider.translations.get(locale)
                .get("creativetab.").computeIfAbsent(getID().replaceAll(":", "."), v -> name);
        return this;
    }


    public static <T extends ItemLike> void submitEntry(ResourceKey<CreativeModeTab> tab, T entry) { customVanillaEntries.get(tab).add(new TabEntryFactory<>(entry)); }
    public static <T extends ItemLikeContext<?>> void submitEntry(ResourceKey<CreativeModeTab> tab, T entry) { customVanillaEntries.get(tab).add(new TabEntryFactory<>(entry)); }
    public static <T extends ItemLike> void submitEntry(CreativeModeTabContext tab, T entry) { tab.addEntry(entry); }
    public static <T extends ItemLikeContext<?>> void submitEntry(CreativeModeTabContext tab, T entry) { tab.addEntry(entry); }

    static {
        customVanillaEntries.put(CreativeModeTabs.BUILDING_BLOCKS, new ArrayList<>());
        customVanillaEntries.put(CreativeModeTabs.COLORED_BLOCKS, new ArrayList<>());
        customVanillaEntries.put(CreativeModeTabs.NATURAL_BLOCKS, new ArrayList<>());
        customVanillaEntries.put(CreativeModeTabs.FUNCTIONAL_BLOCKS, new ArrayList<>());
        customVanillaEntries.put(CreativeModeTabs.REDSTONE_BLOCKS, new ArrayList<>());
        customVanillaEntries.put(CreativeModeTabs.TOOLS_AND_UTILITIES, new ArrayList<>());
        customVanillaEntries.put(CreativeModeTabs.COMBAT, new ArrayList<>());
        customVanillaEntries.put(CreativeModeTabs.FOOD_AND_DRINKS, new ArrayList<>());
        customVanillaEntries.put(CreativeModeTabs.INGREDIENTS, new ArrayList<>());
        customVanillaEntries.put(CreativeModeTabs.SPAWN_EGGS, new ArrayList<>());
        customVanillaEntries.put(CreativeModeTabs.OP_BLOCKS, new ArrayList<>());
    }
}
