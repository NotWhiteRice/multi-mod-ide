package io.github.notwhiterice.deprecated.endlessskies030alpha.blocks;

import com.mojang.serialization.MapCodec;
import io.github.notwhiterice.deprecated.endlessskies030alpha.core.block.PanelBlock;
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
