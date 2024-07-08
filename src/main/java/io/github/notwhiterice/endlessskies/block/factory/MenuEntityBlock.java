package io.github.notwhiterice.endlessskies.block.factory;

import com.mojang.serialization.MapCodec;
import io.github.notwhiterice.endlessskies.block.entity.factory.BasicBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public abstract class MenuEntityBlock<T extends BasicBlockEntity<T, ? extends BasicEntityBlock<T>> & MenuProvider> extends BasicEntityBlock<T> {
    public MenuEntityBlock(Block.Properties prop) { super(prop); }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if(!level.isClientSide() && context.hasMenu()) {
            BlockEntity bEntity = level.getBlockEntity(pos);
            if(context.container.factory.create(pos, state).getClass().isInstance(bEntity))    {
                ServerPlayer sPlayer = (ServerPlayer) player;
                sPlayer.openMenu((MenuProvider) bEntity, buf -> buf.writeBlockPos(pos));
            } else {
                throw new IllegalStateException("Our Container provider is missing!");
            }
        }

        return InteractionResult.SUCCESS;
    }


    protected abstract MapCodec<? extends MenuEntityBlock<T>> codec();
}
