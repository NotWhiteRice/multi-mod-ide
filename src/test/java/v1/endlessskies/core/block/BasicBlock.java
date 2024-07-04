package io.github.deprecated.v1.endlessskies.core.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class BasicBlock extends Block {

    public BasicBlock() { super(BlockBehaviour.Properties.ofFullCopy(Blocks.DIRT)); }
    public BasicBlock(Properties prop) { super(prop); }
}
