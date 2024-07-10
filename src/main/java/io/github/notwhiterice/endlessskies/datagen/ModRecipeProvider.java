package io.github.notwhiterice.endlessskies.datagen;

import io.github.notwhiterice.endlessskies.Reference;
import io.github.notwhiterice.endlessskies.registry.init.ItemInit;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
        addSmelting(Reference.modID, recipeOutput, List.of(Blocks.SMOOTH_STONE), RecipeCategory.MISC, ItemInit.itemStoneTablet.get(), 0.25f, 200, "stone_tablet");

        hammerRecipe(Items.COPPER_INGOT, ItemInit.itemCopperPlate.get(), recipeOutput);
        hammerRecipe(Items.GOLD_INGOT, ItemInit.itemGoldPlate.get(), recipeOutput);
        hammerRecipe(Items.IRON_INGOT, ItemInit.itemIronPlate.get(), recipeOutput);
        hammerRecipe(Items.NETHERITE_INGOT, ItemInit.itemNetheritePlate.get(), recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ItemInit.itemHammer.get())
                .pattern(" I ")
                .pattern(" SI")
                .pattern("S  ")
                .define('I', Items.IRON_INGOT)
                .define('S', Items.STICK)
                .unlockedBy(getHasName(Items.IRON_INGOT), has(Items.IRON_INGOT))
                .unlockedBy(getHasName(Items.STICK), has(Items.STICK))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ItemInit.itemMortarPestle.get())
                .pattern("sSs")
                .pattern(" s ")
                .define('s', Blocks.STONE)
                .define('S', Items.STICK)
                .unlockedBy(getHasName(Blocks.STONE), has(Blocks.STONE))
                .unlockedBy(getHasName(Items.STICK), has(Items.STICK))
                .save(recipeOutput);
    }

    private void hammerRecipe(ItemLike input, ItemLike output, RecipeOutput builder) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, output)
                .pattern("HI")
                .define('H', ItemInit.itemHammer.get())
                .define('I', input)
                .unlockedBy(getHasName(ItemInit.itemHammer.get()), has(ItemInit.itemHammer.get()))
                .unlockedBy(getHasName(input), has(input))
                .save(builder);
    }

    private <T extends AbstractCookingRecipe> void addSmelting(String modID, RecipeOutput output, List<ItemLike> ingredients, RecipeCategory category, ItemLike result, float experience, int cookingTime, String group) {
        addCooking(modID, output, RecipeSerializer.SMELTING_RECIPE, SmeltingRecipe::new, ingredients, category, result, experience, cookingTime, group, "_from_smelting");
    }

    private <T extends AbstractCookingRecipe> void addCooking(String modID, RecipeOutput output, RecipeSerializer<T> serializer, AbstractCookingRecipe.Factory<T> factory, List<ItemLike> ingredients, RecipeCategory category, ItemLike result, float experience, int cookingTime, String group, String suffix) {
        for(ItemLike ingredient : ingredients) {
            SimpleCookingRecipeBuilder.generic(Ingredient.of(ingredient), category, result, experience, cookingTime, serializer, factory)
                    .group(group).unlockedBy(getHasName(ingredient), has(ingredient))
                    .save(output, modID+":"+getItemName(result)+suffix+"_"+getItemName(ingredient));
        }
    }
}