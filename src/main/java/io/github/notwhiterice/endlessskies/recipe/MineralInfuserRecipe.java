package io.github.notwhiterice.endlessskies.recipe;

import io.github.notwhiterice.endlessskies.block.entity.MineralInfuserBlockEntity;
import io.github.notwhiterice.endlessskies.block.entity.RockCrusherBlockEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

import java.util.ArrayList;
import java.util.List;

public class MineralInfuserRecipe {
    public static final List<MineralInfuserRecipe> instances = new ArrayList<>();

    public final ItemStack rockItemStack;
    public final ItemStack mineralItemStack;
    public final ItemStack outputItemStack;

    private MineralInfuserRecipe(ItemStack rock, ItemStack mineral, ItemStack output) {
        rockItemStack = rock; mineralItemStack = mineral;
        outputItemStack = output;
        instances.add(this);
    }

    public static MineralInfuserRecipe create(ItemStack rock, ItemStack mineral, ItemStack output) {
        return new MineralInfuserRecipe(rock, mineral, output);
    }

    public static boolean hasValidRecipe(ItemStackHandler itemHandler) {
        if(itemHandler.getSlots() != 3) return false;
        for(MineralInfuserRecipe recipe : instances)
            if(recipe.rockItemStack.is(itemHandler.getStackInSlot(MineralInfuserBlockEntity.ROCK_SLOT).getItem()) && recipe.mineralItemStack.is(itemHandler.getStackInSlot(MineralInfuserBlockEntity.MINERAL_SLOT).getItem()))
                if(recipe.rockItemStack.getCount() <= itemHandler.getStackInSlot(MineralInfuserBlockEntity.ROCK_SLOT).getCount() && recipe.mineralItemStack.getCount() <= itemHandler.getStackInSlot(MineralInfuserBlockEntity.MINERAL_SLOT).getCount())
                    return true;
        return false;
    }

    public static MineralInfuserRecipe getRecipeInContainer(ItemStackHandler itemHandler) {
        if(itemHandler.getSlots() != 3) return null;
        for(MineralInfuserRecipe recipe : instances)
            if(recipe.rockItemStack.is(itemHandler.getStackInSlot(MineralInfuserBlockEntity.ROCK_SLOT).getItem()) && recipe.mineralItemStack.is(itemHandler.getStackInSlot(MineralInfuserBlockEntity.MINERAL_SLOT).getItem()))
                if(recipe.rockItemStack.getCount() <= itemHandler.getStackInSlot(MineralInfuserBlockEntity.ROCK_SLOT).getCount() && recipe.mineralItemStack.getCount() <= itemHandler.getStackInSlot(MineralInfuserBlockEntity.MINERAL_SLOT).getCount())
                    return recipe;
        return null;
    }
}
