package io.github.notwhiterice.circuitsmod.creativetabs;

import io.github.notwhiterice.circuitsmod.Circuits;
import io.github.notwhiterice.circuitsmod.blocks.BlockInit;
import io.github.notwhiterice.circuitsmod.items.ItemInit;
import io.github.notwhiterice.nwrcorelib.registry.ModRegistry;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.RegistryObject;

public class TabInit {
    public static RegistryObject<CreativeModeTab> tabCircuitsMod;

    public static void initTabs() {
        tabCircuitsMod = ModRegistry.registerTab(Circuits.modID, Circuits.modID, () -> CreativeModeTab.builder()
                .icon(() -> new ItemStack(ItemInit.itemBasicCircuitBoard.get()))
                .title(Component.translatable("creativetab.circuitsmod"))
                .displayItems((param, out) ->{
                    out.accept(ItemInit.itemBasicCircuitBoard.get());
                    out.accept(ItemInit.itemTest.get());
                    out.accept(BlockInit.blockTest.get());
                }).build());
    }
}
