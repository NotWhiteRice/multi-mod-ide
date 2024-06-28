package io.github.notwhiterice.circuitsmod.blocks;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.Blocks;

public class XnorGateBlock extends Basic2to1CircuitBlock {
    public static final MapCodec<XnorGateBlock> CODEC = simpleCodec(XnorGateBlock::new);

    public XnorGateBlock(Properties prop) {
        this();
    }

    public XnorGateBlock() {
        super(Properties.ofFullCopy(Blocks.REPEATER));
    }

    @Override
    public boolean parseCircuit(boolean left, boolean right) {
        return left == right;
    }

    public MapCodec<XnorGateBlock> codec() { return CODEC; }
}
