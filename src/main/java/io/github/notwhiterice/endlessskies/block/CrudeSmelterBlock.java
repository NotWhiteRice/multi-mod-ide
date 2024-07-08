package io.github.notwhiterice.endlessskies.block;

import com.mojang.serialization.MapCodec;
import io.github.notwhiterice.endlessskies.Reference;
import io.github.notwhiterice.endlessskies.block.entity.CrudeSmelterBlockEntity;
import io.github.notwhiterice.endlessskies.block.entity.RockCrusherBlockEntity;
import io.github.notwhiterice.endlessskies.block.factory.BasicBlock;
import io.github.notwhiterice.endlessskies.block.factory.BasicEntityBlock;
import io.github.notwhiterice.endlessskies.block.factory.MenuEntityBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DiodeBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraftforge.fluids.capability.IFluidHandler;
import org.jetbrains.annotations.Nullable;

public class CrudeSmelterBlock extends MenuEntityBlock<CrudeSmelterBlockEntity> {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty LIT = BooleanProperty.create("lit");

    public CrudeSmelterBlock() {
        super(BlockBehaviour.Properties.ofFullCopy(Blocks.BRICKS));
        defineContext(Reference.modID, "crude_smelter");
        registerDefaultState(stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(LIT, false));
    }

    public CrudeSmelterBlock(Properties prop) { this(); }

    @Override
    public void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, LIT);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean wasPistoned) {
        if(state.getBlock() != newState.getBlock()) {
            BlockEntity bEntity = level.getBlockEntity(pos);
            if(bEntity instanceof CrudeSmelterBlockEntity) {
                ((CrudeSmelterBlockEntity) bEntity).dropInventory();
            }
        }
        super.onRemove(state, level, pos, newState, wasPistoned);
    }

    @Override
    protected MapCodec<CrudeSmelterBlock> codec() { return simpleCodec(CrudeSmelterBlock::new); }
}
