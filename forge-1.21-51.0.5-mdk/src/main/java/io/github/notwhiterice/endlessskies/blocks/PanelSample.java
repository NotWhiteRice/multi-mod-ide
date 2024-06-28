package io.github.notwhiterice.endlessskies.blocks;

import com.mojang.serialization.MapCodec;
import io.github.notwhiterice.endlessskies.core.block.IPanel;
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

public class PanelSample extends Block implements IPanel {
    public PanelSample() {
        super(BlockBehaviour.Properties.ofFullCopy(Blocks.REPEATER));
        this.registerDefaultState(this.prepareDefaultState(this.stateDefinition.any()));
    }
    public PanelSample(Properties prop) {
        this();
    }

    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return _getShape(pState);
    }

    public void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) { this.prepareStateDefinition(builder); }

    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState state = IPanel.super.prepareStateForPlacement(this.defaultBlockState(), context);
        return state;
    }

    public MapCodec<PanelSample> codec() { return simpleCodec(PanelSample::new); }
}
