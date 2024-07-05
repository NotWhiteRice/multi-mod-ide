package io.github.notwhiterice.endlessskies;

import io.github.notwhiterice.endlessskies.block.entity.factory.TileEntityContext;
import io.github.notwhiterice.endlessskies.init.BlockInit;
import io.github.notwhiterice.endlessskies.init.CreativeModeTabInit;
import io.github.notwhiterice.endlessskies.init.DevInit;
import io.github.notwhiterice.endlessskies.inventory.factory.MenuContext;
import io.github.notwhiterice.endlessskies.registry.object.ModContext;
import net.minecraft.world.item.Items;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Reference.modID)
public class EndlessSkies {
    public EndlessSkies() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        //ModContext err_1 = new ModContext(Reference.modID);

        //context.registerItem("dupe_test", new ItemFactory());
        //context.registerItem("dupe_test", new ItemFactory());
        ModContext context = new ModContext(Reference.modID);

        BlockInit.registerBlocks(context);

        context.generateModSpecificTab().setIcon(Items.DIAMOND);

        DevInit.registerDevObjs(context);

        CreativeModeTabInit.registerTabs(context);

        for(TileEntityContext tContext : TileEntityContext.getAllTileEnts())
            tContext.getRegistryObject();
        for(MenuContext mContext : MenuContext.getAllMenus())
            mContext.getRegistryObject();

        context.finalizeRegisters(bus);
    }
}
