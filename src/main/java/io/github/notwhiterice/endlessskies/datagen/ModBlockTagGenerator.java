package io.github.notwhiterice.endlessskies.datagen;

import io.github.notwhiterice.endlessskies.EndlessSkies;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagGenerator extends BlockTagsProvider {


    public ModBlockTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, EndlessSkies.modID, existingFileHelper);
    }

    @Override
    public void addTags(HolderLookup.Provider provider) {

    }
}
