package io.github.notwhiterice.endlessskies.item.factory;

import io.github.notwhiterice.endlessskies.Reference;
import io.github.notwhiterice.endlessskies.registry.ModContext;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class CraftingItem extends BasicItem {
    public CraftingItem(Item.Properties prop) {
        super(prop);
        ModContext.getContext(Reference.modID).logger.warn("CraftingItem", "<init>", "Making an item through CraftingItem");
    }

    @Override
    public boolean hasCraftingRemainingItem(ItemStack stack) {
        return true;
    }

    @Override
    public ItemStack getCraftingRemainingItem(ItemStack itemStack) {
        if(itemStack.isDamageableItem()) itemStack.setDamageValue(itemStack.getDamageValue()+1);
        return itemStack.copy();
    }
}
