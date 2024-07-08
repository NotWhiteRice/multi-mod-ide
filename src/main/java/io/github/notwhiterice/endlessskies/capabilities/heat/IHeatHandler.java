package io.github.notwhiterice.endlessskies.capabilities.heat;

import org.jetbrains.annotations.NotNull;

public interface IHeatHandler {
    int getReservoirs();

    int getHeatInReservoir(int slot);

    IHeatHandler setHeatInReservoir(int slot, int heat, boolean simulate);

    int getReservoirCapacity(int slot);

    boolean canFill(int slot, int heat);
    IHeatHandler fill(int slot, int heat, boolean simulate);

    boolean canDrain(int slot, int heat);
    IHeatHandler drain(int slot, int heat, boolean simulate);
}
