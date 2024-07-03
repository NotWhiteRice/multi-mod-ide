package io.github.notwhiterice.endlessskies.registries.object;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.RegistryObject;

public abstract class ItemLikeContext {
    protected RegistryObject<? extends ItemLike> rObject;
    protected String modID;
    protected String name;

    public ItemLike asItemLike() { return rObject.get(); }
    public RegistryObject<? extends ItemLike> asRegistryObject() { return rObject; }

    public <T extends ItemLikeContext> T setCreativeTab(ResourceKey<CreativeModeTab> tabKey) {
        CreativeModeTabContext.addContextToVanillaTab(this, tabKey);
        return (T) this;
    }

    public String getModID() { return modID; }
    public String getName() { return name; }

}
