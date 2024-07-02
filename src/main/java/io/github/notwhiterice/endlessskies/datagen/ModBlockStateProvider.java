package io.github.notwhiterice.endlessskies.datagen;

import io.github.notwhiterice.endlessskies.EndlessSkies;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;

public class ModBlockStateProvider extends BlockStateProvider {
    private static final List<RegistryObject<? extends Block>> SIMPLE_BLOCKS_WITH_ITEM = new ArrayList<>();
    private static final List<RegistryObject<? extends Block>> SIMPLE_BLOCKS_WITHOUT_ITEM = new ArrayList<>();
    private static final List<RegistryObject<? extends Block>> BLOCKS_WITH_MODEL_AND_ITEM = new ArrayList<>();

    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, EndlessSkies.modID, exFileHelper);
    }

    @Override
    public void registerStatesAndModels() {
        for(RegistryObject<? extends Block> rObj : SIMPLE_BLOCKS_WITH_ITEM) blockWithItem(rObj);
        for(RegistryObject<? extends Block> rObj : SIMPLE_BLOCKS_WITHOUT_ITEM) blockWithoutItem(rObj);
        for(RegistryObject<? extends Block> rObj : BLOCKS_WITH_MODEL_AND_ITEM) blockWithModelAndItem(rObj);
    }

    public static <T extends Block> void insertBlock(RegistryObject<T> rObj) { SIMPLE_BLOCKS_WITH_ITEM.add(rObj); }
    public static <T extends Block> void insertBlockWithoutItem(RegistryObject<T> rObj) { SIMPLE_BLOCKS_WITHOUT_ITEM.add(rObj); }

    public static <T extends Block> void insertBlockWIthModel(RegistryObject<T> rObj) { BLOCKS_WITH_MODEL_AND_ITEM.add(rObj); }

    private void blockWithItem(RegistryObject<? extends Block> rObj) {
        simpleBlockWithItem(rObj.get(), cubeAll(rObj.get()));
    }
    private void blockWithoutItem(RegistryObject<? extends Block> rObj) {
        simpleBlock(rObj.get(), cubeAll(rObj.get()));
    }
    private void blockWithModelAndItem(RegistryObject<? extends Block> rObj) {
        simpleBlockWithItem(rObj.get(), new ModelFile.UncheckedModelFile(modLoc("block/" + rObj.getId().getPath())));
    }
}
