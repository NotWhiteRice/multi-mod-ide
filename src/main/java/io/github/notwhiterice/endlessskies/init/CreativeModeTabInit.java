package io.github.notwhiterice.endlessskies.init;

import io.github.notwhiterice.endlessskies.EndlessSkies;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class CreativeModeTabInit {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, EndlessSkies.modID);

    public static final RegistryObject<CreativeModeTab> tabModTab = CREATIVE_MODE_TABS.register(EndlessSkies.modID,
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ItemInit.itemHandWrench.get()))
                    .title(Component.translatable("creativetab.endlessskies"))
                    .displayItems((param, out) -> {
                        out.accept(ItemInit.itemHandWrench.get());
                        out.accept(BlockInit.blockMineralInfuser.get());
                    })
                    .build());

    public static void register(IEventBus bus) {
        CREATIVE_MODE_TABS.register(bus);
    }
}
