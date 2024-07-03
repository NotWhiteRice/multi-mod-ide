package io.github.notwhiterice.endlessskies.block.base;

import io.github.notwhiterice.endlessskies.block.entity.base.BasicTileEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public abstract class BasicEntityBlock extends BaseEntityBlock {
    private Class<? extends BlockEntity> bEntityClass = null;


    public BasicEntityBlock(Properties pProperties) { super(pProperties); }

    @Override
    public RenderShape getRenderShape(BlockState state) { return RenderShape.MODEL; }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        if(bEntityClass == null) throw new IllegalStateException("Block entity must be defined with prepareBlockEntity(class)");
        try {
            return bEntityClass.getConstructor(BlockPos.class, BlockState.class).newInstance(blockPos, blockState);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean wasPistoned) {
        if(state.getBlock() != newState.getBlock()) {
            BlockEntity bEntity = level.getBlockEntity(pos);
            if(bEntity instanceof BasicTileEntity) {
                ((BasicTileEntity) bEntity).dropContents();
            }
        }
        super.onRemove(state, level, pos, newState, wasPistoned);
    }

    public void prepareBlockEntity(Class<? extends BlockEntity> bEntity) { bEntityClass = bEntity; }
}
