package io.github.notwhiterice.endlessskies.registry.block;


import io.github.notwhiterice.endlessskies.EndlessSkies;
import io.github.notwhiterice.endlessskies.registry.ModContext;
import io.github.notwhiterice.endlessskies.registry.block.data.BlockStateFactory;
import io.github.notwhiterice.endlessskies.registry.block.data.LootTableFactory;
import io.github.notwhiterice.endlessskies.registry.block.entity.TileEntityContext;
import io.github.notwhiterice.endlessskies.registry.item.ItemLikeContext;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;

public class BlockContext extends ItemLikeContext<Block> {
    public static final List<BlockContext> instances = new ArrayList<>();

    public final Block.Properties defaultBlockProp = Block.Properties.of();

    public BlockConstructor<?> blockConstructor = null;
    public Block.Properties customBlockProperties = null;
    public Item.Properties customItemProperties = null;
    public BlockStateFactory stateFactory = BlockStateFactory.BLOCK_WITHOUT_MODEL;
    public LootTableFactory dropFactory = LootTableFactory.DROP_SELF;
    public Supplier<? extends Block> forcedSupplier = null;

    public TileEntityContext<?> container = null;
    public boolean hasItem = true;

    public Function<BlockState, MapColor> mapColor = null;
    public boolean hasCollision = true;
    public SoundType sound = null;
    public ToIntFunction<BlockState> lightEmission = null;
    public float resistance = 0.0F;
    public float hardness = 0.0F;
    public boolean requiresTool = false;
    public boolean hasRandomTicks = false;
    public float friction = 0.6F;
    public float speedFactor = 1.0F;
    public float jumpFactor = 1.0F;
    public boolean canOcclude = true;
    public boolean isAir = false;
    public boolean isIgnitedByLava = false;
    public boolean isLiquid = false;
    public boolean forceSolidOff = false;
    public boolean forceSolidOn = false;
    public PushReaction pushReaction = null;
    public boolean spawnTerrainParticles = true;
    public NoteBlockInstrument instrument = null;
    public boolean replaceable = false;
    public BlockBehaviour.StateArgumentPredicate<EntityType<?>> isValidSpawn = null;
    public BlockBehaviour.StatePredicate isRedstoneConductor = null;
    public BlockBehaviour.StatePredicate isSuffocating = null;
    public BlockBehaviour.StatePredicate isViewBlocking = null;
    public BlockBehaviour.StatePredicate hasPostProcess = null;
    public BlockBehaviour.StatePredicate emissiveRendering = null;
    public boolean dynamicShape = false;
    public BlockBehaviour.OffsetType offsetType = null;

    public BlockContext(ModContext parent, String name) {
        super(parent, name);

        if(registerInstance(instances)) parent.logger.info("BlockContext", "<init>", "Registering block '"+getID(":")+"'");
        else parent.logger.warn("BlockContext", "<init>", "Registered duplicate block for '"+getModID()+":"+name+"' as '"+getID(":")+"'");
    }

    private Block.Properties buildBlockProperties(Block.Properties builder) {
        if(mapColor != null) builder.mapColor(mapColor);
        if(!hasCollision) builder.noCollission();
        if(sound != null) builder.sound(sound);
        if(lightEmission != null) builder.lightLevel(lightEmission);
        if(resistance != 0.0F) builder.explosionResistance(resistance);
        if(hardness != 0.0F) builder.destroyTime(hardness);
        if(requiresTool) builder.requiresCorrectToolForDrops();
        if(hasRandomTicks) builder.randomTicks();
        if(friction != 0.6F) builder.friction(friction);
        if(speedFactor != 1.0F) builder.speedFactor(speedFactor);
        if(jumpFactor != 1.0F) builder.jumpFactor(jumpFactor);
        if(!canOcclude) builder.noOcclusion();
        if(isAir) builder.air();
        if(isIgnitedByLava) builder.ignitedByLava();
        if(isLiquid) builder.liquid();
        if(forceSolidOff) builder.forceSolidOff();
        if(forceSolidOn) builder.forceSolidOn();
        if(pushReaction != null) builder.pushReaction(pushReaction);
        if(!spawnTerrainParticles) builder.noTerrainParticles();
        if(instrument != null) builder.instrument(instrument);
        if(replaceable) builder.replaceable();
        if(isValidSpawn != null) builder.isValidSpawn(isValidSpawn);
        if(isRedstoneConductor != null) builder.isRedstoneConductor(isRedstoneConductor);
        if(isSuffocating != null) builder.isSuffocating(isSuffocating);
        if(isViewBlocking != null) builder.isViewBlocking(isViewBlocking);
        if(hasPostProcess != null) builder.hasPostProcess(hasPostProcess);
        if(emissiveRendering != null) builder.emissiveRendering(emissiveRendering);
        if(dynamicShape) builder.dynamicShape();
        if(offsetType != null) builder.offsetType(offsetType);
        if(requiredFeatures != null) builder.requiredFeatures(requiredFeatures);
        return builder;
    }

    public RegistryObject<Block> getRegistry() {
        if(!EndlessSkies.canRegisterObject()) return null;
        if(registry == null) {
            if(forcedSupplier != null) registry = parentMod.BLOCKS.register(name, forcedSupplier);
            else {
                if(customBlockProperties == null) customBlockProperties = defaultBlockProp;
                customBlockProperties = buildBlockProperties(customBlockProperties);
                if(blockConstructor != null) registry = parentMod.BLOCKS.register(name, () -> blockConstructor.create(customBlockProperties));
                else registry = parentMod.BLOCKS.register(name, () -> new Block(customBlockProperties));
            }
            if(hasItem) {
                if(customItemProperties == null) customItemProperties = defaultItemProp;
                customItemProperties = buildItemProperties(customItemProperties);
                parentMod.ITEMS.register(name, () -> new BlockItem(registry.get(), customItemProperties));
            }
        }
        return registry;
    }

    public boolean hasContainer() { return container != null; }
    public boolean hasMenu() { if(!hasContainer()) return false; return container.menu != null; }


    public static boolean doesContextExist(String modID, String name) { return doesInstanceExist(modID, name, instances); }
    public static BlockContext getContext(String modID, String name) { return getInstance(modID, name, instances); }

    public static List<BlockContext> getModBlocks(String modID) { return getModInstances(modID, instances); }
}
