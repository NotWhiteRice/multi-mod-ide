package io.github.notwhiterice.endlessskies.init;

import io.github.notwhiterice.endlessskies.block.TestBlock;
import io.github.notwhiterice.endlessskies.registry.block.BlockContext;
import io.github.notwhiterice.endlessskies.registry.block.BlockFactory;
import io.github.notwhiterice.endlessskies.registry.block.data.BlockStateFactory;
import io.github.notwhiterice.endlessskies.registry.block.data.LootTableFactory;
import io.github.notwhiterice.endlessskies.item.TestItem;
import io.github.notwhiterice.endlessskies.registry.item.ItemContext;
import io.github.notwhiterice.endlessskies.registry.item.ItemFactory;
import io.github.notwhiterice.endlessskies.registry.item.data.ItemModelFactory;
import io.github.notwhiterice.endlessskies.registry.ModContext;
import net.minecraft.world.item.CreativeModeTabs;

public class DevInit {
    public static BlockContext blockTest;
    public static ItemContext itemTest;

    public static void registerDevObjs(ModContext context) {
        BlockFactory bFactory = BlockFactory.init(context);
        ItemFactory iFactory = ItemFactory.init(context);
        blockTest = bFactory.block("test_block")
                .setParent(TestBlock::new)
                .setStateFactory(BlockStateFactory.SIMPLE_BLOCK)
                .setDrops(LootTableFactory.SILK_TOUCH)
                .setCreativeTab(CreativeModeTabs.OP_BLOCKS)
                .setName("Test Block [DEV]").close();
        itemTest = iFactory.item("test_item")
                .setParent(TestItem.class)
                .setModelFactory(ItemModelFactory.SIMPLE_ITEM)
                .setCreativeTab(CreativeModeTabs.OP_BLOCKS)
                .setName("Test Item [DEV]").close();
    }
}
