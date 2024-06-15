package io.github.notwhiterice.bountifulharvest.creativetabs;

import io.github.notwhiterice.bountifulharvest.BountifulHarvest;
import io.github.notwhiterice.bountifulharvest.blocks.BlockInit;
import io.github.notwhiterice.bountifulharvest.items.ItemInit;
import io.github.notwhiterice.nwrcorelib.registry.ModRegistry;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.registries.RegistryObject;

public class TabInit {
    public static RegistryObject<CreativeModeTab> tabBountifulHarvest;

    public static void initTabs() {
        tabBountifulHarvest = ModRegistry.registerTab(BountifulHarvest.modID, BountifulHarvest.modID, () -> CreativeModeTab.builder()
                .icon(() -> new ItemStack(ItemInit.itemCinnamon.get()))
                .title(Component.translatable("creativetab.bountifulharvest"))
                .displayItems((param, out) ->{
                    out.accept(ItemInit.itemCinnamon.get());
                    out.accept(BlockInit.blockCinnamon.get());
                }).build());
    }
}
