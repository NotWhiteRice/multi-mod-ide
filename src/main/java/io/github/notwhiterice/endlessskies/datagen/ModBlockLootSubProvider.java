package io.github.notwhiterice.endlessskies.datagen;

import io.github.notwhiterice.endlessskies.registry.block.BlockContext;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;

import java.util.Set;

public class ModBlockLootSubProvider extends BlockLootSubProvider {
    public ModBlockLootSubProvider(HolderLookup.Provider lookupProvider) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), lookupProvider);
    }

    @Override
    protected void generate() {
        for(BlockContext context : BlockContext.instances) {
            switch(context.dropFactory) {
                case DROP_SELF:
                    this.dropSelf(context.get());
                    break;
                case SILK_TOUCH:
                    this.dropWhenSilkTouch(context.get());
                    break;
            }
        }
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return BlockContext.instances.stream().map(BlockContext::get).toList();
    }
}
