package io.github.notwhiterice.endlessskies.block.entity;

import io.github.notwhiterice.endlessskies.block.CrudeSmelterBlock;
import io.github.notwhiterice.endlessskies.block.entity.factory.MenuBlockEntity;
import io.github.notwhiterice.endlessskies.capabilities.ESCapabilities;
import io.github.notwhiterice.endlessskies.capabilities.heat.HeatStackHandler;
import io.github.notwhiterice.endlessskies.capabilities.heat.IHeatHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Containers;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class CrudeSmelterBlockEntity extends MenuBlockEntity {
    public final ItemStackHandler itemHandler = new ItemStackHandler(2);
    public final HeatStackHandler heatHandler;

    public static final int INPUT_SLOT = 0;
    public static final int OUTPUT_SLOT = 1;

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
    private LazyOptional<IHeatHandler> lazyHeatHandler = LazyOptional.empty();

    private int progress = 0;
    private int maxProgress = 200;

    private final RecipeManager.CachedCheck<SingleRecipeInput, ? extends AbstractCookingRecipe> quickCheck
            = RecipeManager.createCheck(RecipeType.SMELTING);
    
    public CrudeSmelterBlockEntity(BlockPos pos, BlockState state) {
        super(pos, state);
        heatHandler = new HeatStackHandler(1, 1200) {
            @Override
            public void onContentsChanged(int slot) {
                super.onContentsChanged(slot);
                setChanged();
            }
        };
        this.data = new ContainerData() {
            @Override
            public int get(int i) {
                return switch(i) {
                    case 0 -> progress;
                    case 1 -> maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int i, int value) {
                switch(i) {
                    case 0 -> progress = value;
                    case 1 -> maxProgress = value;
                }
            }

            @Override
            public int getCount() { return 2; }
        };
        //heatHandler.setHeatInReservoir(0, 900, false);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
        if(cap == ForgeCapabilities.ITEM_HANDLER) return lazyItemHandler.cast();
        if(cap == ESCapabilities.HEAT_HANDLER) return lazyHeatHandler.cast();
        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
        lazyHeatHandler = LazyOptional.of(() -> heatHandler);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
        lazyHeatHandler.invalidate();
    }

    @Override
    public void saveAdditional(CompoundTag tag, HolderLookup.Provider lookupProvider) {
        tag.put("inventory", itemHandler.serializeNBT(lookupProvider));
        tag.putInt("crude_smelter.progress", progress);
        tag.putInt("crude_smelter.cook_time", maxProgress);
        tag.putString("heat_data", heatHandler.toString());
        super.saveAdditional(tag, lookupProvider);
    }

    @Override
    public void loadAdditional(CompoundTag tag, HolderLookup.Provider lookupProvider) {
        super.loadAdditional(tag, lookupProvider);
        itemHandler.deserializeNBT(lookupProvider, tag.getCompound("inventory"));
        progress = tag.getInt("crude_smelter.progress");
        maxProgress = tag.getInt("crude_smelter.cook_time");
        heatHandler.fromString(tag.getString("heat_data"));
    }

    public void dropInventory() {
        SimpleContainer inv = new SimpleContainer(itemHandler.getSlots());
        for(int i = 0; i < itemHandler.getSlots(); i++) {
            inv.setItem(i, itemHandler.getStackInSlot(i));
        }
        assert level != null;
        Containers.dropContents(level, this.worldPosition, inv);
    }

    public void tick(Level level, BlockPos pos, BlockState state) {
        boolean isLit = isLit();

        heatHandler.setInputForSlot(0, true, false);
        heatHandler.setOutputForSlot(0, false, false);

        heatHandler.tick(false);

        ItemStack inputStack = itemHandler.getStackInSlot(INPUT_SLOT);
        boolean hasInput = !inputStack.isEmpty();
        RecipeHolder<? extends AbstractCookingRecipe> recipeholder;
        if (hasInput) recipeholder = quickCheck.getRecipeFor(new SingleRecipeInput(inputStack), level).orElse(null);
        else recipeholder = null;

        int i = 99;
        if (isLit() && canBurn(level.registryAccess(), recipeholder)) {
            increaseCraftingProgress();
            heatHandler.drain(0, 1, false);
            if (hasProgressFinished()) {
                resetProgress();
                maxProgress = getTotalCookTime(level);
                if (burn(level.registryAccess(), recipeholder)) {}
            }
        } else resetProgress();

        if (isLit != isLit()) {
            state = state.setValue(CrudeSmelterBlock.LIT, isLit());
            level.setBlock(pos, state, 3);
        }

        setChanged();
    }

    private boolean isLit() { return heatHandler.getHeatInSlot(0) >= 900; }

    private boolean canBurn(RegistryAccess pRegistryAccess, @Nullable RecipeHolder<? extends AbstractCookingRecipe> pRecipe) {
        if (!itemHandler.getStackInSlot(INPUT_SLOT).isEmpty() && pRecipe != null) {
            ItemStack result = pRecipe.value().assemble(new SingleRecipeInput(itemHandler.getStackInSlot(0)), pRegistryAccess);
            if (result.isEmpty()) return false;
            else {
                ItemStack outputStack = itemHandler.getStackInSlot(OUTPUT_SLOT);
                if (outputStack.isEmpty()) return true;
                else if (!ItemStack.isSameItemSameComponents(outputStack, result)) return false;
                else return outputStack.getCount() + result.getCount() <= 99 && outputStack.getCount() + result.getCount() <= outputStack.getMaxStackSize() || outputStack.getCount() + result.getCount() <= result.getMaxStackSize();
            }
        } else return false;
    }

    private boolean burn(RegistryAccess pRegistryAccess, @Nullable RecipeHolder<? extends AbstractCookingRecipe> pRecipe) {
        if (pRecipe != null && this.canBurn(pRegistryAccess, pRecipe)) {
            ItemStack inputStack = itemHandler.getStackInSlot(INPUT_SLOT);
            ItemStack result = pRecipe.value().assemble(new SingleRecipeInput(inputStack), pRegistryAccess);
            ItemStack outputStack = itemHandler.getStackInSlot(OUTPUT_SLOT);
            if (outputStack.isEmpty()) itemHandler.setStackInSlot(OUTPUT_SLOT, result.copy());
            else if (ItemStack.isSameItemSameComponents(outputStack, result)) outputStack.grow(result.getCount());

            inputStack.shrink(1);
            return true;
        } else {
            return false;
        }
    }

    private int getTotalCookTime(Level pLevel) {
        SingleRecipeInput singlerecipeinput = new SingleRecipeInput(itemHandler.getStackInSlot(INPUT_SLOT));
        return quickCheck.getRecipeFor(singlerecipeinput, pLevel).map((recipe) -> {
            return (recipe.value()).getCookingTime();
        }).orElse(200);
    }


    private boolean hasProgressFinished() {
        return progress >= maxProgress;
    }

    private void resetProgress() {
        progress = 0;
    }

    public boolean isSuperheated() {
        return heatHandler.isSlotCreativeHeated(0);
    }

    private void increaseCraftingProgress() {
        int step = heatHandler.getCreativeBonusForSlot(0);
        if(!heatHandler.isSlotCreativeHeated(0) || step == 1) step = heatHandler.getHeatInSlot(0) >= 1100 ? 2 : 1;
        progress += step;
    }
}
