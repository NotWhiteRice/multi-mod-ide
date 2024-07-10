package io.github.notwhiterice.endlessskies.block.entity.factory;

import io.github.notwhiterice.endlessskies.block.factory.BasicBlock;
import io.github.notwhiterice.endlessskies.block.factory.BasicEntityBlock;
import io.github.notwhiterice.endlessskies.registry.block.BlockContext;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public abstract class BasicBlockEntity extends BlockEntity {
    protected final BlockContext context;

    public BasicBlockEntity(BlockPos pos, BlockState state) {
        super(((BasicBlock) state.getBlock()).context.container.get(), pos, state);
        context = ((BasicBlock) state.getBlock()).context;
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider lookupProvider) {
        CompoundTag nbt = super.getUpdateTag(lookupProvider);
        saveAdditional(nbt, lookupProvider);
        return nbt;
    }

    @Override
    public void setChanged() {
        super.setChanged();
        level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), BasicEntityBlock.UPDATE_ALL);
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    public abstract void tick(Level level, BlockPos pos, BlockState state);
}
