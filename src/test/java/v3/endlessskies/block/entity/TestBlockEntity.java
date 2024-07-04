package io.github.deprecated.v3.endlessskies.block.entity;

import io.github.deprecated.v3.endlessskies.temp.TileEntityInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class TestBlockEntity extends BlockEntity {
    public ContainerData data = null;
    public int timesClicked = 0;

    public TestBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(TileEntityInit.tileEntityTest.get(), pPos, pBlockState);
        this.data = new ContainerData() {
            @Override
            public int get(int i) {
                return switch (i) {
                    case 0 -> timesClicked;
                    default -> 0;
                };
            }

            @Override
            public void set(int i, int value) {
                switch (i) {
                    case 0 -> timesClicked = value;
                }
            }

            @Override
            public int getCount() {
                return 1;
            }
        };
    }

    @Override
    public void saveAdditional(CompoundTag tag, HolderLookup.Provider lookupProvider) {
        tag.putInt("test_blocked.clicks", timesClicked);

        super.saveAdditional(tag, lookupProvider);
    }

    @Override
    public void loadAdditional(CompoundTag tag, HolderLookup.Provider lookupProvider) {
        super.loadAdditional(tag, lookupProvider);
        timesClicked = tag.getInt("test_blocked.clicks");
    }
}
