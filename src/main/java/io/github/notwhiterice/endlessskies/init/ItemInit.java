package io.github.notwhiterice.endlessskies.init;

import io.github.notwhiterice.endlessskies.EndlessSkies;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemInit {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, EndlessSkies.modID);

    public static final RegistryObject<Item> itemHandWrench = ITEMS.register("hand_wrench",
            () -> new Item(new Item.Properties()));

    public static void register(IEventBus bus) { ITEMS.register(bus); }
}
