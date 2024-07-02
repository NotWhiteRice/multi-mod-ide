package io.github.notwhiterice.endlessskies.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Containers;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

public abstract class BasicTileEntity extends BlockEntity {
    private ItemStackHandler itemHandler = null;
    protected LazyOptional<IItemHandler> lazyItemHandler = null;

    ContainerData data = null;

    public BasicTileEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public void prepareItemHandler(int slots) {
        itemHandler = new ItemStackHandler(slots);
        lazyItemHandler = LazyOptional.empty();
    }

    public void dropContents() {
        if(itemHandler == null || level == null) return;
        SimpleContainer storage = new SimpleContainer(itemHandler.getSlots());
        for(int i = 0; i < itemHandler.getSlots(); i++) {
            storage.setItem(i, itemHandler.getStackInSlot(i));
        }
        Containers.dropContents(level, worldPosition, storage);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap) {
        if(lazyItemHandler != null && cap == ForgeCapabilities.ITEM_HANDLER) return lazyItemHandler.cast();
        return super.getCapability(cap);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        if(itemHandler != null) lazyItemHandler = LazyOptional.of(() -> itemHandler);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        if(lazyItemHandler != null) lazyItemHandler.invalidate();
    }

    @Override
    public void saveAdditional(CompoundTag tag, HolderLookup.Provider lookupProvider) {
        super.saveAdditional(tag, lookupProvider);
        if(itemHandler != null) tag.put("inventory", itemHandler.serializeNBT(lookupProvider));
    }

    @Override
    public void loadAdditional(CompoundTag tag, HolderLookup.Provider lookupProvider) {
        super.loadAdditional(tag, lookupProvider);
        if(itemHandler != null) itemHandler.deserializeNBT(lookupProvider, tag.getCompound("inventory"));
    }
}
