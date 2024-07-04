package io.github.deprecated.v3.endlessskies.block;

import com.mojang.serialization.MapCodec;
import io.github.deprecated.v3.endlessskies.block.entity.TestBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class TestBlock extends BaseEntityBlock {
    public TestBlock() { super(BlockBehaviour.Properties.ofFullCopy(Blocks.GRASS_BLOCK)); }
    public TestBlock(Block.Properties prop) { this(); }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if(!level.isClientSide()) {
            BlockEntity bEntity = level.getBlockEntity(pos);
            if(bEntity instanceof TestBlockEntity) {
                TestBlockEntity entity = (TestBlockEntity) bEntity;

                entity.timesClicked++;
                entity.setChanged();

                player.sendSystemMessage(Component.literal("You have clicked this block x" + entity.timesClicked + " times"));
            }// else {
                //throw new IllegalStateException("This black has no tile entity.");
            // }
        }
        return InteractionResult.PASS;
    }

    @Override
    public MapCodec<TestBlock> codec() { return simpleCodec(TestBlock::new); }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new TestBlockEntity(pos, state);
    }
}
