package io.github.notwhiterice.endlessskies.block.factory;

import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

public class BlockFactory {
    private Block.Properties bProp;
    private Class<? extends Block> bClass;
    private Supplier<? extends Block> bSupplier;

    public BlockFactory(Block.Properties prop) { bProp = prop; }
    public BlockFactory(Class<? extends Block> classObj) { bClass = classObj; }
    public BlockFactory(Supplier<? extends Block> supplier) { bSupplier = supplier; }
    public BlockFactory(Block.Properties prop, Class<? extends Block> classObj) {
        bProp = prop;
        bClass = classObj;
    }

    public Supplier<? extends Block> generate() {
        if(bSupplier != null) return bSupplier;
        if(bClass != null) {
            if(bProp == null) {
                return () -> {
                    try {
                        return bClass.newInstance();
                    } catch (Exception e) { throw new RuntimeException(e); }
                };
            }
            return () -> {
                try {
                    return bClass.getDeclaredConstructor(Block.Properties.class).newInstance(bProp);
                } catch (Exception e) { throw new RuntimeException(e); }
            };
        }
        return () -> new Block(bProp);
    }
}
