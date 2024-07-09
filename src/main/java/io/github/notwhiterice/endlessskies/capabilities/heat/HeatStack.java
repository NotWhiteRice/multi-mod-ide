package io.github.notwhiterice.endlessskies.capabilities.heat;

public class HeatStack {
    private int heat;
    private int capacity;
    private int heatSource;
    private int exchangeRate;
    private int effectiveExchangeRate;
    private int lossRate;
    private int ambientHeat;
    private int creativeBonus;
    private boolean canInput;
    private boolean canOutput;
    private boolean isCreativeHeated;

    public HeatStack(int heat, int capacity, int exchangeRate) {
        this.heat = heat;
        this.capacity = capacity;
        this.ambientHeat = 0;
        this.heatSource = ambientHeat;
        this.exchangeRate = exchangeRate;
        this.effectiveExchangeRate = exchangeRate;
        this.lossRate = 1;
        this.canInput = true;
        this.canOutput = false;
    }

    public HeatStack copy(boolean validate) {
        HeatStack out = new HeatStack(heat, capacity, exchangeRate);
        out.heatSource = heatSource;
        if(validate) out.validate();
        return out;
    }

    public int getHeat() { return heat; }
    public int getCapacity() { return capacity; }
    public int getHeatSource() { return heatSource; }
    public int getExchangeRate() { return exchangeRate; }
    public int getLossRate() { return lossRate; }
    public int getAmbientHeat() { return ambientHeat; }
    public int getCreativeBonus() { return creativeBonus; }
    public boolean canInput() { return canInput; }
    public boolean canOutput() { return canOutput; }
    public boolean isCreativeHeated() { return isCreativeHeated; }
    public boolean isColdStack() { return false; }
    public HeatStack setHeat(int heat, boolean simulate, boolean validate) {
        HeatStack temp = copy(false);
        (simulate ? temp : this).heat = heat;
        if(validate) (simulate ? temp : this).validate();
        return (simulate ? temp : this);
    }
    public HeatStack setCapacity(int capacity, boolean simulate, boolean validate) {
        HeatStack temp = copy(false);
        (simulate ? temp : this).capacity = capacity;
        if(validate) (simulate ? temp : this).validate();
        return (simulate ? temp : this);
    }
    public HeatStack setDesiredHeat(int desired, boolean simulate, boolean validate) {
        HeatStack temp = copy(false);
        (simulate ? temp : this).heatSource = desired;
        if(validate) (simulate ? temp : this).validate();
        return (simulate ? temp : this);
    }
    public HeatStack setExchangeRate(int rate, boolean simulate, boolean validate) {
        HeatStack temp = copy(false);
        (simulate ? temp : this).exchangeRate = rate;
        if(validate) (simulate ? temp : this).validate();
        return (simulate ? temp : this);
    }
    public HeatStack setLossRate(int rate, boolean simulate, boolean validate) {
        HeatStack temp = copy(false);
        (simulate ? temp : this).lossRate = rate;
        if(validate) (simulate ? temp : this).validate();
        return (simulate ? temp : this);
    }
    public HeatStack setAmbientHeat(int ambient, boolean simulate, boolean validate) {
        HeatStack temp = copy(false);
        (simulate ? temp : this).ambientHeat = ambient;
        if(validate) (simulate ? temp : this).validate();
        return (simulate ? temp : this);
    }
    public HeatStack setCreativeBonus(int bonus, boolean simulate, boolean validate) {
        HeatStack temp = copy(false);
        (simulate ? temp : this).creativeBonus = bonus;
        if(validate) (simulate ? temp : this).validate();
        return (simulate ? temp : this);
    }
    public HeatStack setInput(boolean canInput, boolean simulate, boolean validate) {
        HeatStack temp = copy(validate);
        if(validate && !simulate) validate();
        (simulate ? temp : this).canInput = canInput;
        return (simulate ? temp : this);
    }
    public HeatStack setOutput(boolean canOutput, boolean simulate, boolean validate) {
        HeatStack temp = copy(validate);
        if(validate && !simulate) validate();
        (simulate ? temp : this).canOutput = canOutput;
        return (simulate ? temp : this);
    }
    public HeatStack setCreativeHeated(boolean heated, boolean simulate, boolean validate) {
        HeatStack temp = copy(false);
        (simulate ? temp : this).isCreativeHeated = heated;
        if(validate) (simulate ? temp : this).validate();
        return (simulate ? temp : this);
    }

