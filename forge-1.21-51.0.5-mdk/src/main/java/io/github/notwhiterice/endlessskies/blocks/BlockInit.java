package io.github.notwhiterice.endlessskies.blocks;

import io.github.notwhiterice.endlessskies.EndlessSkies;
import io.github.notwhiterice.endlessskies.registry.ModRegistry;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

public class BlockInit {

    //Custom block type examples
    public static RegistryObject<Block> blockPanel;
    public static RegistryObject<Block> blockDiPanel;

    //Dev blocks
    public static RegistryObject<Block> blockTest;

    public static void initBlocks() {
        blockPanel = ModRegistry.registerBlock(EndlessSkies.modID, "panel", PanelSample::new);
        blockDiPanel = ModRegistry.registerBlock(EndlessSkies.modID, "dipanel", DiPanelSample::new);

        blockTest = ModRegistry.registerBlock(EndlessSkies.modID, "test_block", TestBlock::new);
    }
}
