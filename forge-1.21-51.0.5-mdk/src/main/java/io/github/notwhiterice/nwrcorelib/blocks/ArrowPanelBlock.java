package io.github.notwhiterice.nwrcorelib.blocks;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class ArrowPanelBlock extends AimedPanelBlock {
    public static final MapCodec<ArrowPanelBlock> CODEC = simpleCodec(ArrowPanelBlock::new);

    public ArrowPanelBlock(Properties prop) {
        this();
    }

    public ArrowPanelBlock() {
        super(BlockBehaviour.Properties.ofFullCopy(Blocks.REPEATER));
        this.registerDefaultState(((BlockState) this.stateDefinition.any()).setValue(FACE, Direction.DOWN).setValue(FACING, Direction.NORTH));
    }

    public InteractionResult useWithoutItem(BlockState blockState, Level level, BlockPos blockPos, Player player, BlockHitResult hitResult) {
        Direction currState = blockState.getValue(FACING);
        Direction currFace = blockState.getValue(FACE);
        if(player.isShiftKeyDown() && player.getMainHandItem().isEmpty()) {
            if (currState == Direction.NORTH)
                level.setBlock(blockPos, blockState.setValue(FACING, Direction.EAST), 2);
            if (currState == Direction.EAST)
                level.setBlock(blockPos, blockState.setValue(FACING, Direction.SOUTH), 2);
            if (currState == Direction.SOUTH)
                level.setBlock(blockPos, blockState.setValue(FACING, Direction.WEST), 2);
            if (currState == Direction.WEST)
                level.setBlock(blockPos, blockState.setValue(FACING, Direction.NORTH), 2);
                return InteractionResult.SUCCESS;
        } else if(!player.isShiftKeyDown()) {
            if (currFace == Direction.NORTH)
                level.setBlock(blockPos, blockState.setValue(FACE, Direction.UP), 2);
            if (currFace == Direction.UP)
                level.setBlock(blockPos, blockState.setValue(FACE, Direction.EAST), 2);
            if (currFace == Direction.EAST)
                level.setBlock(blockPos, blockState.setValue(FACE, Direction.SOUTH), 2);
            if (currFace == Direction.SOUTH)
                level.setBlock(blockPos, blockState.setValue(FACE, Direction.WEST), 2);
            if (currFace == Direction.WEST)
                level.setBlock(blockPos, blockState.setValue(FACE, Direction.DOWN), 2);
            if (currFace == Direction.DOWN)
                level.setBlock(blockPos, blockState.setValue(FACE, Direction.NORTH), 2);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.CONSUME;
    }

    public MapCodec<ArrowPanelBlock> codec() { return CODEC; }
}
