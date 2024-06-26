package io.github.notwhiterice.endlessskies.blocks;

import io.github.notwhiterice.endlessskies.EndlessSkies;
import io.github.notwhiterice.endlessskies.registry.ModRegistry;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

public class BlockInit {

    //Custom block type examples
    public static RegistryObject<Block> blockPanelSample;
    public static RegistryObject<Block> blockAimedPanelSample;

    //Dev blocks
    public static RegistryObject<Block> blockTest;

    public static void initBlocks() {
        blockPanelSample = ModRegistry.registerBlock(EndlessSkies.modID, "panel_sample", PanelBlockSample::new);
        blockAimedPanelSample = ModRegistry.registerBlock(EndlessSkies.modID, "aimedpanel_sample", AimedPanelBlockSample::new);

        blockTest = ModRegistry.registerBlock(EndlessSkies.modID, "test_block", TestBlock::new);
    }
}
