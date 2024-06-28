package io.github.notwhiterice.endlessskies.core.util;

import net.minecraft.core.Direction;

public class DirectionUtils {
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

    public static Direction joinCardinals(Direction first, Direction second) {
        if(first == Direction.NORTH) return second;
        if(first == Direction.WEST) return second.getCounterClockWise();
        if(first == Direction.SOUTH) return second.getOpposite();
        if(first == Direction.EAST) return second.getClockWise();
        return null;
    }
}
