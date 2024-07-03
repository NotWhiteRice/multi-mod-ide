package io.github.notwhiterice.endlessskies.registries.object;

import io.github.notwhiterice.endlessskies.core.exception.DualRegistryException;
import io.github.notwhiterice.endlessskies.registries.ModRegistry;
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

public class CreativeModeTabContext {
    public static final Map<ResourceKey<CreativeModeTab>, List<RegistryObject<? extends ItemLike>>> vanillaCTabMap = new HashMap<>();
    private static final Map<String, Map<String, CreativeModeTabContext>> instances = new HashMap<>();
    private final String modID;
    private final String name;
    private final List<TabEntryContext<?>> entries = new ArrayList<>();
    private TabIconContext icon;

    private RegistryObject<CreativeModeTab> rObject;

    private boolean isLocked = false;

    public static class TabEntryContext<T> {
        private final T entry;

        public TabEntryContext(T entry) { this.entry=entry; }

        public T getEntry() { return entry; }
    }

    public static class TabIconContext<T> {
        private final T icon;

        public TabIconContext(T icon) { this.icon=icon; }

        public T getIcon() { return icon; }
    }


    public CreativeModeTabContext(String modID, String name) throws DualRegistryException {
        if(doesContextExist(modID, name)) throw DualRegistryException.generate(modID + ":" + name, "CreativeModeTabContext.instances");
        if(!ModContext.instances.containsKey(modID)) throw new IllegalStateException("Attempted to create a ItemContext for an unregistered mod");
        if(ModRegistry.isModLocked(modID)) throw new IllegalStateException("Attempted to make a CreativeModeTabContext for a mod whose registers have already been finalized");
        this.modID = modID;
        this.name = name;

        if(modID == name) {
            if(ItemContext.isModKnown(modID))
                for(ItemContext context : ItemContext.getContextsForMod(modID).values())
                    entries.add(new TabEntryContext<>(context));
            if(BlockContext.isModKnown(modID))
                for(BlockContext context : BlockContext.getContextsForMod(modID).values())
                    entries.add(new TabEntryContext<>(context));
        }

        if(!isModKnown(modID)) instances.put(modID, new HashMap<>());
        instances.get(modID).put(name, this);
    }

    public static boolean isModKnown(String modID) {
        return instances.containsKey(modID);
    }

    public static boolean doesContextExist(String modID, String name) {
        if(!instances.containsKey(modID)) return false;
        return instances.get(modID).containsKey(name);
    }

    public static Map<String, CreativeModeTabContext> getContextsForMod(String modID) {
        if(!isModKnown(modID)) throw new IllegalArgumentException("Attempted to gather CreativeModeTabContexts for a mod without any registered creative mode tabs");
        return instances.get(modID);
    }

    public CreativeModeTabContext addDisplay(ItemLike itemLike) {
        if(modID.equals(name)) throw new IllegalStateException("Attempted to modify entries of a mod-specific creative mode tab");
        if(isLocked) throw new IllegalStateException("Attempted to edit properties of a creative mode tab that has already been locked");
        entries.add(new TabEntryContext<>(itemLike));
        return this;
    }

    public CreativeModeTabContext displayAdd(ItemLikeContext context) {
        if(modID.equals(name)) throw new IllegalStateException("Attempted to modify entries of a mod-specific creative mode tab");
        if(isLocked) throw new IllegalStateException("Attempted to edit properties of a creative mode tab that has already been locked");
        entries.add(new TabEntryContext<>(context));
        return this;
    }

    public CreativeModeTabContext setIcon(ItemLike itemLike) {
        if(isLocked) throw new IllegalStateException("Attempted to edit properties of a creative mode tab that has already been locked");
        icon = new TabIconContext(itemLike);
        return this;
    }

    public CreativeModeTabContext setIcon(ItemLikeContext context) {
        if(isLocked) throw new IllegalStateException("Attempted to edit properties of a creative mode tab that has already been locked");
        icon = new TabIconContext(context);
        return this;
    }

    private CreativeModeTab.Builder generateBuilder() {
        if(icon == null) throw new IllegalStateException("Attempted to build a creative mode tab without a set icon");
        Object iconObj = icon.getIcon();
        if(!(iconObj instanceof ItemLike) && !(iconObj instanceof ItemLikeContext)) throw new IllegalStateException("The icon is somehow of an unsupported type");
        Component title = Component.translatable("creativetab." + modID + (modID == name ? "" : ("." + name)));
        return CreativeModeTab.builder()
                .icon(() -> new ItemStack(iconObj instanceof ItemLike ? (ItemLike) iconObj : iconObj instanceof ItemLikeContext ? ((ItemLikeContext) iconObj).asItemLike() : Items.AIR))
                .title(title)
                .displayItems((param, out) -> {
                    for(TabEntryContext<?> entryObj : entries) {
                        Object object = entryObj.getEntry();
                        if(object instanceof ItemLike) out.accept((ItemLike) object);
                        else if(object instanceof ItemLikeContext) out.accept(((ItemLikeContext) object).asItemLike());
                    }
                });
    }

    public RegistryObject<CreativeModeTab> generateRegistryObject() {
        if(rObject != null) throw new IllegalStateException("Attempted to generate the registry object for a single creative mode tab multiple times");

        RegistryObject<CreativeModeTab> out = ModContext.instances.get(modID).CREATIVE_MODE_TABS
                .register(name, () -> generateBuilder().build());
        rObject = out;
        isLocked = true;
        return out;
    }

    //public Item asItem() { return (Item) rObject.get(); }


    public static void addContextToVanillaTab(ItemLikeContext context, ResourceKey<CreativeModeTab> tabKey) {
        if(!vanillaCTabMap.containsKey(tabKey)) throw new IllegalArgumentException("The given tab is not supported by addContextToVanillaTab");
        vanillaCTabMap.get(tabKey).add(context.asRegistryObject());
    }

    static {
        vanillaCTabMap.put(CreativeModeTabs.BUILDING_BLOCKS, new ArrayList<>());
        vanillaCTabMap.put(CreativeModeTabs.COLORED_BLOCKS, new ArrayList<>());
        vanillaCTabMap.put(CreativeModeTabs.NATURAL_BLOCKS, new ArrayList<>());
        vanillaCTabMap.put(CreativeModeTabs.FUNCTIONAL_BLOCKS, new ArrayList<>());
        vanillaCTabMap.put(CreativeModeTabs.REDSTONE_BLOCKS, new ArrayList<>());
        vanillaCTabMap.put(CreativeModeTabs.TOOLS_AND_UTILITIES, new ArrayList<>());
        vanillaCTabMap.put(CreativeModeTabs.COMBAT, new ArrayList<>());
        vanillaCTabMap.put(CreativeModeTabs.FOOD_AND_DRINKS, new ArrayList<>());
        vanillaCTabMap.put(CreativeModeTabs.INGREDIENTS, new ArrayList<>());
        vanillaCTabMap.put(CreativeModeTabs.SPAWN_EGGS, new ArrayList<>());
        vanillaCTabMap.put(CreativeModeTabs.OP_BLOCKS, new ArrayList<>());
    }
}
