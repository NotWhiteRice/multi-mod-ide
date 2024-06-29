package io.github.notwhiterice.deprecated.endlessskies030alpha.core.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;

import java.util.EnumSet;

public class DirectionUtils {

    public static EnumSet<Direction> cardinals = EnumSet.of(Direction.NORTH, Direction.WEST, Direction.SOUTH, Direction.EAST);

    public static boolean isEqual(Direction... dirs) {
        Direction temp = dirs[0];
        for(Direction dir : dirs) if(dir != temp) return false;
        return true;
    }

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

    public static boolean hasOneAxis(Direction... dirs) {
        Direction temp = dirs[0];
        if(onXAxis(temp)) return onXAxis(dirs);
        if(onYAxis(temp)) return onYAxis(dirs);
        return onZAxis(dirs);
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
