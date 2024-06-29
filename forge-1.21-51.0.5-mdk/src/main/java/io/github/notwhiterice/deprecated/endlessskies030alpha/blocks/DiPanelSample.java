package io.github.notwhiterice.deprecated.endlessskies030alpha.blocks;

import com.mojang.serialization.MapCodec;
import io.github.notwhiterice.deprecated.endlessskies030alpha.core.block.DiPanelBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class DiPanelSample extends DiPanelBlock {
    public DiPanelSample() { super(BlockBehaviour.Properties.ofFullCopy(Blocks.REPEATER)); }
    public DiPanelSample(Properties prop) { this(); }

    @Override
    public MapCodec<DiPanelSample> codec() { return simpleCodec(DiPanelSample::new); }
}
