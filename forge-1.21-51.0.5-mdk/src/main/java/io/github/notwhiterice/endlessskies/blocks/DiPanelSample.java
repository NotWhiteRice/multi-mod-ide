package io.github.notwhiterice.endlessskies.blocks;

import com.mojang.serialization.MapCodec;
import io.github.notwhiterice.endlessskies.core.block.IDiPanel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class DiPanelSample extends Block implements IDiPanel {
    public DiPanelSample() { super(BlockBehaviour.Properties.ofFullCopy(Blocks.REPEATER)); }
    public DiPanelSample(Properties prop) {
        this();
    }

    @Override
    public void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        IDiPanel.super.prepareStateDefinition(builder);
    }

    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return _getShape(pState);
    }

    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState state = IDiPanel.super.prepareStateForPlacement(this.defaultBlockState(), context);
        return state;
    }

    public MapCodec<DiPanelSample> codec() { return simpleCodec(DiPanelSample::new); }

}
