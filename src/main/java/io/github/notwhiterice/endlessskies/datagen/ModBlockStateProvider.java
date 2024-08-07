package io.github.notwhiterice.endlessskies.datagen;

import io.github.notwhiterice.endlessskies.Reference;
import io.github.notwhiterice.endlessskies.logger.ModLogger;
import io.github.notwhiterice.endlessskies.registry.ModContext;
import io.github.notwhiterice.endlessskies.registry.block.BlockContext;
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
        for(BlockContext context  : BlockContext.getModBlocks(modID)) {
            String name = context.name;
            switch(context.stateFactory) {
                case SIMPLE_BLOCK -> {
                    if(context.hasItem) simpleBlockWithItem(context.get(), cubeAll(context.get()));
                    else simpleBlock(context.get(), cubeAll(context.get()));
                }
                case BLOCK_WITH_MODEL -> {
                    if(context.hasItem) simpleBlockWithItem(context.get(), new ModelFile.UncheckedModelFile(modLoc("block/" + context.name)));
                    else simpleBlock(context.get(), new ModelFile.UncheckedModelFile(modLoc("block/" + context.name)));
                }
                case BLOCK_WITHOUT_MODEL -> {
                    if(context.hasItem) simpleBlockWithItem(context.get(), new ModelFile.UncheckedModelFile(ResourceLocation.fromNamespaceAndPath(Reference.modID, "block/unimplemented_model")));
                    else simpleBlock(context.get(), new ModelFile.UncheckedModelFile(ResourceLocation.fromNamespaceAndPath(Reference.modID, "block/unimplemented_model")));
                }
                case BLOCK_WITH_ERROR_MODEL -> {
                    if(context.hasItem) simpleBlockWithItem(context.get(), new ModelFile.UncheckedModelFile(ResourceLocation.fromNamespaceAndPath(Reference.modID, "block/error_model")));
                    else simpleBlock(context.get(), new ModelFile.UncheckedModelFile(ResourceLocation.fromNamespaceAndPath(Reference.modID, "block/error_model")));
                }
            }
        }
    }
}