package io.github.notwhiterice.endlessskies.block.factory;

import com.mojang.serialization.MapCodec;
import io.github.notwhiterice.endlessskies.block.entity.factory.BasicBlockEntity;
import io.github.notwhiterice.endlessskies.capabilities.ESCapabilities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public abstract class BasicEntityBlock extends BasicBlock implements EntityBlock {
    public BasicEntityBlock(Block.Properties prop) {
        super(prop);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return context.container.factory.create(pos, state);
    }

    @Override
    protected void neighborChanged(BlockState state, Level level, BlockPos pos, Block neighbor, BlockPos neighborPos, boolean movedByPiston) {
        super.neighborChanged(state, level, pos, neighbor, neighborPos, movedByPiston);
        BlockEntity bEntity = level.getBlockEntity(pos);

        bEntity.getCapability(ESCapabilities.HEAT_HANDLER).ifPresent(heatHandler0 -> {
            heatHandler0.clearHeatSourceForSlot(0, false);
            for(Direction side : Direction.values()) {
                BlockPos sidedPos = pos.relative(side);
                BlockEntity sidedTile = level.getBlockEntity(sidedPos);
                if (sidedTile != null) {
                    sidedTile.getCapability(ESCapabilities.HEAT_HANDLER, side.getOpposite()).ifPresent(heatHandler1 -> {
                        heatHandler0.acceptSourceInSlot(0, heatHandler1.getStackInSlot(0), false);
                    });
                }
            }
        });
    }

    @Nullable
    @Override
    public <U extends BlockEntity> BlockEntityTicker<U> getTicker(Level level, BlockState state, BlockEntityType<U> bEntityType) {
        if(level.isClientSide || !context.hasContainer()) return null;
        BlockEntity entity = bEntityType.create(new BlockPos(1, 1, 1), state);

        if(entity instanceof BasicBlockEntity) {
            entity.setRemoved();
            return createTickerHelper(bEntityType, context.container.get(),
                    (pLevel, pos, pState, bEntity) -> ((BasicBlockEntity) bEntity).tick(pLevel, pos, pState));
        }

        entity.setRemoved();
        return null;
    }

    protected boolean triggerEvent(BlockState state, Level level, BlockPos pos, int id, int param) {
        super.triggerEvent(state, level, pos, id, param);
        BlockEntity container = level.getBlockEntity(pos);
        return container == null ? false : container.triggerEvent(id, param);
    }

    @Nullable
    protected MenuProvider getMenuProvider(BlockState state, Level level, BlockPos pos) {
        BlockEntity container = level.getBlockEntity(pos);
        return container instanceof MenuProvider ? (MenuProvider) container : null;
    }

    @Nullable
    protected static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> createTickerHelper(BlockEntityType<A> serverType, BlockEntityType<E> clientType, BlockEntityTicker<? super E> ticker) {
        return clientType == serverType ? (BlockEntityTicker<A>) ticker : null;
    }


    protected abstract MapCodec<? extends BasicEntityBlock> codec();
}
