package io.github.notwhiterice.endlessskies.creativetab.factory;

import io.github.notwhiterice.endlessskies.registry.block.BlockContext;
import io.github.notwhiterice.endlessskies.registry.item.ItemLikeContext;
import net.minecraft.world.level.ItemLike;

public class TabEntryFactory<T> {
    private final T entry;
    public TabEntryFactory(T entry) {
        this.entry=entry;
        if(entry instanceof BlockContext) if(!((BlockContext) entry).hasItem())
            throw new IllegalStateException("Attempted to make a tab entry for a block without an item");
    }
    public ItemLike getEntry() {
        if(entry instanceof ItemLike) return (ItemLike) entry;
        if(entry instanceof ItemLikeContext<?>) return ((ItemLikeContext<?>) entry).getItemStack().getItem();
        throw new IllegalStateException("Attempted to get the entry from a faulty tab entry context");
    }
}
