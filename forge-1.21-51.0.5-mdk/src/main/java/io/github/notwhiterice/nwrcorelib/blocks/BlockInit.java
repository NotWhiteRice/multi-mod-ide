package io.github.notwhiterice.nwrcorelib.blocks;

import io.github.notwhiterice.nwrcorelib.NWRCore;
import io.github.notwhiterice.nwrcorelib.registry.ModRegistry;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

public class BlockInit {

    //Custom block type examples
    public static RegistryObject<Block> blockStonePanel;
    public static RegistryObject<Block> blockArrowPanel;

    //Dev blocks
    public static RegistryObject<Block> blockTest;

    public static void initBlocks() {
        blockStonePanel = ModRegistry.registerBlock(NWRCore.modID, "stone_panel", StonePanelBlock::new);
        blockArrowPanel = ModRegistry.registerBlock(NWRCore.modID, "arrow_panel", ArrowPanelBlock::new);

        blockTest = ModRegistry.registerBlock(NWRCore.modID, "test_block", TestBlock::new);
    }
}
