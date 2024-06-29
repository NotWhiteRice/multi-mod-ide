package io.github.notwhiterice.deprecated.circuitsmod040alpha.block;

import com.mojang.serialization.MapCodec;
import io.github.notwhiterice.deprecated.endlessskies030alpha.core.block.CircuitedPanelBlock;
import io.github.notwhiterice.deprecated.endlessskies030alpha.core.block.IDiPanel;
import io.github.notwhiterice.deprecated.endlessskies030alpha.core.block.state.properties.CircuitConnection;
import io.github.notwhiterice.deprecated.endlessskies030alpha.core.util.DirectionUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

import java.util.Objects;

public class RedstonePanelBlock extends CircuitedPanelBlock {
    //Static variables
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
    public static final BooleanProperty N_ORIGIN = BooleanProperty.create("n_origin");
    public static final BooleanProperty W_ORIGIN = BooleanProperty.create("w_origin");
    public static final BooleanProperty S_ORIGIN = BooleanProperty.create("s_origin");
    public static final BooleanProperty E_ORIGIN = BooleanProperty.create("e_origin");

    //Constructors
    public RedstonePanelBlock() {
        super(BlockBehaviour.Properties.ofFullCopy(Blocks.REPEATER));
        this.registerDefaultState(this.getDefaultState());
    }
    public RedstonePanelBlock(BlockBehaviour.Properties prop) { this(); }

