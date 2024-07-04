package io.github.deprecated.v1.endlessskies.core.creativetab;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.RegistryObject;

public abstract class ICreativeTab extends CreativeModeTab {
    public ICreativeTab(String tabID, RegistryObject<? extends ItemLike> icon, RegistryObject<? extends ItemLike>[] tabContents) {
        super(CreativeModeTab.builder()
                .icon(() -> new ItemStack(icon.get()))
                .title(Component.translatable("creativetab." + tabID))
                .displayItems((param, out) ->{
                    for(RegistryObject<? extends ItemLike> rObject : tabContents) out.accept(rObject.get());
                }));
    }
}
