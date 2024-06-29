package io.github.notwhiterice.deprecated.endlessskies030alpha.core.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class BasicBlock extends Block {

    public BasicBlock() { super(BlockBehaviour.Properties.ofFullCopy(Blocks.DIRT)); }
    public BasicBlock(Properties prop) { super(prop); }
}
