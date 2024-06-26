package io.github.notwhiterice.endlessskies.blocks;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class AimedPanelBlockSample extends AimedPanelBlock {
    public static final MapCodec<AimedPanelBlockSample> CODEC = simpleCodec(AimedPanelBlockSample::new);

    public AimedPanelBlockSample(Properties prop) {
        this();
    }

    public AimedPanelBlockSample() {
        super(BlockBehaviour.Properties.ofFullCopy(Blocks.REPEATER));
        this.registerDefaultState(((BlockState) this.stateDefinition.any()).setValue(FACE, Direction.DOWN).setValue(FACING, Direction.NORTH));
    }

    public MapCodec<AimedPanelBlockSample> codec() { return CODEC; }
}
