package io.github.notwhiterice.endlessskies.inventory;

import io.github.notwhiterice.endlessskies.Reference;
import io.github.notwhiterice.endlessskies.block.entity.CreativeHeaterBlockEntity;
import io.github.notwhiterice.endlessskies.block.entity.MineralInfuserBlockEntity;
import io.github.notwhiterice.endlessskies.block.factory.BasicBlock;
import io.github.notwhiterice.endlessskies.block.factory.BlockContext;
import io.github.notwhiterice.endlessskies.inventory.factory.BasicMenu;
import io.github.notwhiterice.endlessskies.inventory.factory.MenuContext;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.SlotItemHandler;

public class CreativeHeaterMenu extends BasicMenu<CreativeHeaterBlockEntity> {
    public CreativeHeaterMenu(int id, Inventory inv, FriendlyByteBuf data) {
        this(id, inv, inv.player.level().getBlockEntity(data.readBlockPos()), new SimpleContainerData(1));
    }

    public CreativeHeaterMenu(int id, Inventory inv, BlockEntity entity, ContainerData data) {
        super(id, inv, entity, data);
    }

    protected int getSlotCount() { return 0; }
    protected int getInvLeft() { return 8; }
    protected int getInvTop() { return 84; }
    protected int getHotbarTop() { return 142; }
}
