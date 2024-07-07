package io.github.notwhiterice.endlessskies.recipe;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;

public class ModRecipes {



    public static void registerRecipes() {
        RockCrusherRecipe.create(new ItemStack(Blocks.COBBLESTONE, 1), new ItemStack(Blocks.GRAVEL, 1));
        RockCrusherRecipe.create(new ItemStack(Blocks.GRAVEL, 1), new ItemStack(Blocks.SAND, 1));
    }
}
