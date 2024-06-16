package io.github.notwhiterice.nwrcorelib.creativetabs;

import io.github.notwhiterice.nwrcorelib.blocks.BlockInit;
import io.github.notwhiterice.nwrcorelib.items.ItemInit;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

public class TabBase extends CreativeModeTab {
    public TabBase(String tabID, RegistryObject<Item> icon, List<RegistryObject<? extends ItemLike>> tabContents) {
        super(CreativeModeTab.builder()
                .icon(() -> new ItemStack(icon.get()))
                .title(Component.translatable("creativetab." + tabID))
                .displayItems((param, out) ->{
                    for(RegistryObject<? extends ItemLike> rObject : tabContents) out.accept(rObject.get());
                }));
    }
}
