package io.github.notwhiterice.deprecated.endlessskies030alpha.core.block;

import com.mojang.serialization.MapCodec;
import io.github.notwhiterice.deprecated.endlessskies030alpha.core.block.state.properties.CircuitConnection;
import io.github.notwhiterice.deprecated.endlessskies030alpha.core.util.DirectionUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RedStoneWireBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.ticks.TickPriority;
import net.minecraftforge.event.ForgeEventFactory;

import java.util.EnumSet;

public class CircuitedPanelBlock extends PanelBlock {
    //Static variables
    public static final EnumProperty<CircuitConnection> NORTH = EnumProperty.create("north", CircuitConnection.class);
    public static final BooleanProperty N_POWERED = BooleanProperty.create("n_powered");
    public static final EnumProperty<CircuitConnection> WEST = EnumProperty.create("west", CircuitConnection.class);
    public static final BooleanProperty W_POWERED = BooleanProperty.create("w_powered");
    public static final EnumProperty<CircuitConnection> SOUTH = EnumProperty.create("south", CircuitConnection.class);
    public static final BooleanProperty S_POWERED = BooleanProperty.create("s_powered");
    public static final EnumProperty<CircuitConnection> EAST = EnumProperty.create("east", CircuitConnection.class);
    public static final BooleanProperty E_POWERED = BooleanProperty.create("e_powered");

    //Constructors
    public CircuitedPanelBlock() {
        super(BlockBehaviour.Properties.ofFullCopy(Blocks.REPEATER));
        this.registerDefaultState(getDefaultState());
    }
    public CircuitedPanelBlock(Properties prop) {
        super(prop);
        this.registerDefaultState(getDefaultState());
    }

