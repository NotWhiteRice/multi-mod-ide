package io.github.notwhiterice.endlessskies.block.factory.data;

public enum BlockStateFactory {
    SIMPLE_BLOCK("simple"),
    BLOCK_WITH_MODEL("custom"),
    BLOCK_WITHOUT_MODEL("default"),
    SKIP_DATAGEN("skip");

    private final String action;

    BlockStateFactory(String action) { this.action = action; }
}
