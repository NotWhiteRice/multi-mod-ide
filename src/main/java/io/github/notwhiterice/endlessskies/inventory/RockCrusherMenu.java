package io.github.notwhiterice.endlessskies.inventory;

import io.github.notwhiterice.endlessskies.capabilities.item.OutputSlot;
import io.github.notwhiterice.endlessskies.inventory.factory.BasicMenu;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.SlotItemHandler;

public class RockCrusherMenu extends BasicMenu {
    public RockCrusherMenu(int id, Inventory inv, FriendlyByteBuf data) {
        this(id, inv, inv.player.level().getBlockEntity(data.readBlockPos()), new SimpleContainerData(2));
    }

    public RockCrusherMenu(int id, Inventory inv, BlockEntity entity, ContainerData data) {
        super(id, inv, entity, data);
        checkContainerSize(inv, 2);

        container.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(itemHandler -> {
            this.addSlot(new SlotItemHandler(itemHandler, 0, 49, 36));
            this.addSlot(new OutputSlot(itemHandler, 1, 115, 36));
        });
    }

    public boolean isCrafting() {
        return data.get(0) > 0;
    }

    protected int getSlotCount() { return 2; }

    protected int getModifiableSlots() { return 1; }
    protected int getInvLeft() { return 8; }
    protected int getInvTop() { return 84; }
    protected int getHotbarTop() { return 142; }

    public int getProgress() {
        return data.get(0);
    }

    public int getScaledProgress() {
        int progress = data.get(0);
        int maxProgress = data.get(1);
        int progressIndicSize = 38;

        return (maxProgress != 0 && progress != 0) ? (int)Math.round((double)progress * (double)progressIndicSize / (double)maxProgress) : 0;
    }
}
