package io.github.notwhiterice.endlessskies.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;

public class DirHelper {

    public static boolean onYAxis(Direction... dirs) {
        for(Direction dir : dirs) if(dir != Direction.UP && dir != Direction.DOWN) return false;
        return true;
    }

    public static Direction getDirFromNeighbors(BlockPos pos, BlockPos neighbor) {
        for(Direction side : Direction.values()) if(neighbor.equals(pos.relative(side))) return side;
        return null;
    }
}
