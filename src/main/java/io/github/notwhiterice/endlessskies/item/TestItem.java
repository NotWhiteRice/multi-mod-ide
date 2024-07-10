package io.github.notwhiterice.endlessskies.item;

import io.github.notwhiterice.endlessskies.block.entity.PanelBlockEntity;
import io.github.notwhiterice.endlessskies.init.BlockInit;
import io.github.notwhiterice.endlessskies.item.factory.BasicItem;
import io.github.notwhiterice.endlessskies.util.DirHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.entity.BlockEntity;

public class TestItem extends BasicItem {
    public TestItem() { super(new Item.Properties()); }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Direction face = context.getClickedFace().getOpposite();
        BlockPos pos = context.getClickedPos().relative(face.getOpposite());

        double mouseX = context.getClickLocation().x - context.getClickedPos().getX();
        double mouseY = context.getClickLocation().y - context.getClickedPos().getY();
        double mouseZ = context.getClickLocation().z - context.getClickedPos().getZ();
        double faceX = mouseX, faceY = mouseY;

        if(DirHelper.onYAxis(face)) faceY = 1-mouseZ;
        if(face == Direction.WEST) faceX = 1-mouseZ;
        if(face == Direction.SOUTH) faceX = 1-mouseX;
        if(face == Direction.EAST) faceX = mouseZ;

        Direction facing = Direction.NORTH;
        if(faceX > faceY && faceX>=1-faceY) facing=Direction.EAST;
        if(faceX >= faceY && faceX<1-faceY) facing=Direction.SOUTH;
        if(faceX < faceY && faceX<=1-faceY) facing=Direction.WEST;
        if(faceX <= 0.75 && faceX >= 0.25 && faceY <= 0.75 && faceY >= 0.25) facing = Direction.NORTH;

        if(context.getLevel().setBlock(pos, BlockInit.blockPanel.get().defaultBlockState(), 2)) {
            BlockEntity entity = context.getLevel().getBlockEntity(pos);

            if(entity instanceof PanelBlockEntity) {
                PanelBlockEntity tile = (PanelBlockEntity) entity;
                tile.face = face;
                tile.facing = facing;
            }
        }

        return super.useOn(context);
    }
}
