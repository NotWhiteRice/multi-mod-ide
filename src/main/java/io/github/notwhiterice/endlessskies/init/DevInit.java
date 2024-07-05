package io.github.notwhiterice.endlessskies.init;

import io.github.notwhiterice.endlessskies.block.TestBlock;
import io.github.notwhiterice.endlessskies.block.factory.BlockContext;
import io.github.notwhiterice.endlessskies.block.factory.BlockFactory;
import io.github.notwhiterice.endlessskies.datagen.tag.BlockStateProviderTag;
import io.github.notwhiterice.endlessskies.datagen.tag.ItemModelProviderTag;
import io.github.notwhiterice.endlessskies.datagen.tag.LootTableProviderTag;
import io.github.notwhiterice.endlessskies.item.TestItem;
import io.github.notwhiterice.endlessskies.item.factory.ItemContext;
import io.github.notwhiterice.endlessskies.item.factory.ItemFactory;
import io.github.notwhiterice.endlessskies.registry.object.ModContext;
import net.minecraft.world.item.CreativeModeTabs;

public class DevInit {
    public static BlockContext blockTest;
    public static ItemContext itemTest;

    public static void registerDevObjs(ModContext context) {
        blockTest = context.registerBlock("test_block", new BlockFactory(TestBlock.class))
                .setStateDataTag(BlockStateProviderTag.SIMPLE_BLOCK)
                .setCreativeTab(CreativeModeTabs.BUILDING_BLOCKS)
                .setLootDataTag(LootTableProviderTag.DROP_SELF);
        itemTest = context.registerItem("test_item", new ItemFactory(TestItem.class))
                .setModelDataTag(ItemModelProviderTag.SIMPLE_ITEM)
                .setCreativeTab(CreativeModeTabs.TOOLS_AND_UTILITIES);
    }
}