    //Vanilla overrides
    @Override
    public void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACE, POWERED, NORTH, N_ORIGIN, WEST, W_ORIGIN, SOUTH, S_ORIGIN, EAST, E_ORIGIN);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState state = super.getStateForPlacement(context);
        BlockPos pos = context.getClickedPos();
        Level level = context.getLevel();
        /*
        Sets properties for each connection and the powered flag
        TODO: Implement setting connection to input/output if connected to input/output
         */
        for(Direction side : DirectionUtils.cardinals) {
            Direction dir = getDirFromSide(state, side);
            BlockPos sidedPos = pos.relative(dir);
            BlockState blockState = level.getBlockState(sidedPos);
            if(blockState.getBlock() instanceof Basic2to1CircuitBlock || blockState.getBlock() instanceof NotGateBlock) {
                if(blockState.getValue(IDiPanel.FACING) == side.getOpposite()) {
                    state = state.setValue(getConnPropFromSide(side), CircuitConnection.INPUT);
                }
                if(blockState.getBlock() instanceof Basic2to1CircuitBlock) {
                    if(DirectionUtils.hasOneAxis(side.getClockWise(), blockState.getValue(IDiPanel.FACING))) {
                        state = state.setValue(getConnPropFromSide(side), CircuitConnection.OUTPUT);
                    }
                } else {
                    if(blockState.getValue(IDiPanel.FACING) == side) {
                        state = state.setValue(getConnPropFromSide(side), CircuitConnection.OUTPUT);
                    }
                }
            } else if(blockState.canRedstoneConnectTo(level, sidedPos, dir.getOpposite())) {
                state = state.setValue(getConnPropFromSide(side), CircuitConnection.JOINT);
                if(isSidePowered(level, pos, state, dir)) {
                    state = state.setValue(POWERED, true);
                    state = state.setValue(getOriginPropFromSide(side), true);
                }
            }
        }
        return state;
    }

    @Override
    public boolean canConnectRedstone(BlockState state, BlockGetter world, BlockPos pos, Direction dir) {
        Direction side = getSideFromDir(state, dir);
        return !DirectionUtils.onYAxis(side);
    }

    @Override
    public int getSignal(BlockState state, BlockGetter level, BlockPos pos, Direction dir) {
        Direction side = getSideFromDir(state, dir).getOpposite();
        if(!isSideOutput(state, side)) return 0;
        return getOutputSignal(state, side);
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource rand) {
        boolean doUpdate = false;
        boolean isPowered = false;

        /*
        Adjusting redstone connections
        TODO: Implement connecting sides as input/output if connected to another input/output
         */
        for(Direction side : DirectionUtils.cardinals) {
            Direction dir = getDirFromSide(state, side);
            BlockPos sidedPos = pos.relative(dir);
            BlockState blockState = level.getBlockState(sidedPos);
            CircuitConnection conn = CircuitConnection.NONE;
            boolean updateConn = false;

            if(blockState.getBlock() instanceof CircuitedPanelBlock) {
                CircuitConnection other = readConnectionFromSide(blockState, side.getOpposite());
                if(other.isJoint()) conn = CircuitConnection.JOINT;
                else if(other.isIO()) conn = other.invertIO();
                if(other.isIO()) updateConn = true;
            } else {
                boolean canConnect = blockState.canRedstoneConnectTo(level, sidedPos, dir);
                boolean isConnected = state.getValue(getConnPropFromSide(side)).isIO();
                CircuitConnection def = CircuitConnection.JOINT;
                if(blockState.getBlock() instanceof Basic2to1CircuitBlock || blockState.getBlock() instanceof NotGateBlock) {
                    if(blockState.getValue(IDiPanel.FACING) == side.getOpposite()) {
                        state = state.setValue(getConnPropFromSide(side), CircuitConnection.INPUT);
                        canConnect=true;
                    }
                    if(blockState.getBlock() instanceof Basic2to1CircuitBlock) {
                        if(DirectionUtils.hasOneAxis(side.getClockWise(), blockState.getValue(IDiPanel.FACING))) {
                            def = CircuitConnection.OUTPUT;
                            canConnect=true;
                        }
                    } else {
                        if(blockState.getValue(IDiPanel.FACING) == side) {
                            def = CircuitConnection.OUTPUT;
                            canConnect=true;
                        }
                    }

                    if(blockState.getValue(IDiPanel.FACING) == side.getOpposite()) {
                        def = CircuitConnection.INPUT;
                        canConnect=true;
                    }
                }
                if(canConnect != isConnected) {
                    updateConn = true;
                    if (canConnect) conn = def;
                }
            }

            if (updateConn) {
                doUpdate = true;
                state = state.setValue(Objects.requireNonNull(getConnPropFromSide(side)), conn);
            }
        }

        /*
        Checking all sides and clearing origin flags if not an input
         */
        for(Direction side : DirectionUtils.cardinals) {
            if(!isSideInput(state, side) && readOriginFromSide(state, side)) {
                state = state.setValue(getOriginPropFromSide(side), false);
            }
        }

        /*
        Setting origin flags and powered flag
         */
        for(Direction side : DirectionUtils.cardinals) {
            if(isSideInput(state, side)) {
                Direction dir = getDirFromSide(state, side);
                boolean isSidePowered = isSidePowered(level, pos, state, side);
                if(isSidePowered) {
                    isPowered = true;
                    boolean hasOrigin = false;
                    for(Direction side1 : DirectionUtils.cardinals) if(readOriginFromSide(state, side1)) hasOrigin = true;
                    if(!hasOrigin) {
                        doUpdate = true;
                        state = state.setValue(getOriginPropFromSide(side), true);
                    }
                } else {
                    if(readOriginFromSide(state, side)) {
                        doUpdate = true;
                        state = state.setValue(getOriginPropFromSide(side), false);
                    }
                }
            }
        }

        /*
        Adjusting the powered flag in blockState
         */
        if(isPowered != state.getValue(POWERED)) {
            doUpdate = true;
            state = state.setValue(POWERED, isPowered);
        }

        if(doUpdate) level.setBlock(pos, state, 2);
    }

    @Override
    public MapCodec<RedstonePanelBlock> codec() { return simpleCodec(RedstonePanelBlock::new); }

    //Helper functions
    @Override
    public BlockState getDefaultState() {
        return super.getDefaultState()
                .setValue(POWERED, false)
                .setValue(N_ORIGIN, false)
                .setValue(W_ORIGIN, false)
                .setValue(S_ORIGIN, false)
                .setValue(E_ORIGIN, false);
    }

    public static BooleanProperty getOriginPropFromSide(Direction side) {
        if(DirectionUtils.onYAxis(side)) return null;
        if(side == Direction.NORTH) return N_ORIGIN;
        if(side == Direction.WEST) return W_ORIGIN;
        if(side == Direction.SOUTH) return S_ORIGIN;
        return E_ORIGIN;
    }

    public static boolean readOriginFromSide(BlockState state, Direction side) {
        if(!(state.getBlock() instanceof CircuitedPanelBlock)) return false;
        if(DirectionUtils.onYAxis(side)) return false;
        return state.getValue(getOriginPropFromSide(side));
    }

    public static int getOutputSignal(BlockState state, Direction side) {
        if(!(state.getBlock() instanceof RedstonePanelBlock)) return 0;
        if(!isSideOutput(state, side)) return 0;
        if(readOriginFromSide(state, side)) return 0;
        if(state.getValue(POWERED)) return 15;
        return 0;
    }
}
