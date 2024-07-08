package io.github.notwhiterice.endlessskies.inventory.factory;

import io.github.notwhiterice.endlessskies.Reference;
import io.github.notwhiterice.endlessskies.block.entity.CreativeHeaterBlockEntity;
import io.github.notwhiterice.endlessskies.block.entity.factory.MenuBlockEntity;
import io.github.notwhiterice.endlessskies.block.factory.BasicBlock;
import io.github.notwhiterice.endlessskies.block.factory.BlockContext;
import io.github.notwhiterice.endlessskies.block.factory.MenuEntityBlock;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.Nullable;

public abstract class BasicMenu<T extends BlockEntity & MenuProvider> extends AbstractContainerMenu {
    public BlockContext context;
    public T container;
    protected Level level;
    protected ContainerData data;

    public BasicMenu(int id, Inventory inv, BlockEntity entity, ContainerData data) {
        super(((BasicBlock)entity.getBlockState().getBlock()).context.menu.getMenuType(), id);
        context = ((BasicBlock)entity.getBlockState().getBlock()).context;

        container = (T) entity;
        level = entity.getLevel();
        this.data = data;

        addPlayerInventory(inv);
        addPlayerHotbar(inv);

        addDataSlots(data);
    }

    protected abstract int getSlotCount();
    protected abstract int getInvLeft();
    protected abstract int getInvTop();
    protected abstract int getHotbarTop();


    @Override
    public ItemStack quickMoveStack(Player playerIn, int pIndex) {
        if(getSlotCount() == 0) return ItemStack.EMPTY;
        Slot slot = slots.get(pIndex);
        if (slot == null || !slot.hasItem()) return ItemStack.EMPTY;  //EMPTY_ITEM
        ItemStack itemStack = slot.getItem();
        ItemStack itemStackCopy = itemStack.copy();

        if (pIndex < 36) {
            if (!moveItemStackTo(itemStack, 36, 36 + getSlotCount(), false)) {
                return ItemStack.EMPTY;
            }
        } else if (pIndex < 36 + getSlotCount()) {
            if (!moveItemStackTo(itemStack, 0, 36, false)) {
                return ItemStack.EMPTY;
            }
        } else return ItemStack.EMPTY;

        if (itemStack.getCount() == 0) slot.set(ItemStack.EMPTY);
        else slot.setChanged();
        slot.onTake(playerIn, itemStack);
        return itemStackCopy;
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(ContainerLevelAccess.create(level, container.getBlockPos()), player, context.get());
    }

    protected void addPlayerInventory(Inventory playerInventory) {
        for(int i = 0; i < 3; ++i) {
            for(int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInventory, j+i*9+9, getInvLeft()+j*18, getInvTop()+i*18));
            }
        }
    }

    protected void addPlayerHotbar(Inventory playerInventory) {
        for(int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, getInvLeft()+i*18, getHotbarTop()));
        }
    }
}
