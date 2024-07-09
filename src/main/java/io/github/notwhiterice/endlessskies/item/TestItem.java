package io.github.notwhiterice.endlessskies.item;

import io.github.notwhiterice.endlessskies.block.entity.PanelBlockEntity;
import io.github.notwhiterice.endlessskies.init.BlockInit;
import io.github.notwhiterice.endlessskies.item.base.BasicItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.entity.BlockEntity;

public class TestItem extends BasicItem {
    public TestItem() { super(new Item.Properties()); }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        if(pContext.isInside()) return InteractionResult.PASS;
        Direction face = pContext.getClickedFace().getOpposite();
        BlockPos pos = pContext.getClickedPos().relative(face.getOpposite());
        if(pContext.getLevel().setBlock(pos, BlockInit.blockPanel.get().defaultBlockState(), 2)) {
            BlockEntity entity = pContext.getLevel().getBlockEntity(pos);

            if(entity instanceof PanelBlockEntity) {
                PanelBlockEntity tile = (PanelBlockEntity) entity;
                tile.face = face;
            }
        }

        return super.useOn(pContext);
    }
}
