package io.github.notwhiterice.endlessskies.block.entity;

import io.github.notwhiterice.endlessskies.Reference;
import io.github.notwhiterice.endlessskies.block.entity.factory.TileEntityContext;
import io.github.notwhiterice.endlessskies.inventory.RockCrusherMenu;
import io.github.notwhiterice.endlessskies.recipe.RockCrusherRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class RockCrusherBlockEntity extends BlockEntity implements MenuProvider {
    private final ItemStackHandler itemHandler = new ItemStackHandler(2);

    public static final int INPUT_SLOT = 0;
    public static final int OUTPUT_SLOT = 1;

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 160;

    public RockCrusherBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(TileEntityContext.getContext(Reference.modID, "rock_crusher").getEntityType(), pPos, pBlockState);
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
            public int getCount() {
                return 2;
            }
        };
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap) {
        if(cap == ForgeCapabilities.ITEM_HANDLER) return lazyItemHandler.cast();
        return super.getCapability(cap);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.endlessskies.rock_crusher");
    }

    public void dropInventory() {
        SimpleContainer inv = new SimpleContainer(itemHandler.getSlots());
        for(int i = 0; i < itemHandler.getSlots(); i++) {
            inv.setItem(i, itemHandler.getStackInSlot(i));
        }
        Containers.dropContents(this.level, this.worldPosition, inv);
    }

    @Override
    public void saveAdditional(CompoundTag tag, HolderLookup.Provider lookupProvider) {
        tag.put("inventory", itemHandler.serializeNBT(lookupProvider));
        tag.putInt("mineral_infuser.progress", progress);

        super.saveAdditional(tag, lookupProvider);
    }

    @Override
    public void loadAdditional(CompoundTag tag, HolderLookup.Provider lookupProvider) {
        super.loadAdditional(tag, lookupProvider);
        itemHandler.deserializeNBT(lookupProvider, tag.getCompound("inventory"));
        progress = tag.getInt("mineral_infuser.progress");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new RockCrusherMenu(i, inventory, this, this.data);
    }

    public void tick(Level level, BlockPos pos, BlockState state) {
        if(hasRecipe()) {
            increaseCraftingProgress();
            setChanged(level, pos, state);

            if(hasProgressFinished()) {
                craftItem();
                resetProgress();
            }
        } else {
            resetProgress();
        }
    }

    private boolean hasProgressFinished() {
        return progress >= maxProgress;
    }

    private void resetProgress() {
        progress = 0;
    }

    private void craftItem() {
        RockCrusherRecipe recipe = RockCrusherRecipe.getRecipeInContainer(itemHandler);
        this.itemHandler.extractItem(INPUT_SLOT, recipe.inputItemStack.getCount(), false);
        this.itemHandler.setStackInSlot(OUTPUT_SLOT, new ItemStack(recipe.outputItemStack.getItem(), itemHandler.getStackInSlot(OUTPUT_SLOT).getCount() + recipe.outputItemStack.getCount()));
    }

    private void increaseCraftingProgress() {
        progress++;
    }

    private boolean hasRecipe() {
        boolean isValidCraft = RockCrusherRecipe.hasValidRecipe(itemHandler);
        RockCrusherRecipe recipe = RockCrusherRecipe.getRecipeInContainer(itemHandler);
        return isValidCraft && canInsertAmountToOutputSlot(recipe.outputItemStack.getCount()) && canInsertItemIntoOutputSlot(recipe.outputItemStack.getItem());
    }

    private boolean canInsertItemIntoOutputSlot(Item item) {
        return itemHandler.getStackInSlot(OUTPUT_SLOT).isEmpty() || itemHandler.getStackInSlot(OUTPUT_SLOT).is(item);
    }

    private boolean canInsertAmountToOutputSlot(int count) {
        return itemHandler.getStackInSlot(OUTPUT_SLOT).getCount() + count <= itemHandler.getStackInSlot(OUTPUT_SLOT).getMaxStackSize();
    }
}
