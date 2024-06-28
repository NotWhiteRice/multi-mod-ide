package io.github.notwhiterice.circuitsmod.blocks;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.Blocks;

public class NandGateBlock extends Basic2to1CircuitBlock {
    public static final MapCodec<NandGateBlock> CODEC = simpleCodec(NandGateBlock::new);

    public NandGateBlock(Properties prop) {
        this();
    }

    public NandGateBlock() {
        super(Properties.ofFullCopy(Blocks.REPEATER));
    }

    @Override
    public boolean parseCircuit(boolean left, boolean right) {
        return !(left && right);
    }

    public MapCodec<NandGateBlock> codec() { return CODEC; }
}
