package io.github.notwhiterice.endlessskies.block.entity.factory;

import io.github.notwhiterice.endlessskies.block.factory.BasicEntityBlock;
import io.github.notwhiterice.endlessskies.block.factory.BlockContext;
import net.minecraft.core.BlockPos;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public abstract class BasicBlockEntity<T extends BasicBlockEntity<T, U>,U extends BasicEntityBlock<T>> extends BlockEntity {
    protected final BlockContext context;

    public BasicBlockEntity(BlockPos pos, BlockState state) {
        super(((U) state.getBlock()).context.container.getEntityType(), pos, state);
        context = ((U) state.getBlock()).context;
    }

    public abstract void tick(Level level, BlockPos pos, BlockState state);
}
