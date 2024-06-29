package io.github.notwhiterice.deprecated.circuitsmod040alpha.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.Blocks;

public class OrGateBlock extends Basic2to1CircuitBlock {
    public static final MapCodec<OrGateBlock> CODEC = simpleCodec(OrGateBlock::new);

    public OrGateBlock(Properties prop) {
        this();
    }

    public OrGateBlock() {
        super(Properties.ofFullCopy(Blocks.REPEATER));
    }

    @Override
    public boolean parseCircuit(boolean left, boolean right) {
        return left || right;
    }

    public MapCodec<OrGateBlock> codec() { return CODEC; }
}
