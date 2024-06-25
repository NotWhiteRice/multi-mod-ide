package io.github.notwhiterice.endlessskies.blocks;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class StonePanelBlock extends PanelBlock {
    public static final MapCodec<StonePanelBlock> CODEC = simpleCodec(StonePanelBlock::new);

    public StonePanelBlock(Properties prop) {
        this();
    }

    public StonePanelBlock() {
        super(BlockBehaviour.Properties.ofFullCopy(Blocks.REPEATER));
        this.registerDefaultState(((BlockState) this.stateDefinition.any()).setValue(FACE, Direction.DOWN));
    }

    public MapCodec<StonePanelBlock> codec() { return CODEC; }
}
