package io.github.notwhiterice.endlessskies.block.entity.factory;

import io.netty.buffer.Unpooled;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public abstract class MenuBlockEntity extends BasicBlockEntity implements MenuProvider {
    protected ContainerData data;

    public MenuBlockEntity(BlockPos pos, BlockState state) { super(pos, state); }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block." + context.getID("."));
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        Class<? extends AbstractContainerMenu> mClass = context.container.menu.factory.create(i, inventory, new FriendlyByteBuf(Unpooled.buffer()).writeBlockPos(worldPosition)).getClass();
        try {
            return mClass.getDeclaredConstructor(int.class, Inventory.class, BlockEntity.class, ContainerData.class)
                    .newInstance(i, inventory, this, this.data);
        } catch(Exception e) { throw new RuntimeException(e); }
    }
}
