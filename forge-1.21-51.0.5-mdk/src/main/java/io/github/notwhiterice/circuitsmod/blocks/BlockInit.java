package io.github.notwhiterice.circuitsmod.blocks;

import io.github.notwhiterice.circuitsmod.Circuits;
import io.github.notwhiterice.nwrcorelib.registry.ModRegistry;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class BlockInit {
    //Dev blocks
    public static RegistryObject<Block> blockTest;

    public static void initBlocks() {
        blockTest = ModRegistry.registerBlock(Circuits.modID, "test_block", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.GRASS_BLOCK)));
    }
}
