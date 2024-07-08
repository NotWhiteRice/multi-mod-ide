package io.github.notwhiterice.endlessskies;

import io.github.notwhiterice.endlessskies.block.entity.factory.TileEntityContext;
import io.github.notwhiterice.endlessskies.block.factory.BlockContext;
import io.github.notwhiterice.endlessskies.creativetab.factory.CreativeModeTabContext;
import io.github.notwhiterice.endlessskies.init.BlockInit;
import io.github.notwhiterice.endlessskies.init.CreativeModeTabInit;
import io.github.notwhiterice.endlessskies.init.DevInit;
import io.github.notwhiterice.endlessskies.init.ItemInit;
import io.github.notwhiterice.endlessskies.inventory.factory.MenuContext;
import io.github.notwhiterice.endlessskies.item.factory.ItemContext;
import io.github.notwhiterice.endlessskies.recipe.ModRecipes;
import io.github.notwhiterice.endlessskies.registry.object.ModContext;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.SmithingRecipe;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Reference.modID)
public class EndlessSkies {
    private static boolean lockRegisters = true;

    public EndlessSkies() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        //ModContext crash_1 = new ModContext(Reference.modID);

        ModContext context = new ModContext(Reference.modID);

        BlockInit.registerBlocks(context);
        ItemInit.registerItems(context);

        context.generateModSpecificTab()
                .setIcon(Items.DIAMOND)
                .setName("Endless Skies");

        DevInit.registerDevObjs(context);

        CreativeModeTabInit.registerTabs(context);

        lockRegisters = false;

        for(BlockContext block : BlockContext.getAllBlocks())
            block.getRegistryObject();
        for(ItemContext item : ItemContext.getAllItems())
            item.getRegistryObject();
        for(CreativeModeTabContext tab : CreativeModeTabContext.getAllTabs())
            tab.getRegistryObject();
        for(TileEntityContext container : TileEntityContext.getAllTileEnts())
            container.getRegistryObject();
        for(MenuContext menu : MenuContext.getAllMenus())
            menu.getRegistryObject();

        ModRecipes.registerRecipes();

        context.finalizeRegisters(bus);
    }

    public static boolean canRegisterObject() { return !lockRegisters; }
}
