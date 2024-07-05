package io.github.deprecated.v3.endlessskies.temp;

import io.github.deprecated.v3.endlessskies.block.entity.TestBlockEntity;
import io.github.deprecated.v3.endlessskies.registry.object.ModContext;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.RegistryObject;

public class TileEntityInit {

    public static RegistryObject<BlockEntityType<TestBlockEntity>> tileEntityTest;


    public static void registerEntities(ModContext context) {
        tileEntityTest = context.TILE_ENTITIES.register("test_tile", () -> BlockEntityType.Builder.of(
                TestBlockEntity::new, HiddenInit.blockTest.asBlock()).build(null));
    }
}
