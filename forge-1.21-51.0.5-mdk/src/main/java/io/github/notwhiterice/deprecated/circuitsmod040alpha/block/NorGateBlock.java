package io.github.notwhiterice.deprecated.circuitsmod040alpha.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.Blocks;

public class NorGateBlock extends Basic2to1CircuitBlock {
    public static final MapCodec<NorGateBlock> CODEC = simpleCodec(NorGateBlock::new);

    public NorGateBlock(Properties prop) {
        this();
    }

    public NorGateBlock() {
        super(Properties.ofFullCopy(Blocks.REPEATER));
    }

    @Override
    public boolean parseCircuit(boolean left, boolean right) {
        return !(left || right);
    }

    public MapCodec<NorGateBlock> codec() { return CODEC; }
}
