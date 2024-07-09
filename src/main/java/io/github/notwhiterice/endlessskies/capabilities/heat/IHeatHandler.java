package io.github.notwhiterice.endlessskies.capabilities.heat;

public interface IHeatHandler {
    int getSlots();
    HeatStack getStackInSlot(int slot);

    int getHeatInSlot(int slot);

    int getSlotCapacity(int slot);
    int getSourceForSlot(int slot);
    int getExchangeRateForSlot(int slot);
    int getLossRateForSlot(int slot);
    int getAmbientHeatForSlot(int slot);
    int getCreativeBonusForSlot(int slot);
    boolean canSlotInput(int slot);
    boolean canSlotOutput(int slot);
    boolean isSlotCreativeHeated(int slot);

    HeatStack setCreativeBonusForSlot(int slot, int bonus, boolean simulate);

    HeatStack setInputForSlot(int slot, boolean canInput, boolean simulate);
    HeatStack setOutputForSlot(int slot, boolean canOutput, boolean simulate);
    HeatStack setSlotCreativeHeated(int slot, boolean heated, boolean simulate);

    HeatStack clearHeatSourceForSlot(int slot, boolean simulate);


    HeatStack acceptSourceInSlot(int slot, HeatStack source, boolean simulate);

    boolean canDrain(int slot, int heat);
    HeatStack drain(int slot, int heat, boolean simulate);

    IHeatHandler tick(boolean simulate);
}
