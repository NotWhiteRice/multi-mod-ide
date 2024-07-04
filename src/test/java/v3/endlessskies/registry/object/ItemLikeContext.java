package io.github.deprecated.v3.endlessskies.registry.object;

import io.github.deprecated.v3.endlessskies.registry.object.base.InnerContextBase;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.RegistryObject;

public abstract class ItemLikeContext<T extends ItemLikeContext<T>> extends InnerContextBase<T> {
    private RegistryObject<? extends ItemLike> rObject;

    public ItemLikeContext() { super(); }
    public ItemLikeContext(ModContext context, String name, String parent) { super(context, name, parent); }

    public ItemLike asItemLike() { return rObject.get(); }
    public RegistryObject<? extends ItemLike> asRegistryObject() { return rObject; }

    public T setCreativeTab(ResourceKey<CreativeModeTab> tabKey) {
        CreativeModeTabContext.submitEntry(tabKey, this);
        return (T) this;
    }
    public T setCreativeTab(CreativeModeTabContext tab) {
        tab.addEntry(this);
        return (T) this;
    }
}
