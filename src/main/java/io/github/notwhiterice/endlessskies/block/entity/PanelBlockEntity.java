package io.github.notwhiterice.endlessskies.block.entity;

import io.github.notwhiterice.endlessskies.block.entity.factory.BasicBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class PanelBlockEntity extends BasicBlockEntity {
    public PanelBlockEntity(BlockPos pos, BlockState state) {super(pos, state);}

    public Direction face = Direction.DOWN;
    public Direction facing = Direction.NORTH;

    @Override
    public void saveAdditional(CompoundTag tag, HolderLookup.Provider lookupProvider) {
        tag.putString("panel.face", face.toString());
        super.saveAdditional(tag, lookupProvider);
    }

    @Override
    public void loadAdditional(CompoundTag tag, HolderLookup.Provider lookupProvider) {
        super.loadAdditional(tag, lookupProvider);
        face = Direction.byName(tag.getString("panel.face"));
    }

    @Override
    public void tick(Level level, BlockPos pos, BlockState state) {}
}
