package io.github.notwhiterice.endlessskies.block.entity;

import io.github.notwhiterice.endlessskies.block.CreativeHeaterBlock;
import io.github.notwhiterice.endlessskies.block.entity.factory.MenuBlockEntity;
import io.github.notwhiterice.endlessskies.capabilities.heat.HeatStackHandler;
import io.github.notwhiterice.endlessskies.inventory.CreativeHeaterMenu;
import io.netty.buffer.Unpooled;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

public class CreativeHeaterBlockEntity extends MenuBlockEntity<CreativeHeaterBlockEntity, CreativeHeaterBlock> {
    private int output = 1000;

    public CreativeHeaterBlockEntity(BlockPos pos, BlockState state) {
        super(pos, state);
        this.data = new ContainerData() {
            @Override
            public int get(int i) { return i == 0 ? output : 0; }

            @Override
            public void set(int i, int value) { if (i == 0) { output = value; } }

            @Override
            public int getCount() { return 1; }
        };
    }

    @Override
    public void saveAdditional(CompoundTag tag, HolderLookup.Provider lookupProvider) {
        tag.putInt("creative_heater.output", output);
        super.saveAdditional(tag, lookupProvider);
    }

    @Override
    public void loadAdditional(CompoundTag tag, HolderLookup.Provider lookupProvider) {
        super.loadAdditional(tag, lookupProvider);
        output = tag.getInt("creative_heater.output");
    }


    @Override
    public void tick(Level level, BlockPos pos, BlockState state) { }
}
