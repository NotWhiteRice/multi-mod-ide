package io.github.deprecated.v1.circuitsmod.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.Blocks;

public class AndGateBlock extends Basic2to1CircuitBlock {
    public static final MapCodec<AndGateBlock> CODEC = simpleCodec(AndGateBlock::new);

    public AndGateBlock(Properties prop) {
        this();
    }

    public AndGateBlock() {
        super(Properties.ofFullCopy(Blocks.REPEATER));
    }

    @Override
    public boolean parseCircuit(boolean left, boolean right) {
        return left && right;
    }

    public MapCodec<AndGateBlock> codec() { return CODEC; }
}
