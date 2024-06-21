package io.github.notwhiterice.nwrcorelib.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class RegisterBundle {
    public DeferredRegister<Item> items;
    public DeferredRegister<Block> blocks;
    public DeferredRegister<CreativeModeTab> tabs;

    public RegisterBundle(String modID) {
        items = DeferredRegister.create(ForgeRegistries.ITEMS, modID);
        blocks = DeferredRegister.create(ForgeRegistries.BLOCKS, modID);
        tabs = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, modID);
    }
}
