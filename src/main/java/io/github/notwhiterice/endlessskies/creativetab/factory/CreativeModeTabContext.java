package io.github.notwhiterice.endlessskies.creativetab.factory;

import io.github.notwhiterice.endlessskies.block.factory.BlockContext;
import io.github.notwhiterice.endlessskies.datagen.ModLanguageProvider;
import io.github.notwhiterice.endlessskies.item.factory.ItemContext;
import io.github.notwhiterice.endlessskies.registry.object.ItemLikeContext;
import io.github.notwhiterice.endlessskies.registry.object.ModContext;
import io.github.notwhiterice.endlessskies.registry.object.base.InnerContextBase;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.RegistryObject;

import java.util.*;

public class CreativeModeTabContext extends InnerContextBase<CreativeModeTabContext> {
    private static final Map<String, Map<String, CreativeModeTabContext>> instances = new HashMap<>();

    public static final Map<ResourceKey<CreativeModeTab>, List<TabEntryFactory<?>>> customVanillaEntries = new HashMap<>();
    public static final CreativeModeTabContext dummy = new CreativeModeTabContext(ModContext.dummy, null);

    private final List<TabEntryFactory<?>> entries = new ArrayList<>();
    private RegistryObject<CreativeModeTab> rObject;
    private TabEntryFactory<?> icon;

    public CreativeModeTabContext(ModContext context, String name) {
        super(context.getModID(), name, "CreativeModeTabContext");
        if(context.equals(ModContext.dummy) || name == null) return;
        registerInstance(instances);
        if(isModSpecificTab()) {
            for(BlockContext bContext : BlockContext.getModBlocks(context.getModID()))
                entries.add(new TabEntryFactory<>(bContext));
            for(ItemContext iContext : ItemContext.getModItems(context.getModID()))
                entries.add(new TabEntryFactory<>(iContext));
        }
    }

    public String getID() { return getModID() + (isModSpecificTab() ? "" : (":" + getName())); }
    public CreativeModeTab getTab() { return rObject.get(); }
    public RegistryObject<CreativeModeTab> getRegistryObject() {
        if(!isLocked()) rObject = ModContext.getContext(getModID()).CREATIVE_MODE_TABS
                .register(getName(), () -> generateBuilder().build());
        return rObject;
    }

    public static boolean isModKnown(String modID) { return instances.containsKey(modID); }
    public static boolean doesContextExist(String modID, String name) { return isModKnown(modID) ? instances.get(modID).containsKey(name) : false; }
    public static CreativeModeTabContext getContext(String modID, String name) { return isModKnown(modID) ? instances.get(modID).get(name) : null; }
    public static Collection<String> listModTabs(String modID) { return isModKnown(modID) ? instances.get(modID).values().stream().map(CreativeModeTabContext::getID).toList() : Collections.emptyList(); }
    public static Collection<CreativeModeTabContext> getModTabs(String modID) { return isModKnown(modID) ? instances.get(modID).values() : Collections.emptyList(); }
    public static Collection<String> listAllTabs() { return instances.values().stream().flatMap(modColl -> modColl.values().stream()).map(CreativeModeTabContext::getID).toList(); }
    public static Collection<CreativeModeTabContext> getAllTabs() { return instances.values().stream().flatMap(modColl -> modColl.values().stream()).toList(); }

    public boolean isModSpecificTab() { return getModID().equals(getName()); }
    public boolean isLocked() { return rObject != null; }

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
        if(isLocked()) throw new IllegalStateException("Attempted to add an entry to a tab that has already been generated");
        if(isModSpecificTab()) throw new IllegalStateException("Attempted to add an entry to a mod-specific tab");
        entries.add(new TabEntryFactory<>(itemLike));
        return this;
    }
    public CreativeModeTabContext addEntry(ItemLikeContext context) {
        if(isLocked()) throw new IllegalStateException("Attempted to add an entry to a tab that has already been generated");
        if(isModSpecificTab()) throw new IllegalStateException("Attempted to add an entry to a mod-specific tab");
        entries.add(new TabEntryFactory<>(context));
        return this;
    }

    public CreativeModeTabContext setIcon(ItemLike itemLike) {
        if(isLocked()) throw new IllegalStateException("Attempted to set the icon for a tab that has already been generated");
        if(icon != null) throw new IllegalStateException("Attempted to set the icon for a creative tab whose icon had already been set");
        icon = new TabEntryFactory<>(itemLike);
        return this;
    }
    public CreativeModeTabContext setIcon(ItemLikeContext context) {
        if(isLocked()) throw new IllegalStateException("Attempted to set the icon for a tab that has already been generated");
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
    public static <T extends ItemLikeContext<?, ?>> void submitEntry(ResourceKey<CreativeModeTab> tab, T entry) { customVanillaEntries.get(tab).add(new TabEntryFactory<>(entry)); }
    public static <T extends ItemLike> void submitEntry(CreativeModeTabContext tab, T entry) { tab.addEntry(entry); }
    public static <T extends ItemLikeContext<?, ?>> void submitEntry(CreativeModeTabContext tab, T entry) { tab.addEntry(entry); }

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
