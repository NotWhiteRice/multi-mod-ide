package io.github.deprecated.v1.endlessskies.blocks;

import com.mojang.serialization.MapCodec;
import io.github.deprecated.v1.endlessskies.core.block.PanelBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class PanelSample extends PanelBlock {
    public PanelSample() { super(BlockBehaviour.Properties.ofFullCopy(Blocks.REPEATER)); }
    public PanelSample(Properties prop) {
        this();
    }

    @Override
    public MapCodec<PanelSample> codec() { return simpleCodec(PanelSample::new); }
}
