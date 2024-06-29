package io.github.notwhiterice.deprecated.circuitsmod040alpha.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class SignalEmitterBlock extends BaseEntityBlock {

    public SignalEmitterBlock(Properties prop) { this(); }

    public SignalEmitterBlock() {
        super(BlockBehaviour.Properties.ofFullCopy(Blocks.REDSTONE_BLOCK));
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return null;
    }

    @Override
    public MapCodec<SignalEmitterBlock> codec() { return simpleCodec(SignalEmitterBlock::new); }
}
