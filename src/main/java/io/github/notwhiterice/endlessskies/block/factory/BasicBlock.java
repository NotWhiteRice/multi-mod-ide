package io.github.notwhiterice.endlessskies.block.factory;

import io.github.notwhiterice.endlessskies.block.factory.BlockContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class BasicBlock extends Block {
    public BlockContext context;

    public BasicBlock() { super(BlockBehaviour.Properties.ofFullCopy(Blocks.DIRT)); }
    public BasicBlock(Properties prop) { super(prop); }

    protected void defineContext(String modID, String name) { context = BlockContext.getContext(modID, name); }
}
