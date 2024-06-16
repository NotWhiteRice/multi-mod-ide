package io.github.notwhiterice.nwrcorelib.blocks;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class BlockBase extends Block {

    public BlockBase() { super(BlockBehaviour.Properties.ofFullCopy(Blocks.DIRT)); }
    public BlockBase(Properties prop) { super(prop); }
}
