package io.github.notwhiterice.endlessskies.registry.block;

import io.github.notwhiterice.endlessskies.creativetab.factory.CreativeModeTabContext;
import io.github.notwhiterice.endlessskies.registry.ModContext;
import io.github.notwhiterice.endlessskies.registry.block.data.BlockStateFactory;
import io.github.notwhiterice.endlessskies.registry.block.data.LootTableFactory;
import io.github.notwhiterice.endlessskies.registry.block.entity.TileEntityContext;
import io.github.notwhiterice.endlessskies.registry.inventory.MenuContext;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.flag.FeatureFlag;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.JukeboxSong;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.network.IContainerFactory;

import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;

public class BlockFactory {
    private final ModContext parentMod;
    private BlockContext context;

    private BlockFactory(ModContext context) { this.parentMod = context; }
    private BlockFactory(BlockContext context, ModContext parent) { this(parent); this.context = context; }

    public static BlockFactory init(ModContext modContext) {
        return new BlockFactory(modContext);
    }
    public BlockFactory block(String name) {
        return new BlockFactory(new BlockContext(parentMod, name), parentMod);
    }

    public BlockFactory setParent(BlockConstructor<?> block) {
        context.blockConstructor = block;
        return this;
    }
    public BlockFactory forceSupplier(Supplier<? extends Block> block) {
        context.forcedSupplier = block;
        return this;
    }
    public BlockFactory setStateFactory(BlockStateFactory state) {
        context.stateFactory = state;
        return this;
    }

    public BlockFactory setDrops(LootTableFactory loot) {
        context.dropFactory = loot;
        return this;
    }

    public BlockFactory setName(String name) { return this.applyTranslation(name, "en_us"); }
    public BlockFactory applyTranslation(String name, String locale) {
        context.applyTranslation(name, locale);
        return this;
    }
    public BlockFactory setStackSize(int size) {
        context.stackSize = size;
        return this;
    }
    public BlockFactory setDurability(int durability) {
        context.durability = durability;
        return this;
    }

    public BlockFactory setRarity(Rarity rarity) {
        context.rarity = rarity;
        return this;
    }

    public BlockFactory setFireResistant(boolean resistant) {
        context.isFireResistant = resistant;
        return this;
    }

    public BlockFactory setJukeboxSong(ResourceKey<JukeboxSong> song) {
        context.jukeboxSong = song;
        return this;
    }

    public BlockFactory setRequiredFeatures(FeatureFlag... features) {
        context.requiredFeatures = features;
        return this;
    }

    public BlockFactory setAttributes(ItemAttributeModifiers attributes) {
        context.attributes = attributes;
        return this;
    }

    public BlockFactory setHasItem(boolean hasItem) {
        context.hasItem = hasItem;
        return this;
    }

    public BlockFactory setMapColor(DyeColor color) {
        context.mapColor = (state) -> color.getMapColor();
        return this;
    }
    public BlockFactory setMapColor(MapColor color) {
        context.mapColor = (state) -> color;
        return this;
    }

    public BlockFactory setMapColor(Function<BlockState, MapColor> color) {
        context.mapColor = color;
        return this;
    }

    public BlockFactory setHasCollision(boolean collision) {
        context.hasCollision = collision;
        return this;
    }

    public BlockFactory setSound(SoundType sound) {
        context.sound = sound;
        return this;
    }

    public BlockFactory setLightEmission(int light) {
        context.lightEmission = (state) -> light;
        return this;
    }

    public BlockFactory setLightEmission(ToIntFunction<BlockState> light) {
        context.lightEmission = light;
        return this;
    }

    public BlockFactory setResistance(float resistance) {
        context.resistance = resistance;
        return this;
    }

    public BlockFactory setHardness(float hardness) {
        context.hardness = hardness;
        return this;
    }

    public BlockFactory setStrength(float strength) {
        context.resistance = strength;
        context.hardness = strength;
        return this;
    }

    public BlockFactory setStrength(float hardness, float resistance) {
        context.resistance = resistance;
        context.hardness = hardness;
        return this;
    }

    public BlockFactory setRequiresTool(boolean requiresTool) {
        context.requiresTool = requiresTool;
        return this;
    }

    public BlockFactory setHasRandomTicks(boolean ticks) {
        context.hasRandomTicks = ticks;
        return this;
    }

    public BlockFactory setFriction(float friction) {
        context.friction = friction;
        return this;
    }

    public BlockFactory setSpeedFactor(float factor) {
        context.speedFactor = factor;
        return this;
    }

