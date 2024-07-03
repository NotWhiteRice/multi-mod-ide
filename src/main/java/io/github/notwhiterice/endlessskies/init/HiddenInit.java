package io.github.notwhiterice.endlessskies.init;

import io.github.notwhiterice.endlessskies.EndlessSkies;
import io.github.notwhiterice.endlessskies.core.exception.DualRegistryException;
import io.github.notwhiterice.endlessskies.datagen.tag.BlockStateProviderTag;
import io.github.notwhiterice.endlessskies.datagen.tag.ItemModelProviderTag;
import io.github.notwhiterice.endlessskies.item.TestItem;
import io.github.notwhiterice.endlessskies.registries.object.BlockContext;
import io.github.notwhiterice.endlessskies.registries.object.ItemContext;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class HiddenInit {

    public static BlockContext blockTest;
    public static ItemContext itemTest;

    public static void registerDevObjects() throws DualRegistryException {
        blockTest = EndlessSkies.context.registerBlock("test_block", BlockBehaviour.Properties.ofFullCopy(Blocks.DIRT))
                .setStateProviderTag(BlockStateProviderTag.SIMPLE_BLOCKS)
                .setCreativeTab(CreativeModeTabs.BUILDING_BLOCKS);
        itemTest = EndlessSkies.context.registerItem("test_item", TestItem.class)
                .setModelProviderTag(ItemModelProviderTag.SIMPLE_ITEM)
                .setCreativeTab(CreativeModeTabs.TOOLS_AND_UTILITIES);
    }
}
