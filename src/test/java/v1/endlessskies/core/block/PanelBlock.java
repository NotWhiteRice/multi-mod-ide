package io.github.deprecated.v1.endlessskies.core.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RedStoneWireBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.event.ForgeEventFactory;

import java.util.EnumSet;

public class PanelBlock extends BasicBlock {
    //Static variables
    public static final DirectionProperty FACE = DirectionProperty.create("face", Direction.values());

    //Constructors
    public PanelBlock() {
        super(BlockBehaviour.Properties.ofFullCopy(Blocks.REPEATER));
        this.registerDefaultState(getDefaultState());
    }
    public PanelBlock(Properties prop) {
        super(prop);
        this.registerDefaultState(getDefaultState());
    }

    //Vanilla overrides
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return getShapeForFace(state.getValue(FACE), 2.0);
    }

    @Override
    public void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACE);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACE, context.getClickedFace().getOpposite());
    }

    @Override
    public MapCodec<? extends PanelBlock> codec() { return simpleCodec(PanelBlock::new); }

    //Helper functions
    public VoxelShape getShapeForFace(Direction face, double heightInPixels) {
        if(face == Direction.UP) return box(0.0, 16.0-heightInPixels, 0.0, 16.0, 16.0, 16.0);
        if(face == Direction.DOWN) return box(0.0, 0.0, 0.0, 16.0, heightInPixels, 16.0);
        if(face == Direction.NORTH) return box(0.0, 0.0, 0.0, 16.0, 16.0, heightInPixels);
        if(face == Direction.WEST) return box(0.0, 0.0, 0.0, heightInPixels, 16.0, 16.0);
        if(face == Direction.SOUTH) return box(0.0, 0.0, 16.0-heightInPixels, 16.0, 16.0, 16.0);
        return box(16.0-heightInPixels, 0.0, 0.0, 16.0, 16.0, 16.0);
    }

    public BlockState getDefaultState() {
        return this.stateDefinition.any().setValue(FACE, Direction.DOWN);
    }

    public static Direction getDirFromSide(Direction face, Direction side) {
        if(side == Direction.UP) return face.getOpposite();
        if(side == Direction.DOWN) return face;
        if(face == Direction.UP || face == Direction.DOWN) return side;
        if(side == Direction.NORTH) return Direction.UP;
        if(side == Direction.WEST) return face.getCounterClockWise();
        if(side == Direction.SOUTH) return Direction.DOWN;
        return face.getClockWise();
    }

    public static Direction getDirFromSide(BlockState state, Direction side) {
        if(!(state.getBlock() instanceof PanelBlock)) return null;
        return getDirFromSide(state.getValue(FACE), side);
    }

    public static Direction getSideFromDir(Direction face, Direction dir) {
        if(dir == face.getOpposite()) return Direction.UP;
        if(dir == face) return Direction.DOWN;
        if(face == Direction.UP || face == Direction.DOWN) return dir;
        if(dir == Direction.UP) return Direction.NORTH;
        if(dir == face.getCounterClockWise()) return Direction.WEST;
        if(dir == Direction.DOWN) return Direction.SOUTH;
        return Direction.EAST;
    }

    public static Direction getSideFromDir(BlockState state, Direction dir) {
        if(!(state.getBlock() instanceof PanelBlock)) return null;
        return getSideFromDir(state.getValue(FACE), dir);
    }

    public static boolean isSidePowered(Level level, BlockPos pos, BlockState state, Direction side) {
        Direction dir = getDirFromSide(state, side);
        BlockPos rPos = pos.relative(dir);
        BlockState rState = level.getBlockState(rPos);

        if(level.getSignal(rPos, dir) > 0) return true;
        if(rState.is(Blocks.REDSTONE_WIRE)) return rState.getValue(RedStoneWireBlock.POWER) > 0;
        return false;
    }

    public void updateNeighborsAtSides(Level level, BlockPos pos, BlockState state) {
        EnumSet<Direction> dirs = EnumSet.of(
                getDirFromSide(state.getValue(FACE), Direction.NORTH),
                getDirFromSide(state.getValue(FACE), Direction.WEST),
                getDirFromSide(state.getValue(FACE), Direction.SOUTH),
                getDirFromSide(state.getValue(FACE), Direction.EAST));
        if (!ForgeEventFactory.onNeighborNotify(level, pos, level.getBlockState(pos), dirs, false).isCanceled()) {
            for(Direction dir : dirs) {
                level.neighborChanged(pos.relative(dir), this, pos);
                level.updateNeighborsAtExceptFromFacing(pos.relative(dir), this, dir.getOpposite());
            }
        }
    }

}
