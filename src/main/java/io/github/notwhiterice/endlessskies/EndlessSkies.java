package io.github.notwhiterice.endlessskies;

import io.github.notwhiterice.endlessskies.registry.block.entity.TileEntityContext;
import io.github.notwhiterice.endlessskies.registry.block.BlockContext;
import io.github.notwhiterice.endlessskies.creativetab.factory.CreativeModeTabContext;
import io.github.notwhiterice.endlessskies.init.BlockInit;
import io.github.notwhiterice.endlessskies.init.CreativeModeTabInit;
import io.github.notwhiterice.endlessskies.init.DevInit;
import io.github.notwhiterice.endlessskies.init.ItemInit;
import io.github.notwhiterice.endlessskies.registry.inventory.MenuContext;
import io.github.notwhiterice.endlessskies.registry.item.ItemContext;
import io.github.notwhiterice.endlessskies.recipe.ModRecipes;
import io.github.notwhiterice.endlessskies.registry.ModContext;
import net.minecraft.world.item.Items;
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

        for(BlockContext block : BlockContext.instances)
            block.getRegistry();
        for(ItemContext item : ItemContext.instances)
            item.getRegistry();
        for(CreativeModeTabContext tab : CreativeModeTabContext.instances)
            tab.getRegistry();
        for(TileEntityContext container : TileEntityContext.instances)
            container.getRegistry();
        for(MenuContext menu : MenuContext.instances)
            menu.getRegistry();

        ModRecipes.registerRecipes();

        context.finalizeRegisters(bus);
    }

    public static boolean canRegisterObject() { return !lockRegisters; }
}