    public BlockFactory setJumpFactor(float factor) {
        context.jumpFactor = factor;
        return this;
    }

    public BlockFactory setCanOcclude(boolean occlude) {
        context.canOcclude = occlude;
        return this;
    }

    public BlockFactory setIsAir(boolean air) {
        context.isAir = air;
        return this;
    }

    public BlockFactory setIgnitedByLava(boolean ignite) {
        context.isIgnitedByLava = ignite;
        return this;
    }

    public BlockFactory setLiquid(boolean liquid) {
        context.isLiquid = liquid;
        return this;
    }
    public BlockFactory setForceSolidOff(boolean force) {
        context.forceSolidOff = force;
        return this;
    }
    public BlockFactory setForceSolidOn(boolean force) {
        context.forceSolidOn = force;
        return this;
    }

    public BlockFactory setPushReaction(PushReaction reaction) {
        context.pushReaction = reaction;
        return this;
    }

    public BlockFactory setTerrainParticles(boolean particles) {
        context.spawnTerrainParticles = particles;
        return this;
    }

    public BlockFactory setInstrument(NoteBlockInstrument instrument) {
        context.instrument = instrument;
        return this;
    }

    public BlockFactory setReplaceable(boolean replaceable) {
        context.replaceable = replaceable;
        return this;
    }

    public BlockFactory setIsValidSpawn(boolean spawn) {
        context.isValidSpawn = (state, level, pos, entity) -> spawn;
        return this;
    }

    public BlockFactory setIsValidSpawn(Block.StateArgumentPredicate<EntityType<?>> spawn) {
        context.isValidSpawn = spawn;
        return this;
    }

    public BlockFactory setRedstoneConductor(boolean conductor) {
        context.isSuffocating = (state, level, pos) -> conductor;
        return this;
    }

    public BlockFactory setRedstoneConductor(Block.StatePredicate conductor) {
        context.isSuffocating = conductor;
        return this;
    }

    public BlockFactory setSuffocating(boolean suffocate) {
        context.isSuffocating = (state, level, pos) -> suffocate;
        return this;
    }

    public BlockFactory setSuffocating(Block.StatePredicate suffocate) {
        context.isSuffocating = suffocate;
        return this;
    }

    public BlockFactory setViewBlocking(boolean blocking) {
        context.isViewBlocking = (state, level, pos) -> blocking;
        return this;
    }

    public BlockFactory setViewBlocking(Block.StatePredicate blocking) {
        context.isViewBlocking = blocking;
        return this;
    }

    public BlockFactory setHasPostProcess(boolean process) {
        context.hasPostProcess = (state, level, pos) -> process;
        return this;
    }

    public BlockFactory setHasPostProcess(Block.StatePredicate process) {
        context.hasPostProcess = process;
        return this;
    }

    public BlockFactory setEmissiveRendering(boolean emissive) {
        context.emissiveRendering = (state, level, pos) -> emissive;
        return this;
    }

    public BlockFactory setEmissiveRendering(Block.StatePredicate emissive) {
        context.emissiveRendering = emissive;
        return this;
    }

    public BlockFactory setDynamicShape(boolean dynamic) {
        context.dynamicShape = true;
        return this;
    }

    public BlockFactory setOffsetType(Block.OffsetType type) {
        context.offsetType = type;
        return this;
    }

    public BlockFactory setCreativeTab(ResourceKey<CreativeModeTab> tab) {
        context.setCreativeTab(tab);
        return this;
    }

    public BlockFactory setCreativeTab(CreativeModeTabContext tab) {
        context.setCreativeTab(tab);
        return this;
    }


    public BlockFactory setContainer(BlockEntityType.BlockEntitySupplier<? extends BlockEntity> entity) {
        context.container = new TileEntityContext<>(parentMod, context.name, entity, context);
        return this;
    }

    public <T extends BlockEntity>BlockFactory setContainer(BlockEntityType.BlockEntitySupplier<T> entity, BlockEntityRendererProvider<T> renderer) {
        context.container = new TileEntityContext<>(parentMod, context.name, entity, renderer, context);
        return this;
    }

    public <T extends AbstractContainerMenu> BlockFactory setMenu(IContainerFactory<T> menu, MenuScreens.ScreenConstructor<T, ? extends AbstractContainerScreen<T>> screen) {
        if(context.container == null) throw new IllegalStateException("Menus cannot be added before tile entities");
        context.container.menu = new MenuContext<>(parentMod, context.name, menu, screen);
        return this;
    }

    public BlockContext close() {
        BlockContext out = context;
        context = null;
        return out;
    }
}
