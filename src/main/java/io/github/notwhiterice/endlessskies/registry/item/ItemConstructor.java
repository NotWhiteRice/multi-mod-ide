package io.github.notwhiterice.endlessskies.registry.item;

import net.minecraft.world.item.Item;

public interface ItemConstructor<T extends Item> {
    T create(Item.Properties prop);
}
