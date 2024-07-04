package io.github.deprecated.v1.endlessskies.core.block.state.properties;

import net.minecraft.util.StringRepresentable;

public enum CircuitConnection implements StringRepresentable {
    JOINT("joint"),
    INPUT("input"),
    OUTPUT("output"),
    NONE("none");

    private final String name;

    private CircuitConnection(final String name) { this.name = name; }

    public String toString() { return this.getSerializedName(); }

    public String getSerializedName() { return this.name; }

    public boolean isNone() { return this == NONE; }
    public boolean isIO() { return this != NONE; }
    public boolean isInput() { return this == INPUT || this == JOINT; }
    public boolean isInputS() { return this == INPUT; }
    public boolean isOutput() { return this == OUTPUT || this == JOINT; }
    public boolean isOutputS() { return this == OUTPUT; }
    public boolean isJoint() { return this == JOINT; }

    public CircuitConnection invertIO() { return invertIO(this); }

    public static CircuitConnection invertIO(CircuitConnection conn) {
        if(conn.isNone()) return JOINT;
        if(conn.isInputS()) return OUTPUT;
        if(conn.isOutputS()) return INPUT;
        return NONE;
    }
}
