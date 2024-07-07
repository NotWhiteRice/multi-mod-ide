package io.github.notwhiterice.endlessskies.registry.object;

import io.github.notwhiterice.endlessskies.creativetab.factory.CreativeModeTabContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

import java.util.List;
import java.util.Map;

public abstract class ItemLikeContext<T extends ItemLikeContext<T, P>, P extends ItemLike> extends InnerContextv2<T, P> {
    protected int stackSize = 64;

    protected ItemLikeContext(ModContext context, String name, String type, Map<String, List<T>> instances) {
        super(context, name, type);
        registerInstance(instances);
    }

    public ItemStack getItemStack() { return new ItemStack(rObject.get()); }
    protected void setStackSize(int size, String src) { onEdit(src); stackSize = size; }

    public T setCreativeTab(ResourceKey<CreativeModeTab> tabKey) {
        CreativeModeTabContext.submitEntry(tabKey, this);
        return (T) this;
    }
    public T setCreativeTab(CreativeModeTabContext tab) {
        tab.addEntry(this);
        return (T) this;
    }
}
