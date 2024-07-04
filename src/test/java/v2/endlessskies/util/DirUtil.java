package io.github.deprecated.v2.endlessskies.util;

import net.minecraft.core.BlockPos;
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
    public static final EnumSet<DirUtil> X_AXIS = EnumSet.of
            (WEST, EAST);
    public static final EnumSet<DirUtil> Y_AXIS = EnumSet.of
            (UP, DOWN);
    public static final EnumSet<DirUtil> Z_AXIS = EnumSet.of
            (NORTH, SOUTH);
    public static final EnumSet<DirUtil> POSITIVES = EnumSet.of
            (SOUTH, UP, EAST);
    public static final EnumSet<DirUtil> NEGATIVES = EnumSet.of
            (NORTH, DOWN, WEST);

    private static final Map<Direction, DirUtil> reverseLookup = new IdentityHashMap<>();
    static {
        for(DirUtil obj : DirUtil.values()) reverseLookup.put(obj.get(), obj);
    }

    private final Direction direction;

    DirUtil(Direction dir) { direction = dir; }

    public Direction get() { return direction; }
    public static Direction getDirFromValue(DirUtil obj) { return obj.get(); }
    public static DirUtil fromDir(Direction dir) { return reverseLookup.get(dir); }

    public DirUtil cycle(DirUtil obj) {
        if(this == UP) return DOWN;
        if(this == DOWN) return NORTH;
        if(this == NORTH) return WEST;
        if(this == WEST) return SOUTH;
        if(this == SOUTH) return EAST;
        return UP;
    }

    public DirUtil flip() {
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

    public boolean isCardinal() { return CARDINALS.contains(this); }
    public boolean isOnXAxis() { return X_AXIS.contains(this); }
    public boolean isOnYAxis() { return Y_AXIS.contains(this); }
    public boolean isOnZAxis() { return Z_AXIS.contains(this); }
    public boolean isPositive() { return POSITIVES.contains(this); }
    public boolean isNegative() { return NEGATIVES.contains(this); }

    public static boolean areCardinal(DirUtil... objs) {
        for(DirUtil dir : objs) if(!dir.isCardinal()) return false;
        return true;
    }

    public static boolean areOnXAxis(DirUtil... objs) {
        for(DirUtil dir : objs) if(!dir.isOnXAxis()) return false;
        return true;
    }

    public static boolean areOnYAxis(DirUtil... objs) {
        for(DirUtil dir : objs) if(!dir.isOnYAxis()) return false;
        return true;
    }

    public static boolean areOnZAxis(DirUtil... objs) {
        for(DirUtil dir : objs) if(!dir.isOnZAxis()) return false;
        return true;
    }

    public static boolean arePositive(DirUtil... objs) {
        for(DirUtil dir : objs) if(!dir.isPositive()) return false;
        return true;
    }

    public static boolean areNegative(DirUtil... objs) {
        for(DirUtil dir : objs) if(!dir.isNegative()) return false;
        return true;
    }

    public static boolean areEqual(DirUtil... objs) {
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

    public static DirUtil findRelativeCardinal(DirUtil facing, DirUtil relative) {
        if(relative == null) return NORTH;
        if(facing == NORTH) return relative;
        if(facing == WEST) return relative.rotateY(false);
        if(facing == SOUTH) return relative.flip();
        if(facing == EAST) return relative.rotateY(true);
        return NORTH;
    }

    public static DirUtil undoRelativeCardinal(DirUtil facing, DirUtil absolute) {
        if(absolute == null) return NORTH;
        if(facing == NORTH) return absolute;
        if(facing == WEST) return absolute.rotateY(true);
        if(facing == SOUTH) return absolute.flip();
        if(facing == EAST) return absolute.rotateY(false);
        return NORTH;
    }

    public static DirUtil getDirFromNeighbors(BlockPos pos, BlockPos neighbor) {
        for(Direction dir : Direction.values()) if(neighbor.equals(pos.relative(dir))) return reverseLookup.get(dir);
        return null;
    }
}
