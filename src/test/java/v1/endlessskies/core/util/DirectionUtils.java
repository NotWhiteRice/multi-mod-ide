package io.github.deprecated.v1.endlessskies.core.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;

public class DirectionUtils {

    public static boolean onXAxis(Direction... dirs) {
        for(Direction dir : dirs) if(dir != Direction.EAST && dir != Direction.WEST) return false;
        return true;
    }

    public static boolean onYAxis(Direction... dirs) {
        for(Direction dir : dirs) if(dir != Direction.UP && dir != Direction.DOWN) return false;
        return true;
    }

    public static boolean onZAxis(Direction... dirs) {
        for(Direction dir : dirs) if(dir != Direction.SOUTH && dir != Direction.NORTH) return false;
        return true;
    }

    public static boolean isPositive(Direction... dirs) {
        for(Direction dir : dirs) if(dir != Direction.EAST && dir != Direction.UP && dir != Direction.SOUTH) return false;
        return true;
    }

    public static boolean isNegative(Direction... dirs) {
        for(Direction dir : dirs) if(dir != Direction.WEST && dir != Direction.DOWN && dir != Direction.NORTH) return false;
        return true;
    }

    public static Direction joinCardinals(Direction start, Direction end) {
        if(end == null) return null;
        if(start == Direction.NORTH) return end;
        if(start == Direction.WEST) return end.getCounterClockWise();
        if(start == Direction.SOUTH) return end.getOpposite();
        if(start == Direction.EAST) return end.getClockWise();
        return null;
    }

    public static Direction unjoinCardinals(Direction start, Direction joined) {
        if(joined == null) return null;
        if(start == Direction.NORTH) return joined;
        if(start == Direction.WEST) return joined.getClockWise();
        if(start == Direction.SOUTH) return joined.getOpposite();
        if(start == Direction.EAST) return joined.getCounterClockWise();
        return null;
    }

    public static Direction getDirFromNeighbors(BlockPos pos, BlockPos neighbor) {
        for(Direction dir : Direction.values()) if(neighbor.equals(pos.relative(dir))) return dir;
        return null;
    }
}
