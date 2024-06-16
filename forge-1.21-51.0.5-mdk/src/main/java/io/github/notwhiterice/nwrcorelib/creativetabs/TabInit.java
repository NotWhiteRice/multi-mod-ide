package io.github.notwhiterice.nwrcorelib.creativetabs;

import io.github.notwhiterice.nwrcorelib.NWRCore;
import io.github.notwhiterice.nwrcorelib.blocks.BlockInit;
import io.github.notwhiterice.nwrcorelib.items.ItemInit;
import io.github.notwhiterice.nwrcorelib.registry.ModRegistry;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.RegistryObject;

public class TabInit {
    public static RegistryObject<CreativeModeTab> tabNWRCore;

    public static void initTabs() {
        tabNWRCore = ModRegistry._registerTab(NWRCore.rBundle, NWRCore.modID, () -> CreativeModeTab.builder()
                .icon(() -> new ItemStack(ItemInit.itemTest.get()))
                .title(Component.translatable("creativetab.nwrcorelib"))
                .displayItems((param, out) ->{
                    out.accept(ItemInit.itemTest.get());
                    out.accept(BlockInit.blockTest.get());
                }).build());
    }
}
