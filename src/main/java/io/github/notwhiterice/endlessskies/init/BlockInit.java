package io.github.notwhiterice.endlessskies.init;

import io.github.notwhiterice.endlessskies.block.MineralInfuserBlock;
import io.github.notwhiterice.endlessskies.block.RockCrusherBlock;
import io.github.notwhiterice.endlessskies.block.entity.MineralInfuserBlockEntity;
import io.github.notwhiterice.endlessskies.block.entity.RockCrusherBlockEntity;
import io.github.notwhiterice.endlessskies.block.factory.BlockContext;
import io.github.notwhiterice.endlessskies.block.factory.BlockFactory;
import io.github.notwhiterice.endlessskies.block.factory.data.BlockStateFactory;
import io.github.notwhiterice.endlessskies.inventory.MineralInfuserMenu;
import io.github.notwhiterice.endlessskies.inventory.RockCrusherMenu;
import io.github.notwhiterice.endlessskies.inventory.screen.MineralInfuserScreen;
import io.github.notwhiterice.endlessskies.inventory.screen.RockCrusherScreen;
import io.github.notwhiterice.endlessskies.registry.object.ModContext;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;

public class BlockInit {
    //Natural blocks
    public static BlockContext blockLimestone;

    //Building blocks and materials
    public static BlockContext blockPolishedLimestone;

    //T1 machines
    public static BlockContext blockCrudeFurnace;

    //Creative machines
    public static BlockContext blockCreativeHeater;

    //Temporary blocks
    public static BlockContext blockMineralInfuser;
    public static BlockContext blockRockCrusher;

    public static void registerBlocks(ModContext context) {
        BlockFactory factory = BlockFactory.init(context);
        blockLimestone = factory.block("limestone").setStrength(1.5F, 6.0F).setMapColor(MapColor.SAND).setStateFactory(BlockStateFactory.SIMPLE_BLOCK).setCreativeTab(CreativeModeTabs.BUILDING_BLOCKS).setName("Limestone").close();
        blockPolishedLimestone = factory.block("polished_limestone").setStrength(1.5F, 6.0F).setMapColor(MapColor.SAND).setStateFactory(BlockStateFactory.SIMPLE_BLOCK).setCreativeTab(CreativeModeTabs.BUILDING_BLOCKS).setName("Polished Limestone").close();








        blockMineralInfuser = factory.block("mineral_infuser")
                .setParent(MineralInfuserBlock.class)
                .setContainer(MineralInfuserBlockEntity::new)
                .setMenu(MineralInfuserMenu::new, MineralInfuserScreen::new)
                .setCreativeTab(CreativeModeTabs.FUNCTIONAL_BLOCKS)
                .setName("Mineral Infuser").close();
        blockRockCrusher = factory.block("rock_crusher")
                .setParent(RockCrusherBlock.class)
                .setContainer(RockCrusherBlockEntity::new)
                .setMenu(RockCrusherMenu::new, RockCrusherScreen::new)
                .setCreativeTab(CreativeModeTabs.FUNCTIONAL_BLOCKS)
                .setName("Rock Crusher").close();
    }
}
