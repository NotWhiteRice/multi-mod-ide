package io.github.notwhiterice.endlessskies.registry.object;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class ItemLikeContextv2<T extends ItemLikeContextv2<T, P>, P extends ItemLike> extends InnerContextv2<T, P> {
    protected int stackSize;

    protected ItemLikeContextv2(ModContext context, String name, String type, Map<String, List<T>> instances) {
        super(context, name, type);
        registerInstance(instances);
    }

    public ItemStack getItemStack() { return new ItemStack(rObject.get()); }
    protected void setStackSize(int size, String src) { onEdit(src); stackSize = size; }
}
