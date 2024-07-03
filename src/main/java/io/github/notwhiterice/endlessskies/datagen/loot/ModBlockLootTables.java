package io.github.notwhiterice.endlessskies.datagen.loot;

import io.github.notwhiterice.endlessskies.EndlessSkies;
import io.github.notwhiterice.endlessskies.init.BlockInit;
import io.github.notwhiterice.endlessskies.init.HiddenInit;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

import java.util.Set;

public class ModBlockLootTables extends BlockLootSubProvider {
    public ModBlockLootTables(HolderLookup.Provider provider) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), provider);
    }

    @Override
    protected void generate() {
        this.dropSelf(BlockInit.blockMineralInfuser.asBlock());
        this.dropSelf(BlockInit.blockRockCrusher.asBlock());
        this.dropSelf(HiddenInit.blockTest.asBlock());
        //this.dropSelf(BlockInit.blockFaceComposite.get());
        //this.dropWhenSilkTouch(BlockInit.blockClearGlass.get());
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return EndlessSkies.context.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}
