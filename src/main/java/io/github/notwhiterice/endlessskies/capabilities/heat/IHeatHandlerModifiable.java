package io.github.notwhiterice.endlessskies.capabilities.heat;

public interface IHeatHandlerModifiable extends IHeatHandler {

    HeatStack setStackInSlot(int slot, HeatStack stack);

    HeatStack setHeatInSlot(int slot, int heat, boolean simulate);
    HeatStack setSlotCapacity(int slot, int capacity, boolean simulate);
    HeatStack setSourceForSlot(int slot, int source, boolean simulate);
    HeatStack setExchangeRateForSlot(int slot, int rate, boolean simulate);
    HeatStack setLossRateForSlot(int slot, int rate, boolean simulate);
    HeatStack setAmbientHeatForSlot(int slot, int ambient, boolean simulate);
}
