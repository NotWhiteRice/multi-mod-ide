package io.github.notwhiterice.endlessskies.datagen;

import io.github.notwhiterice.endlessskies.EndlessSkies;
import io.github.notwhiterice.endlessskies.registries.object.BlockContext;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

import java.util.Map;

public class ModBlockStateProvider extends BlockStateProvider {
    private final String modID = EndlessSkies.modID;

    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, EndlessSkies.modID, exFileHelper);
    }

    @Override
    public void registerStatesAndModels() {
        if(BlockContext.isModKnown(modID)) {
            for(Map.Entry<String, BlockContext> entry : BlockContext.getContextsForMod(modID).entrySet()) {
                String name = entry.getKey();
                BlockContext context = entry.getValue();
                switch(context.getStateProviderTag()) {
                    case SIMPLE_BLOCKS:
                        simpleBlockWithItem(context.asBlock(), cubeAll(context.asBlock()));
                        break;
                    case BLOCKS_WITHOUT_ITEM:
                        simpleBlock(context.asBlock(), cubeAll(context.asBlock()));
                        break;
                    case BLOCKS_WITH_MODEL:
                        simpleBlockWithItem(context.asBlock(), new ModelFile.UncheckedModelFile(modLoc("block/" + context.asRegistryObject().getId().getPath())));
                        break;
                    case BLOCKS_WITHOUT_MODEL:
                        simpleBlockWithItem(context.asBlock(), new ModelFile.UncheckedModelFile(ResourceLocation.fromNamespaceAndPath(EndlessSkies.modID, "block/unimplemented_block_model")));
                        break;
                    case BLOCKS_WITHOUT_MODEL_OR_ITEM:
                        simpleBlock(context.asBlock(), new ModelFile.UncheckedModelFile(ResourceLocation.fromNamespaceAndPath(EndlessSkies.modID, "block/unimplemented_block_model")));
                        break;
                }
            }
        }
    }

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
