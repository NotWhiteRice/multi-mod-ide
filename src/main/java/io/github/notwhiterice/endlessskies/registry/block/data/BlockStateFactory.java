package io.github.notwhiterice.endlessskies.registry.block.data;

public enum BlockStateFactory {
    SIMPLE_BLOCK("simple"),
    BLOCK_WITH_MODEL("custom"),
    BLOCK_WITHOUT_MODEL("default"),
    BLOCK_WITH_ERROR_MODEL("error"),
    SKIP_DATAGEN("skip");

    private final String action;

    BlockStateFactory(String action) { this.action = action; }
}
