package io.github.notwhiterice.endlessskies.block;

import com.mojang.serialization.MapCodec;
import io.github.notwhiterice.endlessskies.block.base.BasicEntityBlock;
import io.github.notwhiterice.endlessskies.block.entity.RockCrusherBlockEntity;
import io.github.notwhiterice.endlessskies.init.BlockEntityInit;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class RockCrusherBlock extends BasicEntityBlock {

    public RockCrusherBlock() {
        super(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE));
        prepareBlockEntity(RockCrusherBlockEntity.class);
    }

    public RockCrusherBlock(Properties prop) {
        super(prop);
        prepareBlockEntity(RockCrusherBlockEntity.class);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if(!level.isClientSide()) {
            BlockEntity bEntity = level.getBlockEntity(pos);
            if(bEntity instanceof RockCrusherBlockEntity) {
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

        return createTickerHelper(bEntityType, BlockEntityInit.tileEntityRockCrusher.get(),
                (pLevel, pos, pState, bEntity) -> bEntity.tick(pLevel, pos, pState));
    }

    @Override
    public MapCodec<RockCrusherBlock> codec() { return simpleCodec(RockCrusherBlock::new); }
}
