package io.github.notwhiterice.circuitsmod.blocks;

import com.mojang.serialization.MapCodec;
import io.github.notwhiterice.nwrcorelib.blocks.BlockBase;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public abstract class FaceBlock extends BlockBase {
    public static final DirectionProperty FACE;

    public FaceBlock(BlockBehaviour.Properties prop) { super(prop); }

    protected abstract MapCodec<? extends FaceBlock> codec();

    public static VoxelShape createCenteredPanelBox(Direction dir, double outside, double height) {
        return switch (dir) {
            case DOWN -> Block.box(outside, 0.0, outside, 16.0 - outside, height, 16.0 - outside);
            case UP -> Block.box(outside, 16.0 - height, outside, 16.0 - outside, 16.0, 16.0 - outside);
            case NORTH -> Block.box(outside, outside, 0.0, 16.0 - outside, 16.0 - outside, height);
            case WEST -> Block.box(0.0, outside, outside, height, 16.0 - outside, 16.0 - outside);
            case SOUTH -> Block.box(outside, outside, 16.0 - height, 16.0 - outside, 16.0 - outside, 16.0);
            case EAST -> Block.box(16.0 - height, outside, outside, 16.0, 16.0 - outside, 16.0 - outside);
            default -> Block.box(0.0, 0.0, 0.0, 16.0, 16.0, 16.0);
        };
    }

    protected VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collContext) {
        Direction face = blockState.getValue(FACE);
        return createCenteredPanelBox(face, 0.0, 2.0);
    }

    public BlockState getStateForPlacement(BlockPlaceContext placementContext) {
        Direction face = placementContext.getClickedFace().getOpposite();
        return this.defaultBlockState().setValue(FACE, face);
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateBuilder) {
        stateBuilder.add(FACE);
    }

    static {
        FACE = DirectionProperty.create("face", new Direction[]{Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST, Direction.UP, Direction.DOWN});
    }

}
