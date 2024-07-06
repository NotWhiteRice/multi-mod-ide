package io.github.notwhiterice.endlessskies.init;

import io.github.notwhiterice.endlessskies.block.TestBlock;
import io.github.notwhiterice.endlessskies.block.factory.BlockContext;
import io.github.notwhiterice.endlessskies.block.factory.BlockFactory;
import io.github.notwhiterice.endlessskies.block.factory.data.BlockStateFactory;
import io.github.notwhiterice.endlessskies.block.factory.data.LootTableFactory;
import io.github.notwhiterice.endlessskies.item.TestItem;
import io.github.notwhiterice.endlessskies.item.factory.ItemContext;
import io.github.notwhiterice.endlessskies.item.factory.ItemFactory;
import io.github.notwhiterice.endlessskies.item.factory.data.ItemModelFactory;
import io.github.notwhiterice.endlessskies.registry.object.ModContext;

public class DevInit {
    public static BlockContext blockTest;
    public static ItemContext itemTest;

    public static void registerDevObjs(ModContext context) {
        BlockFactory bFactory = BlockFactory.init(context);
        ItemFactory iFactory = ItemFactory.init(context);
        blockTest = bFactory.block("test_block")
                .setParent(TestBlock.class)
                .setStateFactory(BlockStateFactory.SIMPLE_BLOCK)
                .setDrops(LootTableFactory.SILK_TOUCH)
                .setName("Test Block [DEV]").close();
        itemTest = iFactory.item("test_item")
                .setParent(TestItem.class)
                .setModelFactory(ItemModelFactory.SIMPLE_ITEM)
                .setName("Test Item [DEV]").close();
    }
}
