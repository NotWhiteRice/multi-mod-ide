package io.github.notwhiterice.deprecated.circuitsmod040alpha.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.Blocks;

public class XorGateBlock extends Basic2to1CircuitBlock {
    public static final MapCodec<XorGateBlock> CODEC = simpleCodec(XorGateBlock::new);

    public XorGateBlock(Properties prop) {
        this();
    }

    public XorGateBlock() {
        super(Properties.ofFullCopy(Blocks.REPEATER));
    }

    @Override
    public boolean parseCircuit(boolean left, boolean right) {
        return left != right;
    }

    public MapCodec<XorGateBlock> codec() { return CODEC; }
}
