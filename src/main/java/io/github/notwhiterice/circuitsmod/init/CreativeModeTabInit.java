package io.github.notwhiterice.circuitsmod.init;

import io.github.notwhiterice.circuitsmod.Circuits;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class CreativeModeTabInit {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Circuits.modID);

    public static final RegistryObject<CreativeModeTab> tabModTab = CREATIVE_MODE_TABS.register(Circuits.modID,
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ItemInit.itemHandWrench.get()))
                    .title(Component.translatable("creativetab.circuitsmod"))
                    .displayItems((param, out) -> {
                        out.accept(ItemInit.itemHandWrench.get());
                        out.accept(BlockInit.blockClearGlass.get());
                    })
                    .build());

    public static void register(IEventBus bus) {
        CREATIVE_MODE_TABS.register(bus);
    }


}
