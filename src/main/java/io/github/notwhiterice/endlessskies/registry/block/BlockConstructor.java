package io.github.notwhiterice.endlessskies.registry.block;

import net.minecraft.world.level.block.Block;

public interface BlockConstructor<T> {
    T create(Block.Properties prop);
}
