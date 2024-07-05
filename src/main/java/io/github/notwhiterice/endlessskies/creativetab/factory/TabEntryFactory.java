package io.github.notwhiterice.endlessskies.creativetab.factory;

import io.github.notwhiterice.endlessskies.registry.object.ItemLikeContext;
import net.minecraft.world.level.ItemLike;

public class TabEntryFactory<T> {
    private final T entry;
    public TabEntryFactory(T entry) { this.entry=entry; }
    public ItemLike getEntry() {
        if(entry instanceof ItemLike) return (ItemLike) entry;
        if(entry instanceof ItemLikeContext) return ((ItemLikeContext<?>) entry).getItemLike();
        throw new IllegalStateException("Attempted to get the entry from a faulty tab entry context");
    }
}
