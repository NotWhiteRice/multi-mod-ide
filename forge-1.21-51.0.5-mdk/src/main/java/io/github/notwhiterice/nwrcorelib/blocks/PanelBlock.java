package io.github.notwhiterice.nwrcorelib.blocks;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public abstract class PanelBlock extends BasicBlock {
    public static final DirectionProperty FACE =
            DirectionProperty.create("face", new Direction[]
                    {Direction.UP, Direction.DOWN, Direction.NORTH, Direction.WEST, Direction.SOUTH, Direction.EAST});

    public PanelBlock(Properties prop) { super(prop); }

    public static VoxelShape createCenteredBox(Direction dir, double outside, double height) {
        return switch (dir) {
            case DOWN -> Block.box(outside, 0.0, outside, 16.0 - outside, height, 16.0 - outside);
            case UP -> Block.box(outside, 16.0 - height, outside, 16.0 - outside, 16.0, 16.0 - outside);
            case NORTH -> Block.box(outside, outside, 0.0, 16.0 - outside, 16.0 - outside, height);
            case WEST -> Block.box(0.0, outside, outside, height, 16.0 - outside, 16.0 - outside);
            case SOUTH -> Block.box(outside, outside, 16.0 - height, 16.0 - outside, 16.0 - outside, 16.0);
            case EAST -> Block.box(16.0 - height, outside, outside, 16.0, 16.0 - outside, 16.0 - outside);
        };
    }

    public VoxelShape getShape(BlockState blockState, BlockGetter getter, BlockPos blockPos, CollisionContext context) {
        Direction face = blockState.getValue(FACE);
        return createCenteredBox(face, 0.0, 2.0);
    }

    public void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACE);
    }

    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACE, context.getClickedFace().getOpposite());
    }

    public abstract MapCodec<? extends PanelBlock> codec();
}
