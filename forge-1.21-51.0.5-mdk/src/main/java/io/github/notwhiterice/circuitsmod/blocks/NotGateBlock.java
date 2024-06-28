package io.github.notwhiterice.circuitsmod.blocks;

import com.mojang.serialization.MapCodec;
import io.github.notwhiterice.endlessskies.core.block.IDiPanel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DiodeBlock;
import net.minecraft.world.level.block.RedStoneWireBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.ticks.TickPriority;
import net.minecraftforge.event.ForgeEventFactory;

import java.util.EnumSet;

public class NotGateBlock extends Block implements IDiPanel {
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;

    public NotGateBlock() {
        super(BlockBehaviour.Properties.ofFullCopy(Blocks.REPEATER));
        this.registerDefaultState(IDiPanel.super.prepareDefaultState(this.stateDefinition.any()).setValue(POWERED, false));
    }
    public NotGateBlock(Properties prop) {
        this();
    }

    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return _getShape(pState);
    }

    @Override
    public void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        IDiPanel.super.prepareStateDefinition(builder).add(POWERED);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState state = IDiPanel.super.prepareStateForPlacement(this.defaultBlockState(), context);
        return state.setValue(POWERED, isPowered(context.getLevel(), context.getClickedPos(), state));
    }
    public int getDirectSignal(BlockState blockState, BlockGetter level, BlockPos blockPos, Direction dir) {
        return blockState.getSignal(level, blockPos, dir);
    }

    protected int getSignal(BlockState blockState, BlockGetter level, BlockPos blockPos, Direction direction) {
        if(getRelativeDir(blockState.getValue(FACE), blockState.getValue(FACING), Direction.SOUTH) != direction) return 0;
        if(blockState.getValue(POWERED)) return 0;
        return 15;
    }

    public void tick(BlockState blockState, ServerLevel level, BlockPos blockPos, RandomSource random) {
        boolean isOn = (Boolean)blockState.getValue(POWERED);
        boolean isPowered = this.isPowered(level, blockPos, blockState);
        if (isOn != isPowered) level.setBlock(blockPos, (BlockState)blockState.setValue(POWERED, isPowered), 2);
    }

    public void neighborChanged(BlockState blockState, Level level, BlockPos blockPos, Block block, BlockPos originPos, boolean isPistonable) {
        boolean isOn = blockState.getValue(POWERED);
        boolean isPowered = this.isPowered(level, blockPos, blockState);
        if (isOn != isPowered && !level.getBlockTicks().willTickThisTick(blockPos, this)) {
            TickPriority tickpriority = TickPriority.HIGH;
            if (this.shouldPrioritize(level, blockPos, blockState)) {
                tickpriority = TickPriority.EXTREMELY_HIGH;
            } else if (isOn) {
                tickpriority = TickPriority.VERY_HIGH;
            }

            level.scheduleTick(blockPos, this, 0, tickpriority);
        }
    }

    protected boolean isPowered(Level level, BlockPos blockPos, BlockState blockState) {
        Direction inDir = getRelativeDir(blockState.getValue(FACE), blockState.getValue(FACING), Direction.SOUTH);
        BlockPos inPos = blockPos.relative(inDir);
        BlockState inState = level.getBlockState(inPos);
        if(level.getSignal(inPos, inDir) > 0) return true;
        if(inState.is(Blocks.REDSTONE_WIRE)) return inState.getValue(RedStoneWireBlock.POWER) > 0;
        return false;
    }

    public boolean canConnectRedstone(BlockState state, BlockGetter world, BlockPos pos, Direction side) {
        return side == getRelativeDir(state.getValue(FACE), state.getValue(FACING), Direction.NORTH) || side == getRelativeDir(state.getValue(FACE), state.getValue(FACING), Direction.SOUTH);
    }

    protected void updateNeighborsInFront(Level pLevel, BlockPos pPos, BlockState pState) {
        Direction dir = getRelativeDir(pState.getValue(FACE), pState.getValue(FACING), Direction.SOUTH);
        BlockPos blockpos = pPos.relative(dir);
        if (!ForgeEventFactory.onNeighborNotify(pLevel, pPos, pLevel.getBlockState(pPos), EnumSet.of(dir), false).isCanceled()) {
            pLevel.neighborChanged(blockpos, this, pPos);
            pLevel.updateNeighborsAtExceptFromFacing(blockpos, this, dir.getOpposite());
        }
    }

    public boolean shouldPrioritize(BlockGetter pLevel, BlockPos pPos, BlockState pState) {
        Direction direction = getRelativeDir(pState.getValue(FACE), pState.getValue(FACING), Direction.NORTH);
        BlockState blockstate = pLevel.getBlockState(pPos.relative(direction));
        boolean isDiode = blockstate.getBlock() instanceof DiodeBlock
                || blockstate.getBlock() instanceof NotGateBlock
                || blockstate.getBlock() instanceof AndGateBlock
                || blockstate.getBlock() instanceof OrGateBlock
                || blockstate.getBlock() instanceof XorGateBlock
                || blockstate.getBlock() instanceof NandGateBlock
                || blockstate.getBlock() instanceof NorGateBlock
                || blockstate.getBlock() instanceof XnorGateBlock;
        return isDiode && getRelativeDir(pState.getValue(FACE), pState.getValue(FACING), Direction.SOUTH) != direction;
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

    protected boolean isSignalSource(BlockState pState) {
        return true;
    }

    public MapCodec<NotGateBlock> codec() { return simpleCodec(NotGateBlock::new); }

}
