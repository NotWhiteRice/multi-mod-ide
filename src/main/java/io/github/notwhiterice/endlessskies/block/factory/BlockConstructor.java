package io.github.notwhiterice.endlessskies.block.factory;

import net.minecraft.world.level.block.Block;

public interface BlockConstructor<T> {
    T create(Block.Properties prop);
}
