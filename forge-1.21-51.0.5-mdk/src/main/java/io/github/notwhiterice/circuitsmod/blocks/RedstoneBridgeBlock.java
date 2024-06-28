package io.github.notwhiterice.circuitsmod.blocks;

import com.mojang.serialization.MapCodec;
import io.github.notwhiterice.endlessskies.core.block.IPanel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RedStoneWireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.ticks.TickPriority;
import net.minecraftforge.event.ForgeEventFactory;

import java.util.EnumSet;

public class RedstoneBridgeBlock extends Block implements IPanel {
    public static final BooleanProperty X_POWERED = BooleanProperty.create("x_powered");
    public static final BooleanProperty Y_POWERED = BooleanProperty.create("y_powered");
    public static final BooleanProperty N_POWERED = BooleanProperty.create("n_powered");
    public static final BooleanProperty W_POWERED = BooleanProperty.create("w_powered");
    public static final BooleanProperty S_POWERED = BooleanProperty.create("s_powered");
    public static final BooleanProperty E_POWERED = BooleanProperty.create("e_powered");

    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return _getShape(pState);
    }

    public RedstoneBridgeBlock() {
        super(Properties.ofFullCopy(Blocks.REPEATER));
        this.registerDefaultState(this.prepareDefaultState(this.stateDefinition.any()).setValue(X_POWERED, false).setValue(Y_POWERED, false).setValue(N_POWERED, false).setValue(W_POWERED, false).setValue(S_POWERED, false).setValue(E_POWERED, false));
    }

    public RedstoneBridgeBlock(Properties prop) {
        this();
    }

    public boolean isPowered(Level level, BlockPos pos, BlockState state, Direction dir) {
        Direction face = state.getValue(FACE);
        Direction sDir = getRelativeDir(face, dir);
        BlockPos sPos = pos.relative(sDir);
        BlockState sState = level.getBlockState(sPos);

        if(level.getSignal(sPos, sDir) > 0) return true;
        if(sState.is(Blocks.REDSTONE_WIRE)) return sState.getValue(RedStoneWireBlock.POWER) > 0;
        return false;
    }

    public void tick(BlockState blockState, ServerLevel level, BlockPos blockPos, RandomSource random) {
        boolean xPoweredC = blockState.getValue(X_POWERED);
        boolean yPoweredC = blockState.getValue(Y_POWERED);
        boolean nPoweredC = blockState.getValue(N_POWERED);
        boolean wPoweredC = blockState.getValue(W_POWERED);
        boolean sPoweredC = blockState.getValue(S_POWERED);
        boolean ePoweredC = blockState.getValue(E_POWERED);
        boolean isNPowered = isPowered(level, blockPos, blockState, Direction.NORTH);
        boolean isWPowered = isPowered(level, blockPos, blockState, Direction.WEST);
        boolean isSPowered = isPowered(level, blockPos, blockState, Direction.SOUTH);
        boolean isEPowered = isPowered(level, blockPos, blockState, Direction.EAST);
        boolean isXPowered = isWPowered || isEPowered;
        boolean isYPowered = isNPowered || isSPowered;
        boolean updateBlock = false;
        boolean tickBlock = false;
        TickPriority tickPriority = TickPriority.VERY_HIGH;

        if(xPoweredC != isXPowered) {
            blockState = blockState.setValue(X_POWERED, isXPowered);
            updateBlock = true;
        }
        if(yPoweredC != isYPowered) {
            blockState = blockState.setValue(Y_POWERED, isYPowered);
            updateBlock = true;
        }

        if(isNPowered && !nPoweredC && isSPowered && !sPoweredC) isSPowered = false;
        if(isWPowered && !wPoweredC && isEPowered && !ePoweredC) isEPowered = false;

        if((nPoweredC && sPoweredC) || (wPoweredC && ePoweredC)) {
            blockState = blockState.setValue(N_POWERED, false);
            nPoweredC = false;
            blockState = blockState.setValue(W_POWERED, false);
            wPoweredC = false;
            blockState = blockState.setValue(S_POWERED, false);
            sPoweredC = false;
            blockState = blockState.setValue(E_POWERED, false);
            ePoweredC = false;
            updateBlock = true;
            tickBlock = true;
        }

        if(nPoweredC && !isNPowered) {
            blockState = blockState.setValue(N_POWERED, false);
            updateBlock = true;
        } else if(isNPowered && !sPoweredC && !isSPowered) {
            blockState = blockState.setValue(N_POWERED, true);
            updateBlock = true;
            if(!nPoweredC) tickBlock = true;
        }
        if(wPoweredC && !isWPowered) {
            blockState = blockState.setValue(W_POWERED, false);
            updateBlock = true;
        } else if(isWPowered && !ePoweredC) {
            blockState = blockState.setValue(W_POWERED, true);
            updateBlock = true;
            if(!wPoweredC) tickBlock = true;
        }
        if(sPoweredC && !isSPowered) {
            blockState = blockState.setValue(S_POWERED, false);
            updateBlock = true;
        } else if(isSPowered && !nPoweredC) {
            blockState = blockState.setValue(S_POWERED, true);
            updateBlock = true;
            if(!sPoweredC) tickBlock = true;
        }
        if(ePoweredC && !isEPowered) {
            blockState = blockState.setValue(E_POWERED, false);
            updateBlock = true;
        } else if(isEPowered && !wPoweredC) {
            blockState = blockState.setValue(E_POWERED, true);
            updateBlock = true;
            if(!ePoweredC) tickBlock = true;
        }

        if(updateBlock) level.setBlock(blockPos, blockState, 2);
        if(tickBlock) level.scheduleTick(blockPos, this, 2, tickPriority);
    }

    public void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        this.prepareStateDefinition(builder).add(X_POWERED, Y_POWERED, N_POWERED, W_POWERED, S_POWERED, E_POWERED);
    }

    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState state = IPanel.super.prepareStateForPlacement(this.defaultBlockState(), context);
        return state;
    }

    public int getDirectSignal(BlockState blockState, BlockGetter level, BlockPos blockPos, Direction dir) {
        return blockState.getSignal(level, blockPos, dir);
    }

    protected int getSignal(BlockState blockState, BlockGetter level, BlockPos blockPos, Direction receivingSide) {
        boolean isSide = receivingSide != getRelativeDir(blockState.getValue(FACE), Direction.DOWN) && receivingSide != getRelativeDir(blockState.getValue(FACE), Direction.UP);
        if(!isSide) return 0;
        boolean isOrigin = receivingSide == Direction.NORTH ? blockState.getValue(N_POWERED)
                : receivingSide == Direction.WEST ? blockState.getValue(W_POWERED)
                : receivingSide == Direction.SOUTH ? blockState.getValue(S_POWERED)
                : blockState.getValue(E_POWERED);
        if(!isOrigin) return 0;
        BlockPos inPos = blockPos.relative(receivingSide);
        BlockState inState = level.getBlockState(inPos);
        int signal = Math.max(inState.getSignal(level, inPos, receivingSide), inState.getBlock() instanceof RedStoneWireBlock ? inState.getValue(RedStoneWireBlock.POWER) : 0);
        return signal;
    }


    public boolean canConnectRedstone(BlockState state, BlockGetter world, BlockPos pos, Direction side) {
        return side != getRelativeDir(state.getValue(FACE), Direction.DOWN) && side != getRelativeDir(state.getValue(FACE), Direction.UP);
    }

    public MapCodec<RedstoneBridgeBlock> codec() { return simpleCodec(RedstoneBridgeBlock::new); }

    public void neighborChanged(BlockState blockState, Level level, BlockPos pos1, Block block, BlockPos pos2, boolean isPistonable) {
        boolean isNPowered = this.isPowered(level, pos1, blockState, Direction.SOUTH);
        boolean isWPowered = this.isPowered(level, pos1, blockState, Direction.EAST);
        boolean isSPowered = this.isPowered(level, pos1, blockState, Direction.NORTH);
        boolean isEPowered = this.isPowered(level, pos1, blockState, Direction.WEST);
        if ((blockState.getValue(N_POWERED) != isNPowered || blockState.getValue(W_POWERED) != isWPowered | blockState.getValue(S_POWERED) != isSPowered || blockState.getValue(E_POWERED) != isEPowered) && !level.getBlockTicks().willTickThisTick(pos1, this)) {
            TickPriority tickpriority = TickPriority.HIGH;
            if (blockState.getValue(N_POWERED) || blockState.getValue(W_POWERED) || blockState.getValue(S_POWERED) || blockState.getValue(E_POWERED)) {
                tickpriority = TickPriority.VERY_HIGH;
            }

            level.scheduleTick(pos1, this, 1, tickpriority);
        }
    }

    protected boolean isSignalSource(BlockState pState) {
        return true;
    }

    protected void onPlace(BlockState pState, Level pLevel, BlockPos pPos, BlockState pOldState, boolean pIsMoving) {
        this.updateSideNeighbors(pLevel, pPos, pState);
    }

    protected void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (!pIsMoving && !pState.is(pNewState.getBlock())) {
            super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
            this.updateSideNeighbors(pLevel, pPos, pState);
        }
    }


    protected void updateSideNeighbors(Level pLevel, BlockPos pPos, BlockState pState) {

        EnumSet<Direction> neighborDir = EnumSet.of(
                getRelativeDir(pState.getValue(FACE), Direction.NORTH),
                getRelativeDir(pState.getValue(FACE), Direction.WEST),
                getRelativeDir(pState.getValue(FACE), Direction.SOUTH),
                getRelativeDir(pState.getValue(FACE), Direction.EAST));
        if (!ForgeEventFactory.onNeighborNotify(pLevel, pPos, pLevel.getBlockState(pPos), neighborDir, false).isCanceled()) {
            pLevel.neighborChanged(pPos.relative(getRelativeDir(pState.getValue(FACE), Direction.NORTH)), this, pPos);
            pLevel.updateNeighborsAtExceptFromFacing(pPos.relative(getRelativeDir(pState.getValue(FACE), Direction.NORTH)), this, getRelativeDir(pState.getValue(FACE), Direction.SOUTH));
            pLevel.neighborChanged(pPos.relative(getRelativeDir(pState.getValue(FACE), Direction.WEST)), this, pPos);
            pLevel.updateNeighborsAtExceptFromFacing(pPos.relative(getRelativeDir(pState.getValue(FACE), Direction.WEST)), this, getRelativeDir(pState.getValue(FACE), Direction.EAST));
            pLevel.neighborChanged(pPos.relative(getRelativeDir(pState.getValue(FACE), Direction.SOUTH)), this, pPos);
            pLevel.updateNeighborsAtExceptFromFacing(pPos.relative(getRelativeDir(pState.getValue(FACE), Direction.SOUTH)), this, getRelativeDir(pState.getValue(FACE), Direction.NORTH));
            pLevel.neighborChanged(pPos.relative(getRelativeDir(pState.getValue(FACE), Direction.EAST)), this, pPos);
            pLevel.updateNeighborsAtExceptFromFacing(pPos.relative(getRelativeDir(pState.getValue(FACE), Direction.EAST)), this, getRelativeDir(pState.getValue(FACE), Direction.WEST));
        }
    }
}