package io.github.notwhiterice.endlessskies.blocks;

import io.github.notwhiterice.endlessskies.EndlessSkies;
import io.github.notwhiterice.endlessskies.registry.ModRegistry;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

public class BlockInit {

    //Custom block type examples
    public static RegistryObject<Block> blockStonePanel;
    public static RegistryObject<Block> blockArrowPanel;

    //Dev blocks
    public static RegistryObject<Block> blockTest;

    public static void initBlocks() {
        blockStonePanel = ModRegistry.registerBlock(EndlessSkies.modID, "stone_panel", StonePanelBlock::new);
        blockArrowPanel = ModRegistry.registerBlock(EndlessSkies.modID, "arrow_panel", ArrowPanelBlock::new);

        blockTest = ModRegistry.registerBlock(EndlessSkies.modID, "test_block", TestBlock::new);
    }
}
