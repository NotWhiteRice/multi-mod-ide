package io.github.deprecated.v1.endlessskies.core.block;

import io.github.deprecated.v1.endlessskies.core.util.DirectionUtils;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;

public interface IDiPanel extends IPanel {
    DirectionProperty FACING = DirectionProperty.create(
            "facing",
            Direction.NORTH, Direction.WEST, Direction.SOUTH, Direction.EAST);

    default Direction getRelativeDir(Direction face, Direction facing, Direction dir) {
        if(dir == Direction.UP) return face.getOpposite();
        if(dir == Direction.DOWN) return face;
        if(DirectionUtils.onYAxis(face)) return DirectionUtils.joinCardinals(facing, dir);
        if(DirectionUtils.hasOneAxis(dir, facing)) return (dir == facing) == (DirectionUtils.onZAxis(dir))
                ? Direction.UP : Direction.DOWN;
        if(DirectionUtils.isNegative(dir, facing) || DirectionUtils.isPositive(dir, facing)) return face.getCounterClockWise();
        return face.getCounterClockWise();
    }

    @Override
    default StateDefinition.Builder<Block, BlockState> prepareStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        return IPanel.super.prepareStateDefinition(builder).add(FACING);
    }

    @Override
    default BlockState prepareDefaultState(BlockState pre) {
        return IPanel.super.prepareDefaultState(pre).setValue(FACING, Direction.NORTH);
    }

    @Override
    default BlockState prepareStateForPlacement(BlockState pre0, BlockPlaceContext context) {
        BlockState pre1 = IPanel.super.prepareStateForPlacement(pre0, context);
        double mouseX = context.getClickLocation().x - context.getClickedPos().getX();
        double mouseY = context.getClickLocation().y - context.getClickedPos().getY();
        double mouseZ = context.getClickLocation().z - context.getClickedPos().getZ();
        Direction face = pre1.getValue(FACE);

        double faceX = mouseX, faceY = mouseY;
        if(DirectionUtils.onYAxis(face)) faceY = 1-mouseZ;
        if(face == Direction.WEST) faceX = 1-mouseZ;
        if(face == Direction.SOUTH) faceX = 1-mouseX;
        if(face == Direction.EAST) faceX = mouseZ;

        Direction facing = Direction.NORTH;
        if(faceX > faceY && faceX>=1-faceY) facing=Direction.EAST;
        if(faceX >= faceY && faceX<1-faceY) facing=Direction.SOUTH;
        if(faceX < faceY && faceX<=1-faceY) facing=Direction.WEST;

        return pre1.setValue(FACING, facing);
    }
}
