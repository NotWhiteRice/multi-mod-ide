package io.github.notwhiterice.endlessskies.block.factory;

import com.mojang.serialization.MapCodec;
import io.github.notwhiterice.endlessskies.block.entity.factory.BasicBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public abstract class BasicEntityBlock<T extends BasicBlockEntity> extends BasicBlock implements EntityBlock {
    public BasicEntityBlock(Block.Properties prop) {
        super(prop);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return context.container.factory.create(pos, state);
    }

    @Nullable
    @Override
    public <U extends BlockEntity> BlockEntityTicker<U> getTicker(Level level, BlockState state, BlockEntityType<U> bEntityType) {
        if(level.isClientSide || !context.hasContainer()) return null;

        return createTickerHelper(bEntityType, context.container.getEntityType(),
                (pLevel, pos, pState, bEntity) -> ((T) bEntity).tick(pLevel, pos, pState));
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
