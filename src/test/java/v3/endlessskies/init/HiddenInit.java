package io.github.deprecated.v3.endlessskies.init;

import io.github.deprecated.v3.endlessskies.block.TestBlock;
import io.github.notwhiterice.endlessskies.core.exception.DualRegistryException;
import io.github.deprecated.v3.endlessskies.datagen.tag.BlockStateProviderTag;
import io.github.deprecated.v3.endlessskies.datagen.tag.ItemModelProviderTag;
import io.github.deprecated.v3.endlessskies.item.TestItem;
import io.github.deprecated.v3.endlessskies.registry.object.BlockContext;
import io.github.deprecated.v3.endlessskies.registry.object.ItemContext;
import io.github.deprecated.v3.endlessskies.registry.object.ModContext;
import net.minecraft.world.item.CreativeModeTabs;

public class HiddenInit {
    public static BlockContext blockTest;
    public static ItemContext itemTest;

    public static void registerHidden(ModContext context) throws DualRegistryException, IllegalAccessException {
        blockTest = context.registerBlock("test_block", TestBlock.class)
                .setStateProviderTag(BlockStateProviderTag.SIMPLE_BLOCKS)
                .setCreativeTab(CreativeModeTabs.BUILDING_BLOCKS);
        itemTest = context.registerItem("test_item", TestItem.class)
                .setModelProviderTag(ItemModelProviderTag.SIMPLE_ITEM)
                .setCreativeTab(CreativeModeTabs.TOOLS_AND_UTILITIES);
    }
}
