package io.github.notwhiterice.circuitsmod.blocks;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;

public abstract class DirectionalFaceBlock extends FaceBlock{
    public static final DirectionProperty FACING;

    public DirectionalFaceBlock(Properties prop) { super(prop); }

    protected abstract MapCodec<? extends DirectionalFaceBlock> codec();

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext placementContext) {

        Direction face = placementContext.getClickedFace().getOpposite();
        double mouseX = placementContext.getClickLocation().x - placementContext.getClickedPos().getX();
        double mouseY = placementContext.getClickLocation().y - placementContext.getClickedPos().getY();
        double mouseZ = placementContext.getClickLocation().z - placementContext.getClickedPos().getZ();
        double faceX=mouseX, faceY=mouseY;
        switch(face) {
            case UP:
            case DOWN: {
                faceY=1-mouseZ;
            } break;
            case SOUTH: {
                faceX=1-mouseX;
            } break;
            case WEST: {
                faceX=1-mouseZ;
            } break;
            case EAST: {
                faceX=mouseZ;
            }
        }

        Direction facing = Direction.NORTH;

        if(faceX > faceY && faceX>=1-faceY) facing=Direction.EAST;
        if(faceX >= faceY && faceX<1-faceY) facing=Direction.SOUTH;
        if(faceX < faceY && faceX<=1-faceY) facing=Direction.WEST;

        return this.defaultBlockState().setValue(FACE, face).setValue(FACING, facing);
    }


    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateBuilder) {
        stateBuilder.add(FACE, FACING);
    }

    static {
        FACING = DirectionProperty.create("facing", new Direction[]{Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST});
    }
}
