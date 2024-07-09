package io.github.notwhiterice.endlessskies.block.entity;

import io.github.notwhiterice.endlessskies.block.CreativeHeaterBlock;
import io.github.notwhiterice.endlessskies.block.entity.factory.MenuBlockEntity;
import io.github.notwhiterice.endlessskies.capabilities.ESCapabilities;
import io.github.notwhiterice.endlessskies.capabilities.heat.HeatStackHandler;
import io.github.notwhiterice.endlessskies.capabilities.heat.IHeatHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;

public class CreativeHeaterBlockEntity extends MenuBlockEntity<CreativeHeaterBlockEntity, CreativeHeaterBlock> {
    public final HeatStackHandler heatHandler;
    private int output = 1000;

    private LazyOptional<IHeatHandler> lazyHeatHandler = LazyOptional.empty();

    public CreativeHeaterBlockEntity(BlockPos pos, BlockState state) {
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
            public int get(int i) { return i == 0 ? output : 0; }

            @Override
            public void set(int i, int value) { if (i == 0) { output = value; } }

            @Override
            public int getCount() { return 1; }
        };
    }

    @Override
    public void saveAdditional(CompoundTag tag, HolderLookup.Provider lookupProvider) {
        tag.putInt("creative_heater.output", output);
        super.saveAdditional(tag, lookupProvider);
    }

    @Override
    public void loadAdditional(CompoundTag tag, HolderLookup.Provider lookupProvider) {
        super.loadAdditional(tag, lookupProvider);
        output = tag.getInt("creative_heater.output");
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, Direction side) {
        if(cap == ESCapabilities.HEAT_HANDLER) return lazyHeatHandler.cast();
        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyHeatHandler = LazyOptional.of(() -> heatHandler);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyHeatHandler.invalidate();
    }

    @Override
    public void tick(Level level, BlockPos pos, BlockState state) {
        heatHandler.setInputForSlot(0, false, false);
        heatHandler.setOutputForSlot(0, true, false);
        heatHandler.setSlotCreativeHeated(0, true, false);
        heatHandler.setCreativeBonusForSlot(0, 5, false);
        heatHandler.setHeatInSlot(0, output, false);

        for(Direction side : Direction.values()) {
            BlockPos sidedPos = pos.relative(side);
            BlockEntity sidedTile = level.getBlockEntity(sidedPos);
            if(sidedTile != null) {
                sidedTile.getCapability(ESCapabilities.HEAT_HANDLER, side.getOpposite()).ifPresent(heatHandler -> {
                    heatHandler.acceptSourceInSlot(0, this.heatHandler.getStackInSlot(0), false);
                });
            }
        }
    }
}
