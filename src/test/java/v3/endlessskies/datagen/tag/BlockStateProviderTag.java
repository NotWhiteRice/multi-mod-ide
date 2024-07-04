package io.github.deprecated.v3.endlessskies.datagen.tag;

public enum BlockStateProviderTag {
    SIMPLE_BLOCKS(),
    BLOCK_WITHOUT_ITEM(),
    BLOCK_WITH_MODEL(),
    BLOCK_WITHOUT_MODEL(),
    BLOCK_WITHOUT_MODEL_OR_ITEM(),
    SKIP_DATAGEN();

    BlockStateProviderTag() {}
}