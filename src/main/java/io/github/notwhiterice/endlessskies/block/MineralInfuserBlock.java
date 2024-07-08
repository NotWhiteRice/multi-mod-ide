package io.github.notwhiterice.endlessskies.block;

import com.mojang.serialization.MapCodec;
import io.github.notwhiterice.endlessskies.Reference;
import io.github.notwhiterice.endlessskies.block.entity.MineralInfuserBlockEntity;
import io.github.notwhiterice.endlessskies.block.entity.factory.MenuBlockEntity;
import io.github.notwhiterice.endlessskies.block.entity.factory.TileEntityContext;
import io.github.notwhiterice.endlessskies.block.factory.MenuEntityBlock;
import io.github.notwhiterice.endlessskies.inventory.MineralInfuserMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class MineralInfuserBlock extends MenuEntityBlock<MineralInfuserBlockEntity> {

    public MineralInfuserBlock() { super(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK)); defineContext(Reference.modID, "mineral_infuser");}
    public MineralInfuserBlock(Properties prop) { this(); }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean wasPistoned) {
        if(state.getBlock() != newState.getBlock()) {
            BlockEntity bEntity = level.getBlockEntity(pos);
            if(bEntity instanceof MineralInfuserBlockEntity) {
                ((MineralInfuserBlockEntity) bEntity).dropInventory();
            }
        }
        super.onRemove(state, level, pos, newState, wasPistoned);
    }

    @Override
    public MapCodec<MineralInfuserBlock> codec() { return simpleCodec(MineralInfuserBlock::new); }
}
