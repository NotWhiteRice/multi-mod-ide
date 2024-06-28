package io.github.notwhiterice.endlessskies.core.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RedStoneWireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

import java.util.ArrayList;
import java.util.List;

public interface ICircuitBoard extends IDiPanel {
    BooleanProperty N_POWERED = BooleanProperty.create("n_powered");
    BooleanProperty W_POWERED = BooleanProperty.create("w_powered");
    BooleanProperty S_POWERED = BooleanProperty.create("s_powered");
    BooleanProperty E_POWERED = BooleanProperty.create("e_powered");

    boolean useSideForInputs(Direction dir);
    default boolean useSideForOutputs(Direction dir) { return dir == Direction.NORTH; }

    default List<Direction> getIOSides() {
        List<Direction> out = new ArrayList<>();
        for(Direction dir : Direction.values()) if(useSideForInputs(dir) || useSideForOutputs(dir)) out.add(dir);
        return out;
    }

    default boolean isSidePowered(Level level, BlockPos pos, BlockState state, Direction dir) {
        if(!useSideForInputs(dir)) return false;

        Direction face = state.getValue(FACE);
        Direction facing = state.getValue(FACING);
        Direction sDir = getRelativeDir(face, facing, dir);
        BlockPos sPos = pos.relative(sDir);
        BlockState sState = level.getBlockState(sPos);

        if(level.getSignal(sPos, sDir) > 0) return true;
        if(sState.is(Blocks.REDSTONE_WIRE)) return sState.getValue(RedStoneWireBlock.POWER) > 0;
        return false;
    }

    void tick(BlockState blockState, ServerLevel level, BlockPos blockPos, RandomSource random);

    boolean canConnectRedstone(BlockState state, BlockGetter world, BlockPos pos, Direction side);
    default boolean _canConnectRedstone(BlockState state, Direction side) {
        List<Direction> sides = getIOSides();
        for(Direction dir : sides) if(dir == getRelativeDir(state.getValue(FACE), state.getValue(FACING), side)) return true;
        return false;
    }
}
