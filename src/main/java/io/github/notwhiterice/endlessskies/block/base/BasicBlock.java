package io.github.notwhiterice.endlessskies.block.base;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class BasicBlock extends Block {
    public BasicBlock() { super(BlockBehaviour.Properties.ofFullCopy(Blocks.DIRT)); }
    public BasicBlock(Properties prop) { super(prop); }
}
