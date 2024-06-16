package io.github.notwhiterice.nwrcorelib.blocks;

import io.github.notwhiterice.nwrcorelib.NWRCore;
import io.github.notwhiterice.nwrcorelib.registry.ModRegistry;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.RegistryObject;

public class BlockInit {
    public static RegistryObject<Block> blockTest;

    public static void initBlocks() {
        blockTest = ModRegistry.registerBlock(NWRCore.modID, "test_block", BlockTest::new);
    }
}
