package io.github.notwhiterice.circuitsmod.datagen;

import io.github.notwhiterice.circuitsmod.Circuits;
import io.github.notwhiterice.circuitsmod.init.BlockInit;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, Circuits.modID, exFileHelper);
    }

    @Override
    public void registerStatesAndModels() {
        blockWithItem(BlockInit.blockClearGlass);
    }

    private void blockWithItem(RegistryObject<Block> rObj) {
        simpleBlockWithItem(rObj.get(), cubeAll(rObj.get()));
    }
}