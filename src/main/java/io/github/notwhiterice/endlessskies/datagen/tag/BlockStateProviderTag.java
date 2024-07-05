package io.github.notwhiterice.endlessskies.datagen.tag;

public enum BlockStateProviderTag {
    SIMPLE_BLOCK(),
    SIMPLE_BLOCK_WITHOUT_ITEM(),
    BLOCK_WITH_MODEL(),
    BLOCK_WITH_MODEL_WITHOUT_ITEM(),
    BLOCK_WITHOUT_MODEL(),
    BLOCK_WITHOUT_MODEL_OR_ITEM(),
    SKIP_DATAGEN();

    BlockStateProviderTag() {}
}