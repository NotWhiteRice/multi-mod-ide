package io.github.notwhiterice.endlessskies.blocks;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class PanelBlockSample extends PanelBlock {
    public static final MapCodec<PanelBlockSample> CODEC = simpleCodec(PanelBlockSample::new);

    public PanelBlockSample(Properties prop) {
        this();
    }

    public PanelBlockSample() {
        super(BlockBehaviour.Properties.ofFullCopy(Blocks.REPEATER));
        this.registerDefaultState(((BlockState) this.stateDefinition.any()).setValue(FACE, Direction.DOWN));
    }

    public MapCodec<PanelBlockSample> codec() { return CODEC; }
}
