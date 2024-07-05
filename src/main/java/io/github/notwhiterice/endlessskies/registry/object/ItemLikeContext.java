package io.github.notwhiterice.endlessskies.registry.object;

import io.github.notwhiterice.endlessskies.registry.object.base.InnerContextBase;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.RegistryObject;

public abstract class ItemLikeContext<T extends ItemLikeContext<T>> extends InnerContextBase<T> {
    public RegistryObject<? extends ItemLike> rObject;

    public ItemLikeContext(String modID, String name, String parent) { super(modID, name, parent); }

    public ItemLike getItemLike() { return rObject.get(); }

    public T setCreativeTab(ResourceKey<CreativeModeTab> tabKey) {
        //CreativeModeTabContext.submitEntry(tabKey, this);
        return (T) this;
    }
    public T setCreativeTab(/* CreativeModeTabContext tab */) {
        //tab.addEntry(this);
        return (T) this;
    }
}
