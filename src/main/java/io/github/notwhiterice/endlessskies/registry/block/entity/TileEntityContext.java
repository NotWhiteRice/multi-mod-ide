package io.github.notwhiterice.endlessskies.registry.block.entity;


import io.github.notwhiterice.endlessskies.EndlessSkies;
import io.github.notwhiterice.endlessskies.registry.ModObject;
import io.github.notwhiterice.endlessskies.registry.ModContext;
import io.github.notwhiterice.endlessskies.registry.block.BlockContext;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;

public class TileEntityContext<T extends BlockEntity> extends ModObject<BlockEntityType<T>> {
    public static final List<TileEntityContext<?>> instances = new ArrayList<>();

    public BlockEntityType.BlockEntitySupplier<T> factory;
    public BlockEntityRendererProvider<T> renderer;
    public BlockContext parentBlock;

    //Constructor
    public TileEntityContext(ModContext context, String name, BlockEntityType.BlockEntitySupplier<T> factory, BlockContext parentBlock) {
        super(context, name);
        if(context == null || name == null || factory == null || parentBlock == null) return;
        if(registerInstance(instances)) context.logger.info("TileEntityContext", "<init>(context, name, factory, parent)", "Registering tile entity context for tile entity: '" + getID(":") + "'");
        else context.logger.info("TileEntityContext", "<init>(context, name, factory, parent)", "Registered duplicate tile entity context for tile entity: '" + getModID() + ":" + name + "' as '" + getID(":") + "'");
        this.factory = factory;
        this.parentBlock = parentBlock;
    }
    public TileEntityContext(ModContext context, String name, BlockEntityType.BlockEntitySupplier<T> factory, BlockEntityRendererProvider<T> renderer, BlockContext parentBlock) {
        super(context, name);
        if(context == null || name == null || factory == null || parentBlock == null) return;
        if(registerInstance(instances)) context.logger.info("TileEntityContext", "<init>(context, name, factory, parent)", "Registering tile entity context for tile entity: '" + getID(":") + "'");
        else context.logger.info("TileEntityContext", "<init>(context, name, factory, parent)", "Registered duplicate tile entity context for tile entity: '" + getModID() + ":" + name + "' as '" + getID(":") + "'");
        this.factory = factory;
        this.renderer = renderer;
        this.parentBlock = parentBlock;
    }

    //Getter functions
    public BlockEntityType<T> get() { return super.get(); }
    public RegistryObject<BlockEntityType<T>> getRegistry() {
        if(!EndlessSkies.canRegisterObject()) return null;
        if(registry == null) registry = ModContext.getContext(getModID()).TILE_ENTITIES.register(name,
                () -> BlockEntityType.Builder.of(factory, parentBlock.get()).build(null));
        return registry;
    }
}
