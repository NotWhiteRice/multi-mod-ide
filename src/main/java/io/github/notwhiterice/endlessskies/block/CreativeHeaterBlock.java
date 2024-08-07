package io.github.notwhiterice.endlessskies.block;

import com.mojang.serialization.MapCodec;
import io.github.notwhiterice.endlessskies.Reference;
import io.github.notwhiterice.endlessskies.block.factory.MenuEntityBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class CreativeHeaterBlock extends MenuEntityBlock {
    public CreativeHeaterBlock() { super(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)); defineContext(Reference.modID, "creative_heater"); }

    public CreativeHeaterBlock(Properties prop) { this(); }

    @Override
    public MapCodec<CreativeHeaterBlock> codec() { return simpleCodec(CreativeHeaterBlock::new); }
}
