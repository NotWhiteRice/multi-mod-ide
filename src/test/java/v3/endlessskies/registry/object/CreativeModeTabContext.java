package io.github.deprecated.v3.endlessskies.registry.object;

import io.github.notwhiterice.endlessskies.core.exception.DualRegistryException;
import io.github.deprecated.v3.endlessskies.registry.ModRegistry;
import io.github.deprecated.v3.endlessskies.registry.object.base.InnerContextBase;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreativeModeTabContext extends InnerContextBase<CreativeModeTabContext> {
    public static final Map<ResourceKey<CreativeModeTab>, List<TabEntryContext<?>>> vanillaEntries = new HashMap<>();
    private static final Map<String, Map<String, CreativeModeTabContext>> instances = new HashMap<>();
    public static final CreativeModeTabContext dummy = new CreativeModeTabContext();
    private final List<TabEntryContext<?>> entries = new ArrayList<>();
    private TabEntryContext<?> icon = null;

    private RegistryObject<CreativeModeTab> rObject = null;

    public CreativeModeTabContext() {}
    public CreativeModeTabContext(ModContext context, String name) throws DualRegistryException, IllegalAccessException {
        super(context, name, "CreativeModeTabContext");
        registerInstance();
        if(isModSpecificTab()) {
            for(ItemContext iContext : ModRegistry.getModItems(getModID()))
                entries.add(new TabEntryContext<>(iContext));
            for(BlockContext bContext : ModRegistry.getModBlocks(getModID()))
                entries.add(new TabEntryContext<>(bContext));
        }
    }

    @Override
    public Map<String, Map<String, CreativeModeTabContext>> getInstanceMap() { return instances; }

    @Override
    protected CreativeModeTabContext getReference() { return this; }

    public boolean isModKnown(String modID) { return instances.containsKey(modID); }

    public boolean doesContextExist(String modID, String name) {
        if(!instances.containsKey(modID)) return false;
        return instances.get(modID).containsKey(name);
    }

    public List<CreativeModeTabContext> getContextsForMod(String modID) {
        if(!isModKnown(modID)) return new ArrayList<>();
        return instances.get(modID).values().stream().toList();
    }

    public CreativeModeTabContext getContext(String modID, String name) {
        if(!instances.containsKey(modID)) return null;
        return instances.get(modID).get(name);
    }

    public boolean isModSpecificTab() { return getModID() == getName(); }
    public boolean isLocked() { return rObject != null; }

    public CreativeModeTabContext addEntry(ItemLike itemLike) {
        if(isLocked()) throw new IllegalStateException("Attempted to add an entry to a tab that has already been generated");
        if(isModSpecificTab()) throw new IllegalStateException("Attempted to add an entry to a mod-specific tab");
        entries.add(new TabEntryContext<>(itemLike));
        return this;
    }
    public CreativeModeTabContext addEntry(ItemLikeContext context) {
        if(isLocked()) throw new IllegalStateException("Attempted to add an entry to a tab that has already been generated");
        if(isModSpecificTab()) throw new IllegalStateException("Attempted to add an entry to a mod-specific tab");
        entries.add(new TabEntryContext<>(context));
        return this;
    }

    public CreativeModeTabContext setIcon(ItemLike itemLike) {
        if(isLocked()) throw new IllegalStateException("Attempted to set the icon for a tab that has already been generated");
        if(icon != null) throw new IllegalStateException("Attempted to set the icon for a creative tab whose icon had already been set");
        icon = new TabEntryContext<>(itemLike);
        return this;
    }
    public CreativeModeTabContext setIcon(ItemLikeContext context) {
        if(isLocked()) throw new IllegalStateException("Attempted to set the icon for a tab that has already been generated");
        if(icon != null) throw new IllegalStateException("Attempted to set the icon for a creative tab whose icon had already been set");
        icon = new TabEntryContext<>(context);
        return this;
    }

    private CreativeModeTab.Builder generateBuilder() {
        Component title = Component.translatable("creativetab." + getModID() + (isModSpecificTab() ? "" : ("." + getName())));
        return CreativeModeTab.builder()
                .icon(() -> new ItemStack(icon == null ? Items.AIR : icon.getEntry()))
                .title(title)
                .displayItems((param, out) -> {
                    for(TabEntryContext<?> entry : entries)
                        out.accept(entry.getEntry());
                });
    }

    public RegistryObject<CreativeModeTab> generateRegistryObject(ModContext context) {
        if(isLocked()) throw new IllegalStateException("Attempted to generate the registry object for a tab that has already been generated");
        rObject = context.CREATIVE_MODE_TABS
                .register(getName(), () -> generateBuilder().build());
        return rObject;
    }

    public static <T extends ItemLike> void submitEntry(ResourceKey<CreativeModeTab> tab, T entry) { vanillaEntries.get(tab).add(new TabEntryContext<>(entry)); }
    public static <T extends ItemLikeContext<?>> void submitEntry(ResourceKey<CreativeModeTab> tab, T entry) { vanillaEntries.get(tab).add(new TabEntryContext<>(entry)); }
    public static <T extends ItemLike> void submitEntry(CreativeModeTabContext tab, T entry) { tab.addEntry(entry); }
    public static <T extends ItemLikeContext<?>> void submitEntry(CreativeModeTabContext tab, T entry) { tab.addEntry(entry); }

    public static class TabEntryContext<T> {
        private final T entry;
        public TabEntryContext(T entry) { this.entry=entry; }
        public ItemLike getEntry() {
            if(entry instanceof ItemLike) return (ItemLike) entry;
            if(entry instanceof ItemLikeContext) return ((ItemLikeContext<?>) entry).asItemLike();
            throw new IllegalStateException("A tab entry that isn't a vanilla or modded block or item had somehow been registered");
        }
    }

    static {
        vanillaEntries.put(CreativeModeTabs.BUILDING_BLOCKS, new ArrayList<>());
        vanillaEntries.put(CreativeModeTabs.COLORED_BLOCKS, new ArrayList<>());
        vanillaEntries.put(CreativeModeTabs.NATURAL_BLOCKS, new ArrayList<>());
        vanillaEntries.put(CreativeModeTabs.FUNCTIONAL_BLOCKS, new ArrayList<>());
        vanillaEntries.put(CreativeModeTabs.REDSTONE_BLOCKS, new ArrayList<>());
        vanillaEntries.put(CreativeModeTabs.TOOLS_AND_UTILITIES, new ArrayList<>());
        vanillaEntries.put(CreativeModeTabs.COMBAT, new ArrayList<>());
        vanillaEntries.put(CreativeModeTabs.FOOD_AND_DRINKS, new ArrayList<>());
        vanillaEntries.put(CreativeModeTabs.INGREDIENTS, new ArrayList<>());
        vanillaEntries.put(CreativeModeTabs.SPAWN_EGGS, new ArrayList<>());
        vanillaEntries.put(CreativeModeTabs.OP_BLOCKS, new ArrayList<>());
    }
}
