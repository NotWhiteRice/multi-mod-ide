package io.github.notwhiterice.endlessskies.core.block;

import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.shapes.VoxelShape;

public interface IPanel extends ISpecialBlock {
    DirectionProperty FACE = DirectionProperty.create(
            "face", Direction.values());

    default VoxelShape genShape(Direction face, double pixelHeight) {
        return switch(face) {
            case UP -> Block.box(0.0, 16.0-pixelHeight, 0.0, 16.0, 16.0,16.0);
            case DOWN -> Block.box(0.0, 0.0, 0.0, 16.0, pixelHeight, 16.0);
            case NORTH -> Block.box(0.0, 0.0, 0.0, 16.0, 16.0, pixelHeight);
            case WEST -> Block.box(0.0, 0.0, 0.0, pixelHeight, 16.0, 16.0);
            case SOUTH -> Block.box(0.0, 0.0, 16.0-pixelHeight, 16.0, 16.0, 16.0);
            case EAST -> Block.box(16.0-pixelHeight, 0.0, 0.0, 16.0, 16.0, 16.0);
        };
    }

    default VoxelShape _getShape(BlockState state) {
        return genShape(state.getValue(FACE), 2.0);
    }

    default Direction getRelativeDir(Direction face, Direction dir) {
        if(dir == Direction.UP) return face.getOpposite();
        if(dir == Direction.DOWN) return face;
        if(face == Direction.UP || face == Direction.DOWN) return dir;
        if(dir == Direction.NORTH) return Direction.UP;
        if(dir == Direction.WEST) return dir.getCounterClockWise();
        if(dir == Direction.SOUTH) return Direction.DOWN;
        return dir.getClockWise();
    }

    default StateDefinition.Builder<Block, BlockState> prepareStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        return builder.add(FACE);
    }

    default BlockState prepareDefaultState(BlockState pre) {
        return pre.setValue(FACE, Direction.DOWN);
    }

    default BlockState prepareStateForPlacement(BlockState pre, BlockPlaceContext context) {
        return pre.setValue(FACE, context.getClickedFace().getOpposite());
    }
}
