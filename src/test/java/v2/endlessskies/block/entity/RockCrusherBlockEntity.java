package io.github.deprecated.v2.endlessskies.block.entity;

import io.github.deprecated.v2.endlessskies.init.BlockEntityInit;
import io.github.deprecated.v2.endlessskies.screen.RockCrusherMenu;
import io.github.deprecated.v2.endlessskies.block.entity.base.MenuTileEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class RockCrusherBlockEntity extends MenuTileEntity {
    private static final int INPUT_SLOT = 0;
    private static final int OUTPUT_SLOT = 1;

    private int progress = 0;
    private int maxProgress = 160;

    public RockCrusherBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(BlockEntityInit.tileEntityRockCrusher.get(), pPos, pBlockState);
        prepareItemHandler(2);
        prepareMenu(RockCrusherMenu.class);
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
    public Component getDisplayName() {
        return Component.translatable("block.endlessskies.rock_crusher");
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
        ItemStack result = new ItemStack(Blocks.GRAVEL, 1);
        this.itemHandler.extractItem(INPUT_SLOT, 1, false);
        this.itemHandler.setStackInSlot(OUTPUT_SLOT, new ItemStack(result.getItem(), itemHandler.getStackInSlot(OUTPUT_SLOT).getCount() + result.getCount()));
    }

    private void increaseCraftingProgress() {
        progress++;
    }

    private boolean hasRecipe() {
        boolean isValidCraft = itemHandler.getStackInSlot(INPUT_SLOT).getItem() == Items.COBBLESTONE;
        ItemStack result = new ItemStack(Blocks.GRAVEL);

        return isValidCraft && canInsertAmountToOutputSlot(result.getCount()) && canInsertItemIntoOutputSlot(result.getItem());
    }

    private boolean canInsertItemIntoOutputSlot(Item item) {
        return itemHandler.getStackInSlot(OUTPUT_SLOT).isEmpty() || itemHandler.getStackInSlot(OUTPUT_SLOT).is(item);
    }

    private boolean canInsertAmountToOutputSlot(int count) {
        return itemHandler.getStackInSlot(OUTPUT_SLOT).getCount() + count <= itemHandler.getStackInSlot(OUTPUT_SLOT).getMaxStackSize();
    }
}
