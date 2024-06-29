package io.github.notwhiterice.deprecated.circuitsmod040alpha.block;

import com.mojang.serialization.MapCodec;
import io.github.notwhiterice.deprecated.endlessskies030alpha.core.block.ICircuitBoard;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DiodeBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.ticks.TickPriority;
import net.minecraftforge.event.ForgeEventFactory;

import java.util.EnumSet;


public abstract class Basic2to1CircuitBlock extends Block implements ICircuitBoard {
    public static final BooleanProperty LEFT = BooleanProperty.create("left");
    public static final BooleanProperty RIGHT = BooleanProperty.create("right");

    public Basic2to1CircuitBlock(Properties prop) {
        super(prop);
        this.registerDefaultState(ICircuitBoard.super.prepareDefaultState(this.stateDefinition.any()).setValue(LEFT, false).setValue(RIGHT, false));
    }

    @Override
    public boolean useSideForInputs(Direction dir) {
        return dir == Direction.WEST || dir == Direction.EAST;
    }


    @Override
    public void tick(BlockState blockState, ServerLevel level, BlockPos blockPos, RandomSource random) {
        boolean isLeftOn = blockState.getValue(LEFT);
        boolean isRightOn = blockState.getValue(RIGHT);
        boolean isLeftPowered = this.isSidePowered(level, blockPos, blockState, Direction.WEST);
        boolean isRightPowered = this.isSidePowered(level, blockPos, blockState, Direction.EAST);
        boolean updateBlock = false;

        if (isLeftOn != isLeftPowered) {
            blockState = blockState.setValue(LEFT, isLeftPowered);
            updateBlock = true;
        }
        if (isRightOn != isRightPowered){
            blockState = blockState.setValue(RIGHT, isRightPowered);
            updateBlock = true;
        }


        if(updateBlock) level.setBlock(blockPos, blockState, 2);
    }

    @Override
    public boolean canConnectRedstone(BlockState state, BlockGetter world, BlockPos pos, Direction side) {
        return ICircuitBoard.super._canConnectRedstone(state, side);
    }

    public int getDirectSignal(BlockState blockState, BlockGetter level, BlockPos blockPos, Direction dir) {
        return blockState.getSignal(level, blockPos, dir);
    }

    public abstract boolean parseCircuit(boolean left, boolean right);

    protected int getSignal(BlockState blockState, BlockGetter level, BlockPos blockPos, Direction direction) {
        if(getRelativeDir(blockState.getValue(FACE), blockState.getValue(FACING), Direction.SOUTH) != direction) return 0;
        if(parseCircuit(blockState.getValue(LEFT), blockState.getValue(RIGHT))) return 15;
        return 0;
    }

    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return _getShape(pState);
    }

    @Override
    public void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        ICircuitBoard.super.prepareStateDefinition(builder).add(LEFT, RIGHT);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState blockState = prepareStateForPlacement(this.defaultBlockState(), context);
        assert blockState != null;
        return blockState.setValue(LEFT, isSidePowered(context.getLevel(), context.getClickedPos(), blockState, Direction.WEST)).setValue(RIGHT, isSidePowered(context.getLevel(), context.getClickedPos(), blockState, Direction.EAST));
    }



    public void neighborChanged(BlockState blockState, Level level, BlockPos blockPos, Block block, BlockPos originPos, boolean isPistonable) {
        boolean isLeftPowered = this.isSidePowered(level, blockPos, blockState, Direction.WEST);
        boolean isRightPowered = this.isSidePowered(level, blockPos, blockState, Direction.EAST);
        if ((blockState.getValue(LEFT) != isLeftPowered || blockState.getValue(RIGHT) != isRightPowered) && !level.getBlockTicks().willTickThisTick(blockPos, this)) {
            TickPriority tickpriority = TickPriority.HIGH;
            if (this.shouldPrioritize(level, blockPos, blockState)) {
                tickpriority = TickPriority.EXTREMELY_HIGH;
            } else if (blockState.getValue(LEFT) || blockState.getValue(RIGHT)) {
                tickpriority = TickPriority.VERY_HIGH;
            }

            level.scheduleTick(blockPos, this, 0, tickpriority);
        }
    }

    protected boolean isSignalSource(BlockState pState) {
        return true;
    }

    protected void onPlace(BlockState pState, Level pLevel, BlockPos pPos, BlockState pOldState, boolean pIsMoving) {
        this.updateNeighborsInFront(pLevel, pPos, pState);
    }

    protected void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (!pIsMoving && !pState.is(pNewState.getBlock())) {
            super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
            this.updateNeighborsInFront(pLevel, pPos, pState);
        }
    }

    public boolean shouldPrioritize(BlockGetter pLevel, BlockPos pPos, BlockState pState) {
        Direction direction = getRelativeDir(pState.getValue(FACE), pState.getValue(FACING), Direction.NORTH);
        BlockState blockstate = pLevel.getBlockState(pPos.relative(direction));
        boolean isDiode = blockstate.getBlock() instanceof DiodeBlock
                || blockstate.getBlock() instanceof RedstoneBridgeBlock
                || blockstate.getBlock() instanceof Basic2to1CircuitBlock;
        return isDiode && getRelativeDir(pState.getValue(FACE), pState.getValue(FACING), Direction.SOUTH) != direction;
    }

    protected void updateNeighborsInFront(Level pLevel, BlockPos pPos, BlockState pState) {
        Direction dir = getRelativeDir(pState.getValue(FACE), pState.getValue(FACING), Direction.NORTH);
        BlockPos blockpos = pPos.relative(dir);
        if (!ForgeEventFactory.onNeighborNotify(pLevel, pPos, pLevel.getBlockState(pPos), EnumSet.of(dir), false).isCanceled()) {
            pLevel.neighborChanged(blockpos, this, pPos);
            pLevel.updateNeighborsAtExceptFromFacing(blockpos, this, dir.getOpposite());
        }
    }

    public abstract MapCodec<? extends Basic2to1CircuitBlock> codec();

}