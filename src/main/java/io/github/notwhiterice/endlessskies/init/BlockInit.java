package io.github.notwhiterice.endlessskies.init;

import io.github.notwhiterice.endlessskies.EndlessSkies;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.TransparentBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class BlockInit {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, EndlessSkies.modID);

    //public static final RegistryObject<Block> blockClearGlass = registerBlock("clear_glass",
            //() -> new TransparentBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS)));

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> bSupplier) {
        RegistryObject<T> out = BLOCKS.register(name, bSupplier);
        registerBlockItem(name, out);
        return out;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> rObj) {
        return ItemInit.ITEMS.register(name, () -> new BlockItem(rObj.get(), new Item.Properties()));
    }

    public static void register(IEventBus bus) {
        BLOCKS.register(bus);
    }
}