package io.github.notwhiterice.endlessskies.init;

import io.github.notwhiterice.endlessskies.EndlessSkies;
import io.github.notwhiterice.endlessskies.block.MineralInfuserBlock;
import io.github.notwhiterice.endlessskies.block.RockCrusherBlock;
import io.github.notwhiterice.endlessskies.core.exception.DualRegistryException;
import io.github.notwhiterice.endlessskies.datagen.tag.BlockStateProviderTag;
import io.github.notwhiterice.endlessskies.registries.object.BlockContext;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.eventbus.api.IEventBus;

public class BlockInit {

    public static BlockContext blockMineralInfuser;
    public static BlockContext blockRockCrusher;

    public static void registerBlocks() throws DualRegistryException {
        blockMineralInfuser = EndlessSkies.context.registerBlock("mineral_infuser", MineralInfuserBlock.class)
                .setStateProviderTag(BlockStateProviderTag.BLOCKS_WITHOUT_MODEL)
                .setCreativeTab(CreativeModeTabs.FUNCTIONAL_BLOCKS);
        blockRockCrusher = EndlessSkies.context.registerBlock("rock_crusher", RockCrusherBlock.class)
                .setStateProviderTag(BlockStateProviderTag.BLOCKS_WITHOUT_MODEL)
                .setCreativeTab(CreativeModeTabs.FUNCTIONAL_BLOCKS);
    }

    public static void register(IEventBus bus) {
        EndlessSkies.context.BLOCKS.register(bus);
    }
}