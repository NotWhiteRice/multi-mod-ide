package io.github.notwhiterice.endlessskies.datagen.tag;

import io.github.notwhiterice.endlessskies.Reference;
import net.minecraft.ResourceLocationException;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class ModTags {
    public static class Blocks {
        private static TagKey<Block> tag(String name) {
            return BlockTags.create(ResourceLocation.fromNamespaceAndPath(Reference.modID, name));
        }
    }

    public static class ItemTags {

    }
}
