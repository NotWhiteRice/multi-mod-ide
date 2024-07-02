package io.github.notwhiterice.endlessskies.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Constructor;

public abstract class MenuTileEntity extends BasicTileEntity implements MenuProvider {
    private Class<? extends AbstractContainerMenu> menuClass;

    public MenuTileEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        try {
            Constructor<? extends AbstractContainerMenu> constructor = menuClass.getConstructor(int.class, Inventory.class, BlockEntity.class, ContainerData.class);

            return constructor.newInstance(i, inventory, this, this.data);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void prepareMenu(Class<? extends AbstractContainerMenu> menu) { menuClass = menu; }
}
