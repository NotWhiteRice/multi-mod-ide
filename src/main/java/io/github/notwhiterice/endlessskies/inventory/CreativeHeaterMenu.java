package io.github.notwhiterice.endlessskies.inventory;

import io.github.notwhiterice.endlessskies.inventory.factory.BasicMenu;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.level.block.entity.BlockEntity;

public class CreativeHeaterMenu extends BasicMenu {
    public CreativeHeaterMenu(int id, Inventory inv, FriendlyByteBuf data) {
        this(id, inv, inv.player.level().getBlockEntity(data.readBlockPos()), new SimpleContainerData(1));
    }

    public CreativeHeaterMenu(int id, Inventory inv, BlockEntity entity, ContainerData data) {
        super(id, inv, entity, data);
    }

    protected int getSlotCount() { return 0; }
    protected int getModifiableSlots() { return 0; }
    protected int getInvLeft() { return 8; }
    protected int getInvTop() { return 84; }
    protected int getHotbarTop() { return 142; }
}