    //Vanilla overrides
    @Override
    public void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(NORTH, WEST, SOUTH, EAST);
    }

    @Override
    public boolean canConnectRedstone(BlockState state, BlockGetter world, BlockPos pos, Direction dir) {
        Direction side = getSideFromDir(state, dir);
        if(DirectionUtils.onYAxis(side)) return false;
        if(side == Direction.NORTH && state.getValue(SOUTH).isIO()) return true;
        if(side == Direction.WEST && state.getValue(EAST).isIO()) return true;
        if(side == Direction.SOUTH && state.getValue(NORTH).isIO()) return true;
        if(side == Direction.EAST && state.getValue(WEST).isIO()) return true;
        return false;
    }

    @Override
    public boolean isSignalSource(BlockState state) {
        return state.getValue(NORTH).isIO()
                || state.getValue(WEST).isIO()
                || state.getValue(SOUTH).isIO()
                || state.getValue(EAST).isIO();
    }

    @Override
    public int getSignal(BlockState state, BlockGetter level, BlockPos pos, Direction dir) {
        Direction side = getSideFromDir(state, dir);
        if(!isSideOutput(state, side)) return 0;
        return getOutputSignal(state, side);
    }

    @Override
    public int getDirectSignal(BlockState state, BlockGetter level, BlockPos pos, Direction dir) {
        return state.getSignal(level, pos, dir);
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState prevState, boolean isMoving) {
        this.updateNeighborsAtOutputs(level, pos, state);
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if(!isMoving && !state.is(newState.getBlock())) {
            super.onRemove(state, level, pos, newState, isMoving);
            this.updateNeighborsAtOutputs(level, pos, state);
        }
    }

    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block neighborBlock, BlockPos neighborPos, boolean wasMovedByPiston) {
        Direction dir = DirectionUtils.getDirFromNeighbors(pos, neighborPos);
        Direction side = getDirFromSide(state, dir);
        if(!DirectionUtils.onYAxis(side)) level.scheduleTick(pos, this, 1, TickPriority.HIGH);
    }

    @Override
    public void tick(BlockState state, ServerLevel sLevel, BlockPos pos, RandomSource rand) {
        boolean doUpdate = false;
        if(state.getValue(NORTH).isInput()) {
            boolean isSidePowered = isSidePowered(sLevel, pos, state, Direction.NORTH);
            boolean sidePoweredState = state.getValue(N_POWERED);
            if(isSidePowered != sidePoweredState) {
                doUpdate = true;
                state = state.setValue(N_POWERED, isSidePowered);
            }
        }
        if(state.getValue(WEST).isInput()) {
            boolean isSidePowered = isSidePowered(sLevel, pos, state, Direction.WEST);
            boolean sidePoweredState = state.getValue(W_POWERED);
            if(isSidePowered != sidePoweredState) {
                doUpdate = true;
                state = state.setValue(W_POWERED, isSidePowered);
            }
        }
        if(state.getValue(SOUTH).isInput()) {
            boolean isSidePowered = isSidePowered(sLevel, pos, state, Direction.SOUTH);
            boolean sidePoweredState = state.getValue(S_POWERED);
            if(isSidePowered != sidePoweredState) {
                doUpdate = true;
                state = state.setValue(S_POWERED, isSidePowered);
            }
        }
        if(state.getValue(EAST).isInput()) {
            boolean isSidePowered = isSidePowered(sLevel, pos, state, Direction.EAST);
            boolean sidePoweredState = state.getValue(E_POWERED);
            if(isSidePowered != sidePoweredState) {
                doUpdate = true;
                state = state.setValue(E_POWERED, isSidePowered);
            }
        }

        if(doUpdate) sLevel.setBlock(pos, state, 2);
    }

    @Override
    public MapCodec<? extends CircuitedPanelBlock> codec() { return simpleCodec(CircuitedPanelBlock::new); }

    //Helper functions
    @Override
    public BlockState getDefaultState() {
        return super.getDefaultState()
                .setValue(NORTH, CircuitConnection.NONE)
                .setValue(WEST, CircuitConnection.NONE)
                .setValue(SOUTH, CircuitConnection.NONE)
                .setValue(EAST, CircuitConnection.NONE);
    }

    public static boolean isSideNone(BlockState state, Direction side) {
        if(!(state.getBlock() instanceof CircuitedPanelBlock)) return false;
        if(DirectionUtils.onYAxis(side)) return false;
        return state.getValue(getConnPropFromSide(side)).isNone();
    }

    public static boolean isSideInput(BlockState state, Direction side) {
        if(!(state.getBlock() instanceof CircuitedPanelBlock)) return false;
        if(DirectionUtils.onYAxis(side)) return false;
        return state.getValue(getConnPropFromSide(side)).isInput();
    }

    public static boolean isSideOutput(BlockState state, Direction side) {
        if(!(state.getBlock() instanceof CircuitedPanelBlock)) return false;
        if(DirectionUtils.onYAxis(side)) return false;
        return state.getValue(getConnPropFromSide(side)).isOutput();
    }

    public static boolean isSideJoint(BlockState state, Direction side) {
        if(!(state.getBlock() instanceof CircuitedPanelBlock)) return false;
        if(DirectionUtils.onYAxis(side)) return false;
        return state.getValue(getConnPropFromSide(side)).isJoint();
    }

    public static EnumProperty<CircuitConnection> getConnPropFromSide(Direction side) {
        if(DirectionUtils.onYAxis(side)) return null;
        if(side == Direction.NORTH) return NORTH;
        if(side == Direction.WEST) return WEST;
        if(side == Direction.SOUTH) return SOUTH;
        return EAST;
    }

    public static CircuitConnection readConnectionFromSide(BlockState state, Direction side) {
        if(!(state.getBlock() instanceof CircuitedPanelBlock)) return null;
        if(DirectionUtils.onYAxis(side)) return null;
        return state.getValue(getConnPropFromSide(side));
    }

    public static BooleanProperty getPoweredPropFromSide(Direction side) {
        if(DirectionUtils.onYAxis(side)) return null;
        if(side == Direction.NORTH) return N_POWERED;
        if(side == Direction.WEST) return W_POWERED;
        if(side == Direction.SOUTH) return S_POWERED;
        return E_POWERED;
    }

    public static boolean readPoweredFromSide(BlockState state, Direction side) {
        if(!(state.getBlock() instanceof CircuitedPanelBlock)) return false;
        if(DirectionUtils.onYAxis(side)) return false;
        return state.getValue(getPoweredPropFromSide(side));
    }

    public static boolean isSidePowered(Level level, BlockPos pos, BlockState state, Direction side) {
        if(!isSideInput(state, side)) return false;

        Direction dir = getDirFromSide(state, side);
        BlockPos sidedPos = pos.relative(dir);
        BlockState rState = level.getBlockState(sidedPos);

        if(level.getSignal(sidedPos, dir) > 0) return true;
        if(rState.is(Blocks.REDSTONE_WIRE)) return rState.getValue(RedStoneWireBlock.POWER) > 0;
        return false;
    }


    public static int getOutputSignal(BlockState state, Direction side) {
        if(!(state.getBlock() instanceof CircuitedPanelBlock)) return 0;
        if(!isSideOutput(state, side)) return 0;
        if(side == Direction.NORTH && state.getValue(N_POWERED)) return 15;
        if(side == Direction.WEST && state.getValue(W_POWERED)) return 15;
        if(side == Direction.SOUTH && state.getValue(S_POWERED)) return 15;
        if(side == Direction.EAST && state.getValue(E_POWERED)) return 15;
        return 0;
    }

    public void updateNeighborsAtOutputs(Level level, BlockPos pos, BlockState state) {
        EnumSet<Direction> dirs = EnumSet.of(
                getDirFromSide(state.getValue(FACE), Direction.NORTH),
                getDirFromSide(state.getValue(FACE), Direction.WEST),
                getDirFromSide(state.getValue(FACE), Direction.SOUTH),
                getDirFromSide(state.getValue(FACE), Direction.EAST));
        if(!state.getValue(NORTH).isOutput()) dirs.remove(getDirFromSide(state.getValue(FACE), Direction.NORTH));
        if(!state.getValue(WEST).isOutput()) dirs.remove(getDirFromSide(state.getValue(FACE), Direction.WEST));
        if(!state.getValue(SOUTH).isOutput()) dirs.remove(getDirFromSide(state.getValue(FACE), Direction.SOUTH));
        if(!state.getValue(EAST).isOutput()) dirs.remove(getDirFromSide(state.getValue(FACE), Direction.EAST));
        if (!ForgeEventFactory.onNeighborNotify(level, pos, level.getBlockState(pos), dirs, false).isCanceled()) {
            for(Direction dir : dirs) {
                level.neighborChanged(pos.relative(dir), this, pos);
                level.updateNeighborsAtExceptFromFacing(pos.relative(dir), this, dir.getOpposite());
            }
        }
    }
}
