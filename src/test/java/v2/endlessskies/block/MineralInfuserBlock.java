package io.github.deprecated.v2.endlessskies.block;

import com.mojang.serialization.MapCodec;
import io.github.deprecated.v2.endlessskies.block.entity.MineralInfuserBlockEntity;
import io.github.deprecated.v2.endlessskies.init.BlockEntityInit;
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

public class MineralInfuserBlock extends BaseEntityBlock {

    public MineralInfuserBlock() { super(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)); }
    public MineralInfuserBlock(Properties prop) { super(prop); }

    @Override
    public RenderShape getRenderShape(BlockState state) { return RenderShape.MODEL; }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new MineralInfuserBlockEntity(pos, state);
    }

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
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if(!level.isClientSide()) {
            BlockEntity bEntity = level.getBlockEntity(pos);
            if(bEntity instanceof MineralInfuserBlockEntity) {
                ServerPlayer sPlayer = (ServerPlayer) player;
                sPlayer.openMenu((MenuProvider) bEntity, buf -> buf.writeBlockPos(pos));
            } else {
                throw new IllegalStateException("Our Container provider is missing!");
            }
        }

        return InteractionResult.SUCCESS;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> bEntityType) {
        if(level.isClientSide) return null;

        return createTickerHelper(bEntityType, BlockEntityInit.tileEntityMineralInfuser.get(),
                (pLevel, pos, pState, bEntity) -> bEntity.tick(pLevel, pos, pState));
    }

    @Override
    public MapCodec<MineralInfuserBlock> codec() { return simpleCodec(MineralInfuserBlock::new); }
}
