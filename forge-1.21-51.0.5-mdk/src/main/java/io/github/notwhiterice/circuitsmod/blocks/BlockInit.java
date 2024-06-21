package io.github.notwhiterice.circuitsmod.blocks;

import io.github.notwhiterice.circuitsmod.Circuits;
import io.github.notwhiterice.nwrcorelib.registry.ModRegistry;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

public class BlockInit {

    public static RegistryObject<Block> blockDirectionalPanel;

    public static void initBlocks() {
        blockDirectionalPanel = ModRegistry.registerBlock(Circuits.modID, "directional_panel", BlockDirectionalPanel::new);
    }
}
