package io.github.notwhiterice.endlessskies.datagen.tag;

public enum BlockStateProviderTag {
    SIMPLE_BLOCKS(),
    BLOCKS_WITHOUT_ITEM(),
    BLOCKS_WITH_MODEL(),
    BLOCKS_WITHOUT_MODEL(),
    BLOCKS_WITHOUT_MODEL_OR_ITEM(),
    SKIP_DATAGEN();

    BlockStateProviderTag() {}
}