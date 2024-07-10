package io.github.notwhiterice.endlessskies.registry.item;

import io.github.notwhiterice.endlessskies.core.exception.ClosedContextException;
import io.github.notwhiterice.endlessskies.creativetab.factory.CreativeModeTabContext;
import io.github.notwhiterice.endlessskies.registry.ModContext;
import io.github.notwhiterice.endlessskies.registry.ModObject;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

public abstract class ItemLikeContext<P extends ItemLike> extends ModObject<P> {
    protected int stackSize = 64;

    protected ItemLikeContext(ModContext context, String name, String type) {
        super(context, name);
    }

    public ItemStack getItemStack() { return new ItemStack(registry.get()); }

    @Deprecated(forRemoval = true)
    protected void onEdit(String src) { if(isRegistered()) throw new ClosedContextException(src, "temp"); }

    protected void setStackSize(int size, String src) { onEdit(src); stackSize = size; }

    public <T extends ItemLikeContext> T setCreativeTab(ResourceKey<CreativeModeTab> tabKey) {
        CreativeModeTabContext.submitEntry(tabKey, this);
        return (T) this;
    }
    public <T extends ItemLikeContext> T setCreativeTab(CreativeModeTabContext tab) {
        tab.addEntry(this);
        return (T) this;
    }
}
