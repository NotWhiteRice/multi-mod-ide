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
        for(BlockContext context  : BlockContext.getModBlocks(modID)) {
            String name = context.getName();
            switch(context.getStateFactory()) {
                case SIMPLE_BLOCK -> {
                    if(context.hasItem()) simpleBlockWithItem(context.get(), cubeAll(context.get()));
                    else simpleBlock(context.get(), cubeAll(context.get()));
                }
                case BLOCK_WITH_MODEL -> {
                    if(context.hasItem()) simpleBlockWithItem(context.get(), new ModelFile.UncheckedModelFile(modLoc("block/" + context.getName())));
                    else simpleBlock(context.get(), new ModelFile.UncheckedModelFile(modLoc("block/" + context.getName())));
                }
                case BLOCK_WITHOUT_MODEL -> {
                    if(context.hasItem()) simpleBlockWithItem(context.get(), new ModelFile.UncheckedModelFile(ResourceLocation.fromNamespaceAndPath(Reference.modID, "block/unimplemented_model")));
                    else simpleBlock(context.get(), new ModelFile.UncheckedModelFile(ResourceLocation.fromNamespaceAndPath(Reference.modID, "block/unimplemented_model")));
                }
            }
        }
    }
}