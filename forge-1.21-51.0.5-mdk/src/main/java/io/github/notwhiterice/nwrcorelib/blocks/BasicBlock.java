package io.github.notwhiterice.nwrcorelib.blocks;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class BasicBlock extends Block {

    public BasicBlock() { this(BlockBehaviour.Properties.ofFullCopy(Blocks.DIRT)); }
    public BasicBlock(Properties prop) { super(prop); }
}
