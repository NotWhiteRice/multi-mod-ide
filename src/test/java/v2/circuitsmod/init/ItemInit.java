package io.github.deprecated.v2.circuitsmod.init;

import io.github.deprecated.v2.circuitsmod.Circuits;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemInit {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Circuits.modID);

    public static void register(IEventBus bus) { ITEMS.register(bus); }
}