package io.github.notwhiterice.endlessskies.datagen;

import io.github.notwhiterice.endlessskies.Reference;
import io.github.notwhiterice.endlessskies.block.factory.BlockContext;
import io.github.notwhiterice.endlessskies.datagen.tag.BlockStateProviderTag;
import io.github.notwhiterice.endlessskies.registry.object.ModContext;
import io.github.notwhiterice.endlessskies.registry.object.ModLogger;
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
        ModLogger logger = ModContext.getContext(modID).logger;
        for(BlockContext context : BlockContext.getModBlocks(modID)) {
            String name = context.getName();
            switch(context.getStateDataTag()) {
                case BlockStateProviderTag.SIMPLE_BLOCK:
                    logger.debug("ModBlockStateProvider", "registerStatesAndModels", "Generating a standard block state for id: '" + context.getID() + "'");
                    simpleBlockWithItem(context.getBlock(), cubeAll(context.getBlock()));
                    break;
                case BlockStateProviderTag.SIMPLE_BLOCK_WITHOUT_ITEM:
                    logger.debug("ModBlockStateProvider", "registerStatesAndModels", "Generating a standard block state w/o item for id: '" + context.getID() + "'");
                    simpleBlock(context.getBlock(), cubeAll(context.getBlock()));
                    break;
                case BlockStateProviderTag.BLOCK_WITH_MODEL:
                    logger.debug("ModBlockStateProvider", "registerStatesAndModels", "Generating a block state with a custom model for id: '" + context.getID() + "'");
                    simpleBlockWithItem(context.getBlock(), new ModelFile.UncheckedModelFile(modLoc("block/" + context.rObject.getId().getPath())));
                    break;
                case BlockStateProviderTag.BLOCK_WITH_MODEL_WITHOUT_ITEM:
                    logger.debug("ModBlockStateProvider", "registerStatesAndModels", "Generating a block state with a custom model w/o item for id: '" + context.getID() + "'");
                    simpleBlock(context.getBlock(), new ModelFile.UncheckedModelFile(modLoc("block/" + context.rObject.getId().getPath())));
                    break;
                case BlockStateProviderTag.BLOCK_WITHOUT_MODEL:
                    logger.debug("ModBlockStateProvider", "registerStatesAndModels", "Generating a block state using the default model for id: '" + context.getID() + "'");
                    simpleBlockWithItem(context.getBlock(), new ModelFile.UncheckedModelFile(ResourceLocation.fromNamespaceAndPath(Reference.modID, "block/unimplemented_block_model")));
                    break;
                case BlockStateProviderTag.BLOCK_WITHOUT_MODEL_OR_ITEM:
                    logger.debug("ModBlockStateProvider", "registerStatesAndModels", "Generating a block state with a custom model w/o item for id: '" + context.getID() + "'");
                    simpleBlock(context.getBlock(), new ModelFile.UncheckedModelFile(ResourceLocation.fromNamespaceAndPath(Reference.modID, "block/unimplemented_block_model")));
                    break;
            }
        }
    }
}