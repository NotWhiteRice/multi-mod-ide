package io.github.notwhiterice.endlessskies.inventory;

import io.github.notwhiterice.endlessskies.Reference;
import io.github.notwhiterice.endlessskies.block.entity.CrudeSmelterBlockEntity;
import io.github.notwhiterice.endlessskies.block.entity.factory.TileEntityContext;
import io.github.notwhiterice.endlessskies.capabilities.ESCapabilities;
import io.github.notwhiterice.endlessskies.inventory.factory.BasicMenu;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.SlotItemHandler;

import java.util.concurrent.atomic.AtomicInteger;

public class CrudeSmelterMenu extends BasicMenu<CrudeSmelterBlockEntity> {
    public CrudeSmelterMenu(int id, Inventory inv, FriendlyByteBuf data) {
        this(id, inv, inv.player.level().getBlockEntity(data.readBlockPos()), new SimpleContainerData(2));
    }

    public CrudeSmelterMenu(int id, Inventory inv, BlockEntity entity, ContainerData data) {
        super(id, inv, entity, data);
        checkContainerSize(inv, 3);

        container.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(itemHandler -> {
            this.addSlot(new SlotItemHandler(itemHandler, 0, 56, 27));
            this.addSlot(new SlotItemHandler(itemHandler, 1, 116, 35));
        });
    }

    public boolean isCrafting() {
        return data.get(0) > 0;
    }
    public boolean isLit() {
        AtomicInteger heat = new AtomicInteger();
        container.getCapability(ESCapabilities.HEAT_HANDLER).ifPresent(heatHandler -> {
            heat.set(heatHandler.getHeatInReservoir(0));
        });
        return heat.get() >= 900;
    }
    public boolean isSuperheated() {
        return container.isSuperheated();
    }

    protected int getSlotCount() { return 2; }
    protected int getInvLeft() { return 8; }
    protected int getInvTop() { return 84; }
    protected int getHotbarTop() { return 142; }

    public void updateContainer() { container = (CrudeSmelterBlockEntity) level.getBlockEntity(container.getBlockPos(), TileEntityContext.getContext(Reference.modID, "crude_smelter").getEntityType()).get(); }

    public int getScaledProgress() {
        int progress = data.get(0);
        int maxProgress = data.get(1);
        int progressIndicSize = 22;

        return (maxProgress != 0 && progress != 0) ? progress * progressIndicSize / maxProgress : 0;
    }
    public int getScaledHeat() {
        int heat = container.heatHandler.getHeatInReservoir(0);
        int cap = container.heatHandler.getReservoirCapacity(0);
        int heatIndicSize = 58;

        return (heat != 0 && cap != 0) ? (int)Math.round((double)(heat) * (double)(heatIndicSize) / (double)(cap)) : 0;
    }
    public int getScaledExcess() {
        int heat = container.heatHandler.getHeatInReservoir(0) - 900;
        int cap = container.heatHandler.getReservoirCapacity(0) - 900;
        int heatIndicSize = 13;

        return (heat != 0 && cap != 0) ? (int)Math.round((double)(heat) * (double)(heatIndicSize) / (double)(cap)) : 0;
    }
}
