package io.github.notwhiterice.endlessskies.blocks;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class ArrowPanelBlock extends AimedPanelBlock {
    public static final MapCodec<ArrowPanelBlock> CODEC = simpleCodec(ArrowPanelBlock::new);

    public ArrowPanelBlock(Properties prop) {
        this();
    }

    public ArrowPanelBlock() {
        super(BlockBehaviour.Properties.ofFullCopy(Blocks.REPEATER));
        this.registerDefaultState(((BlockState) this.stateDefinition.any()).setValue(FACE, Direction.DOWN).setValue(FACING, Direction.NORTH));
    }

    public MapCodec<ArrowPanelBlock> codec() { return CODEC; }
}
