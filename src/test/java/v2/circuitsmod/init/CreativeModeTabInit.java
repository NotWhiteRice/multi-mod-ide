package io.github.deprecated.v2.circuitsmod.init;

import io.github.deprecated.v2.circuitsmod.Circuits;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class CreativeModeTabInit {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Circuits.modID);

    public static final RegistryObject<CreativeModeTab> tabModTab = CREATIVE_MODE_TABS.register(Circuits.modID,
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(Items.REDSTONE))
                    .title(Component.translatable("creativetab.circuitsmod"))
                    .displayItems((param, out) -> {

                    })
                    .build());

    public static void register(IEventBus bus) {
        CREATIVE_MODE_TABS.register(bus);
    }


}
