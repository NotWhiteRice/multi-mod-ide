package io.github.deprecated.v3.endlessskies.datagen;

import io.github.deprecated.v2.endlessskies.EndlessSkies;
import io.github.deprecated.v3.endlessskies.datagen.tag.BlockStateProviderTag;
import io.github.deprecated.v3.endlessskies.registry.ModRegistry;
import io.github.deprecated.v3.endlessskies.registry.object.BlockContext;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModBlockStateProvider extends BlockStateProvider {
    private final String modID;

    public ModBlockStateProvider(PackOutput output, String modID, ExistingFileHelper fileHelper) {
        super(output, modID, fileHelper);
        this.modID = modID;
    }

    @Override
    public void registerStatesAndModels() {
        if(ModRegistry.isModKnown(modID)) {
            for(BlockContext context : ModRegistry.getModBlocks(modID)) {
                String name = context.getName();
                switch(context.getStateProviderTag()) {
                    case BlockStateProviderTag.SIMPLE_BLOCKS:
                        simpleBlockWithItem(context.asBlock(), cubeAll(context.asBlock()));
                        break;
                    case BlockStateProviderTag.BLOCK_WITHOUT_ITEM:
                        simpleBlock(context.asBlock(), cubeAll(context.asBlock()));
                        break;
                    case BlockStateProviderTag.BLOCK_WITH_MODEL:
                        simpleBlockWithItem(context.asBlock(), new ModelFile.UncheckedModelFile(modLoc("block/" + context.asRegistryObject().getId().getPath())));
                        break;
                    case BlockStateProviderTag.BLOCK_WITHOUT_MODEL:
                        simpleBlockWithItem(context.asBlock(), new ModelFile.UncheckedModelFile(ResourceLocation.fromNamespaceAndPath(EndlessSkies.modID, "block/unimplemented_block_model")));
                        break;
                    case BlockStateProviderTag.BLOCK_WITHOUT_MODEL_OR_ITEM:
                        simpleBlock(context.asBlock(), new ModelFile.UncheckedModelFile(ResourceLocation.fromNamespaceAndPath(EndlessSkies.modID, "block/unimplemented_block_model")));
                        break;
                }
            }
        }
    }
}
