package io.github.notwhiterice.circuitsmod.blocks;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class BlockDirectionalPanel extends DirectionalFaceBlock {
    public static final MapCodec<BlockDirectionalPanel> CODEC = simpleCodec(BlockDirectionalPanel::new);

    public BlockDirectionalPanel(Properties prop) {
        this();
    }

    public BlockDirectionalPanel() {
        super(BlockBehaviour.Properties.ofFullCopy(Blocks.REPEATER));
        this.registerDefaultState(((BlockState) this.stateDefinition.any()).setValue(FACE, Direction.DOWN).setValue(FACING, Direction.NORTH));
    }

    protected MapCodec<BlockDirectionalPanel> codec() { return CODEC; }
}
