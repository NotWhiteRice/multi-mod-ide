package io.github.notwhiterice.endlessskies.init;

import io.github.notwhiterice.endlessskies.block.MineralInfuserBlock;
import io.github.notwhiterice.endlessskies.block.RockCrusherBlock;
import io.github.notwhiterice.endlessskies.block.entity.MineralInfuserBlockEntity;
import io.github.notwhiterice.endlessskies.block.entity.RockCrusherBlockEntity;
import io.github.notwhiterice.endlessskies.block.factory.BlockContext;
import io.github.notwhiterice.endlessskies.block.factory.BlockFactory;
import io.github.notwhiterice.endlessskies.inventory.MineralInfuserMenu;
import io.github.notwhiterice.endlessskies.inventory.RockCrusherMenu;
import io.github.notwhiterice.endlessskies.inventory.screen.MineralInfuserScreen;
import io.github.notwhiterice.endlessskies.inventory.screen.RockCrusherScreen;
import io.github.notwhiterice.endlessskies.registry.object.ModContext;

public class BlockInit {
    public static BlockContext blockMineralInfuser;
    public static BlockContext blockRockCrusher;

    public static void registerBlocks(ModContext context) {
        BlockFactory factory = BlockFactory.init(context);
        blockMineralInfuser = factory.block("mineral_infuser")
                .setParent(MineralInfuserBlock.class)
                .setContainer(MineralInfuserBlockEntity::new)
                .setMenu(MineralInfuserMenu::new, MineralInfuserScreen::new)
                .setName("Mineral Infuser").close();
        blockRockCrusher = factory.block("rock_crusher")
                .setParent(RockCrusherBlock.class)
                .setContainer(RockCrusherBlockEntity::new)
                .setMenu(RockCrusherMenu::new, RockCrusherScreen::new)
                .setName("Rock Crusher").close();
    }
}
