package io.github.notwhiterice.endlessskies.recipe;

import io.github.notwhiterice.endlessskies.block.entity.RockCrusherBlockEntity;
import io.github.notwhiterice.endlessskies.inventory.RockCrusherMenu;
import io.github.notwhiterice.endlessskies.inventory.screen.RockCrusherScreen;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

import java.util.ArrayList;
import java.util.List;

public class RockCrusherRecipe {
    public static final List<RockCrusherRecipe> instances = new ArrayList<>();

    public final ItemStack inputItemStack;
    public final ItemStack outputItemStack;

    private RockCrusherRecipe(ItemStack input, ItemStack output) {
        inputItemStack = input; outputItemStack = output;
        instances.add(this);
    }

    public static RockCrusherRecipe create(ItemStack input, ItemStack output) {
        return new RockCrusherRecipe(input, output);
    }

    public static boolean hasValidRecipe(ItemStackHandler itemHandler) {
        if(itemHandler.getSlots() != 2) return false;
        for(RockCrusherRecipe recipe : instances)
            if(recipe.inputItemStack.is(itemHandler.getStackInSlot(RockCrusherBlockEntity.INPUT_SLOT).getItem()))
                if(recipe.inputItemStack.getCount() <= itemHandler.getStackInSlot(RockCrusherBlockEntity.INPUT_SLOT).getCount())
                return true;
        return false;
    }

    public static RockCrusherRecipe getRecipeInContainer(ItemStackHandler itemHandler) {
        if(itemHandler.getSlots() != 2) return null;
        for(RockCrusherRecipe recipe : instances)
            if(recipe.inputItemStack.is(itemHandler.getStackInSlot(RockCrusherBlockEntity.INPUT_SLOT).getItem()))
                if(recipe.inputItemStack.getCount() <= itemHandler.getStackInSlot(RockCrusherBlockEntity.INPUT_SLOT).getCount())
                    return recipe;
        return null;
    }
}
