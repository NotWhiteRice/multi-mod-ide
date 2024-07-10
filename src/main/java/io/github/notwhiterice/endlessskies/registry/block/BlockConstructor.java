package io.github.notwhiterice.endlessskies.registry.block;

import net.minecraft.world.level.block.Block;

public interface BlockConstructor<T extends Block> {
    T create(Block.Properties prop);
}
