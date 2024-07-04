package io.github.deprecated.v1.endlessskies.core.block;

import com.mojang.serialization.MapCodec;
import io.github.deprecated.v1.endlessskies.core.util.DirectionUtils;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;

public class DiPanelBlock extends PanelBlock {
    //Static variables
    public static final DirectionProperty FACING = DirectionProperty.create("facing",
            Direction.NORTH, Direction.WEST, Direction.SOUTH, Direction.EAST);

    //Constructors
    public DiPanelBlock() {
        super(BlockBehaviour.Properties.ofFullCopy(Blocks.REPEATER));
        this.registerDefaultState(getDefaultState());
    }
    public DiPanelBlock(Properties prop) {
        super(prop);
        this.registerDefaultState(getDefaultState());
    }

    //Vanilla overrides
    @Override
    public void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState state = super.getStateForPlacement(context);
        Direction face = state.getValue(FACE);

        double mouseX = context.getClickLocation().x - context.getClickedPos().getX();
        double mouseY = context.getClickLocation().y - context.getClickedPos().getY();
        double mouseZ = context.getClickLocation().z - context.getClickedPos().getZ();
        double faceX = mouseX, faceY = mouseY;

        if(DirectionUtils.onYAxis(face)) faceY = 1-mouseZ;
        if(face == Direction.WEST) faceX = 1-mouseZ;
        if(face == Direction.SOUTH) faceX = 1-mouseX;
        if(face == Direction.EAST) faceX = mouseZ;

        Direction facing = Direction.NORTH;
        if(faceX > faceY && faceX>=1-faceY) facing=Direction.EAST;
        if(faceX >= faceY && faceX<1-faceY) facing=Direction.SOUTH;
        if(faceX < faceY && faceX<=1-faceY) facing=Direction.WEST;

        return state.setValue(FACING, facing);
    }

    @Override
    public MapCodec<? extends DiPanelBlock> codec() { return simpleCodec(DiPanelBlock::new); }

    //Helper functions
    @Override
    public BlockState getDefaultState() { return super.getDefaultState().setValue(FACING, Direction.NORTH); }

    public static Direction getDirFromSide(Direction face, Direction facing, Direction side) {
        if(side == Direction.UP) return face.getOpposite();
        if(side == Direction.DOWN) return face;
        if(DirectionUtils.onYAxis(face)) return DirectionUtils.joinCardinals(facing, side);
        if(DirectionUtils.hasOneAxis(side, facing)) return (side == facing) == (DirectionUtils.onZAxis(side))
                ? Direction.UP : Direction.DOWN;
        if(DirectionUtils.isNegative(side, facing) || DirectionUtils.isPositive(side, facing)) return face.getCounterClockWise();
        return face.getCounterClockWise();
    }

    public static Direction getDirFromSide(BlockState state, Direction side) {
        if(!(state.getBlock() instanceof DiPanelBlock)) return null;
        return getDirFromSide(state.getValue(FACE), state.getValue(FACING), side);
    }

    public static Direction getSideFromDir(Direction face, Direction facing, Direction dir) {
        if(dir == face.getOpposite()) return Direction.UP;
        if(dir == face) return Direction.DOWN;
        if(DirectionUtils.onYAxis(face)) return DirectionUtils.unjoinCardinals(facing, dir);
        if(DirectionUtils.onYAxis(dir)) return (dir == Direction.UP) == DirectionUtils.onZAxis(facing)
                ? facing : facing.getOpposite();
        if(dir == face.getCounterClockWise()) {
            if(facing == Direction.NORTH) return Direction.WEST;
            if(facing == Direction.WEST) return Direction.NORTH;
            if(facing == Direction.SOUTH) return Direction.EAST;
            return Direction.SOUTH;
        }
        if(facing == Direction.NORTH) return Direction.EAST;
        if(facing == Direction.WEST) return Direction.SOUTH;
        if(facing == Direction.SOUTH) return Direction.WEST;
        return Direction.NORTH;
    }

    public static Direction getSideFromDir(BlockState state, Direction side) {
        if(!(state.getBlock() instanceof DiPanelBlock)) return null;
        return getSideFromDir(state.getValue(FACE), state.getValue(FACING), side);
    }
}
