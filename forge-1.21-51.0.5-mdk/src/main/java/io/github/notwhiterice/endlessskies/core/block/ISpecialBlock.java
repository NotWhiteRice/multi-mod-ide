package io.github.notwhiterice.endlessskies.core.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;

public interface ISpecialBlock extends IBlock {
    void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder);

    BlockState getStateForPlacement(BlockPlaceContext context);

    MapCodec<? extends Block> codec();
}
