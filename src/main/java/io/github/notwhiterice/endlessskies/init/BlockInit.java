package io.github.notwhiterice.endlessskies.init;

import io.github.notwhiterice.endlessskies.block.MineralInfuserBlock;
import io.github.notwhiterice.endlessskies.block.RockCrusherBlock;
import io.github.notwhiterice.endlessskies.block.entity.MineralInfuserBlockEntity;
import io.github.notwhiterice.endlessskies.block.entity.RockCrusherBlockEntity;
import io.github.notwhiterice.endlessskies.block.factory.BlockContext;
import io.github.notwhiterice.endlessskies.block.factory.BlockFactory;
import io.github.notwhiterice.endlessskies.datagen.tag.BlockStateProviderTag;
import io.github.notwhiterice.endlessskies.inventory.MineralInfuserMenu;
import io.github.notwhiterice.endlessskies.inventory.RockCrusherMenu;
import io.github.notwhiterice.endlessskies.inventory.screen.MineralInfuserScreen;
import io.github.notwhiterice.endlessskies.inventory.screen.RockCrusherScreen;
import io.github.notwhiterice.endlessskies.registry.object.ModContext;
import net.minecraft.world.item.CreativeModeTabs;

public class BlockInit {
    public static BlockContext blockMineralInfuser;
    public static BlockContext blockRockCrusher;

    public static void registerBlocks(ModContext context) {
        blockMineralInfuser = context.registerBlock("mineral_infuser", new BlockFactory(MineralInfuserBlock.class))
                .addTileEntity(MineralInfuserBlockEntity::new)
                .addMenu(MineralInfuserMenu::new, MineralInfuserScreen::new)
                .setCreativeTab(CreativeModeTabs.FUNCTIONAL_BLOCKS);
        blockRockCrusher = context.registerBlock("rock_crusher", new BlockFactory(RockCrusherBlock.class))
                .addTileEntity(RockCrusherBlockEntity::new)
                .addMenu(RockCrusherMenu::new, RockCrusherScreen::new)
                .setCreativeTab(CreativeModeTabs.FUNCTIONAL_BLOCKS);
    }
}