    public HeatStack validate(boolean simulate) {
        HeatStack temp = copy(false);
        if(!simulate) {
            if(heat < 0) heat = 0;
            if(capacity < 0) capacity = 0;
            if(heatSource < 0) heatSource = 0;
            if(exchangeRate < 0) exchangeRate = 0;
            if(lossRate < 0) lossRate = 0;
            if(ambientHeat < 0) ambientHeat = 0;
            if(!isCreativeHeated) creativeBonus = 1;
            if(heat > capacity) heat = capacity;
            if(heatSource < ambientHeat) heatSource = ambientHeat;
            if(heatSource > capacity) heatSource = capacity;
        } else temp.validate(false);
        return (simulate ? temp : this);
    }

    public HeatStack clearHeatSource(boolean simulate, boolean validate) {
        HeatStack temp = copy(false);
        (simulate ? temp : this).heatSource = ambientHeat;
        (simulate ? temp : this).isCreativeHeated = false;
        (simulate ? temp : this).creativeBonus = 1;
        if(validate) (simulate ? temp : this).validate();
        return (simulate ? temp : this);
    }

    public HeatStack acceptSource(HeatStack source, boolean simulate, boolean validateBoth) {
        HeatStack temp = copy(false);
        if(!simulate) {
            if (validateBoth) source.validate(false);
            if(canInput && source.canOutput) {
                effectiveExchangeRate = Math.min(exchangeRate, source.exchangeRate);
                heatSource = Math.max(source.heat, heatSource);
                isCreativeHeated = source.isCreativeHeated || isCreativeHeated;
                creativeBonus = Math.max(source.creativeBonus, creativeBonus);
            }
        } else temp.acceptSource(source, false, validateBoth);
        return (simulate ? temp : this);
    }

    public boolean canDrain(int heat) {
        return this.heat-heat >= ambientHeat;
    }
    public HeatStack drain(int heat, boolean simulate, boolean validate) {
        HeatStack temp = copy(false);
        if(!simulate) this.heat -= heat;
        else temp.drain(heat, false, validate);
        return (simulate ? temp : this);
    }

    public HeatStack tick(boolean simulate, boolean validate) {
        HeatStack temp0 = copy(false);
        if(!simulate) {
            if(validate) validate();
            int value = heat;
            int temp1 = heat < ambientHeat ? 1 : effectiveExchangeRate;
            if(heat >= heatSource) value = Math.max(value - lossRate, heatSource);
            else value = Math.min(value + temp1, heatSource);
            heat = value;
        } else temp0.tick(false, validate);
        return (simulate ? temp0 : this);
    }

    public HeatStack copy() { return copy(true); }
    public HeatStack setHeat(int heat) { return setHeat(heat, false, true); }
    public HeatStack setCapacity(int capacity) { return setCapacity(capacity, false, true); }
    public HeatStack setDesiredHeat(int desired) { return setDesiredHeat(desired, false, true); }
    public HeatStack setExchangeRate(int rate) { return setExchangeRate(rate, false, true); }
    public HeatStack setLossRate(int rate) { return setLossRate(rate, false, true); }
    public HeatStack setAmbientHeat(int ambient) { return setAmbientHeat(ambient, false, true); }
    public HeatStack setCreativeBonus(int bonus) { return setCreativeBonus(bonus, false, true); }
    public HeatStack setInput(boolean canInput) { return setInput(canInput, false, true); }
    public HeatStack setOutput(boolean canOutput) { return setOutput(canInput, false, true); }
    public HeatStack setCreativeHeated(boolean heated) { return setCreativeHeated(heated, false, true); }
    public HeatStack validate() { return validate(false); }
    public HeatStack clearHeatSource() { return clearHeatSource(false, true); }
    public HeatStack drain(int amount) { return drain(amount, false, true); }
    public HeatStack tick() { return tick(false, true); }
}
