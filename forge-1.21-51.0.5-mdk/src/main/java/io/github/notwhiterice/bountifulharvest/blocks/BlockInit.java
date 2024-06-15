package io.github.notwhiterice.bountifulharvest.blocks;

import io.github.notwhiterice.bountifulharvest.BountifulHarvest;
import io.github.notwhiterice.nwrcorelib.registry.ModRegistry;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;

public class BlockInit {
    public static RegistryObject<Block> blockCinnamon;

    public static void initBlocks() {
        blockCinnamon = ModRegistry.registerBlock(BountifulHarvest.modID, "cinnamon_block", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.GRAY_WOOL)));
    }
}
