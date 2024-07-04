package io.github.deprecated.v1.endlessskies.blocks;

import com.mojang.serialization.MapCodec;
import io.github.deprecated.v1.endlessskies.core.block.DiPanelBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class DiPanelSample extends DiPanelBlock {
    public DiPanelSample() { super(BlockBehaviour.Properties.ofFullCopy(Blocks.REPEATER)); }
    public DiPanelSample(Properties prop) { this(); }

    @Override
    public MapCodec<DiPanelSample> codec() { return simpleCodec(DiPanelSample::new); }
}
