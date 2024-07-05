package io.github.deprecated.v2.endlessskies.init;

import io.github.deprecated.v2.endlessskies.EndlessSkies;
import io.github.notwhiterice.endlessskies.block.entity.MineralInfuserBlockEntity;
import io.github.notwhiterice.endlessskies.block.entity.RockCrusherBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlockEntityInit {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create
            (ForgeRegistries.BLOCK_ENTITY_TYPES, EndlessSkies.modID);

    public static final RegistryObject<BlockEntityType<MineralInfuserBlockEntity>> tileEntityMineralInfuser =
            BLOCK_ENTITY_TYPES.register("mineral_infuser_tile", () -> BlockEntityType.Builder.of(
                    MineralInfuserBlockEntity::new, io.github.deprecated.v2.endlessskies.init.BlockInit.blockMineralInfuser.asBlock()).build(null));
    public static final RegistryObject<BlockEntityType<RockCrusherBlockEntity>> tileEntityRockCrusher =
            BLOCK_ENTITY_TYPES.register("rock_crusher_tile", () -> BlockEntityType.Builder.of(
                    RockCrusherBlockEntity::new, io.github.deprecated.v2.endlessskies.init.BlockInit.blockRockCrusher.asBlock()).build(null));

    public static void register(IEventBus bus) { BLOCK_ENTITY_TYPES.register(bus); }
}
