package io.github.notwhiterice.endlessskies.block;

import io.github.notwhiterice.endlessskies.block.factory.BasicBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class TestBlock extends BasicBlock {
    public TestBlock() { super(BlockBehaviour.Properties.ofFullCopy(Blocks.MAGENTA_WOOL)); }
    public TestBlock(Block.Properties prop) { this(); }
}
