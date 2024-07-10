package io.github.notwhiterice.endlessskies.block;

import com.mojang.serialization.MapCodec;
import io.github.notwhiterice.endlessskies.Reference;
import io.github.notwhiterice.endlessskies.block.entity.PanelBlockEntity;
import io.github.notwhiterice.endlessskies.block.factory.BasicEntityBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;


public class PanelBlock extends BasicEntityBlock {

    public PanelBlock(Properties prop) {
        super(prop);
        defineContext(Reference.modID, "panel");
    }

    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        BlockEntity entity = level.getBlockEntity(pos);
        if(entity instanceof PanelBlockEntity) {
            PanelBlockEntity tile = (PanelBlockEntity) entity;
            double pixelHeight = 2.0;
            return switch(tile.face) {
                case UP -> Block.box(0.0, 16.0-pixelHeight, 0.0, 16.0, 16.0,16.0);
                case DOWN -> Block.box(0.0, 0.0, 0.0, 16.0, pixelHeight, 16.0);
                case NORTH -> Block.box(0.0, 0.0, 0.0, 16.0, 16.0, pixelHeight);
                case WEST -> Block.box(0.0, 0.0, 0.0, pixelHeight, 16.0, 16.0);
                case SOUTH -> Block.box(0.0, 0.0, 16.0-pixelHeight, 16.0, 16.0, 16.0);
                case EAST -> Block.box(16.0-pixelHeight, 0.0, 0.0, 16.0, 16.0, 16.0);
            };
        }
        return Shapes.block();
    }

    @Override
    protected RenderShape getRenderShape(BlockState pState) { return RenderShape.INVISIBLE; }

    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return getShape(state, level, pos, context);
    }

    @Override
    protected MapCodec<? extends PanelBlock> codec() { return simpleCodec(PanelBlock::new); }
}
