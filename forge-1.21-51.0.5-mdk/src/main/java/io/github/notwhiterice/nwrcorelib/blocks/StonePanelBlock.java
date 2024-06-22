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

public class StonePanelBlock extends PanelBlock {
    public static final MapCodec<StonePanelBlock> CODEC = simpleCodec(StonePanelBlock::new);

    public StonePanelBlock(Properties prop) {
        this();
    }

    public StonePanelBlock() {
        super(BlockBehaviour.Properties.ofFullCopy(Blocks.REPEATER));
        this.registerDefaultState(((BlockState) this.stateDefinition.any()).setValue(FACE, Direction.DOWN));
    }

    public InteractionResult useWithoutItem(BlockState blockState, Level level, BlockPos blockPos, Player player, BlockHitResult hitResult) {
        Direction currFace = blockState.getValue(FACE);
        if(!player.isShiftKeyDown() || player.getMainHandItem().isEmpty()) {
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

    public MapCodec<StonePanelBlock> codec() { return CODEC; }
}
