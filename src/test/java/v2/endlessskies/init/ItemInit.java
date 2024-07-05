package io.github.deprecated.v2.endlessskies.init;

import io.github.deprecated.v2.endlessskies.EndlessSkies;
import io.github.notwhiterice.endlessskies.core.exception.DualRegistryException;
import io.github.deprecated.v3.endlessskies.datagen.tag.ItemModelProviderTag;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;

public class ItemInit {
    public static ItemContext itemHandWrench;

    public static void registerItems() throws DualRegistryException {
        itemHandWrench = EndlessSkies.context.registerItem("hand_wrench", new Item.Properties())
                .setModelProviderTag(ItemModelProviderTag.SIMPLE_ITEM)
                .setCreativeTab(CreativeModeTabs.TOOLS_AND_UTILITIES);
    }

    public static void register(IEventBus bus) { EndlessSkies.context.ITEMS.register(bus); }
}
