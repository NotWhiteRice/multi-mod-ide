package io.github.notwhiterice.endlessskies.recipe;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;

public class ModRecipes {
    public static void registerRecipes() {
        MineralInfuserRecipe.create(new ItemStack(Blocks.BLACKSTONE, 1), new ItemStack(Items.GOLD_INGOT, 1), new ItemStack(Blocks.GILDED_BLACKSTONE, 1));
        MineralInfuserRecipe.create(new ItemStack(Blocks.OBSIDIAN, 1), new ItemStack(Items.WATER_BUCKET, 1), new ItemStack(Blocks.CRYING_OBSIDIAN, 1));

        RockCrusherRecipe.create(new ItemStack(Blocks.COBBLESTONE, 1), new ItemStack(Blocks.GRAVEL, 1));
        RockCrusherRecipe.create(new ItemStack(Blocks.GRAVEL, 1), new ItemStack(Blocks.SAND, 1));
    }
}
