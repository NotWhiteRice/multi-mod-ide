package io.github.notwhiterice.endlessskies.util;

import net.minecraft.core.Direction;

import java.util.EnumSet;
import java.util.IdentityHashMap;
import java.util.Map;

public enum DirUtil {
    UP(Direction.UP),
    DOWN(Direction.DOWN),
    NORTH(Direction.NORTH),
    WEST(Direction.WEST),
    SOUTH(Direction.SOUTH),
    EAST(Direction.EAST)
    ;

    public static final EnumSet<DirUtil> ALL = EnumSet.of
            (UP, DOWN, NORTH, WEST, SOUTH, EAST);
    public static final EnumSet<DirUtil> CARDINALS = EnumSet.of
            (NORTH, WEST, SOUTH, EAST);
    public static final EnumSet<DirUtil> POSITIVE = EnumSet.of
            (SOUTH, UP, EAST);
    public static final EnumSet<DirUtil> NEGATIVE = EnumSet.of
            (NORTH, DOWN, WEST);
    public static final EnumSet<DirUtil> X_AXIS = EnumSet.of
            (WEST, EAST);
    public static final EnumSet<DirUtil> Y_AXIS = EnumSet.of
            (UP, DOWN);
    public static final EnumSet<DirUtil> Z_AXIS = EnumSet.of
            (NORTH, SOUTH);

    private static final Map<Direction, DirUtil> reverseLookup = new IdentityHashMap<>();
    static {
        for(DirUtil obj : DirUtil.values()) reverseLookup.put(obj.get(), obj);
    }

    private final Direction direction;

    DirUtil(Direction dir) { direction = dir; }

    public Direction get() { return direction; }
    public static Direction getDirFromValue(DirUtil obj) { return obj.get(); }

    public DirUtil cycle(DirUtil obj) {
        if(this == UP) return DOWN;
        if(this == DOWN) return NORTH;
        if(this == NORTH) return WEST;
        if(this == WEST) return SOUTH;
        if(this == SOUTH) return EAST;
        return UP;
    }

    public DirUtil getOpposite() {
        if(this == UP) return DOWN;
        if(this == DOWN) return UP;
        if(this == NORTH) return SOUTH;
        if(this == WEST) return EAST;
        if(this == SOUTH) return NORTH;
        return WEST;
    }


    /**
     * Rotates along the x-axis facing west-to-east
     */
    public DirUtil rotateX(boolean clockwise) {
        if(X_AXIS.contains(this)) return this;
        if(this == UP) return clockwise ? SOUTH : NORTH;
        if(this == DOWN) return clockwise ? NORTH : SOUTH;
        if(this == NORTH) return clockwise ? UP : DOWN;
        return clockwise ? DOWN : UP;
    }

    /**
     * Rotates along the y-axis facing up-to-down
     */
    public DirUtil rotateY(boolean clockwise) {
        if(Y_AXIS.contains(this)) return this;
        if(this == NORTH) return clockwise ? EAST : WEST;
        if(this == WEST) return clockwise ? NORTH : SOUTH;
        if(this == SOUTH) return clockwise ? WEST : EAST;
        return clockwise ? SOUTH : NORTH;
    }

    /**
     * Rotates along the z-axis facing north-to-south
     */
    public DirUtil rotateZ(boolean clockwise) {
        if(Z_AXIS.contains(this)) return this;
        if(this == UP) return clockwise ? WEST : EAST;
        if(this == DOWN) return clockwise ? EAST : WEST;
        if(this == WEST) return clockwise ? DOWN : UP;
        return clockwise ? UP : DOWN;
    }

    public static boolean isEqual(DirUtil... objs) {
        DirUtil temp = objs[0];
        for(DirUtil dir : objs) if(dir != temp) return false;
        return true;
    }

    public static boolean hasOneAxis(DirUtil... objs) {
        DirUtil temp0 = objs[0];
        DirUtil temp1 = reverseLookup.get(temp0.get().getOpposite());
        for(DirUtil dir : objs) if(dir != temp0 && dir != temp1) return false;
        return true;
    }
}
