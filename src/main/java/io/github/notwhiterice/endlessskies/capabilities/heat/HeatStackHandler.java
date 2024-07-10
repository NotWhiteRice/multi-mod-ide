    package io.github.notwhiterice.endlessskies.capabilities.heat;

    import net.minecraft.core.NonNullList;

    public class HeatStackHandler implements IHeatHandlerModifiable {
        private final int slots;
        private final NonNullList<HeatStack> stacks = NonNullList.create();

        public HeatStackHandler(int slots) {
            this.slots = slots;
            for(int i = 0; i < slots; i++) stacks.add(new HeatStack(0, 0, 10));
        }
        public HeatStackHandler(int slots, int capacity) {
            this.slots = slots;
            for(int i = 0; i < slots; i++) stacks.add(new HeatStack(0, capacity, 10));
        }

        public HeatStackHandler copy() {
            HeatStackHandler out = new HeatStackHandler(slots);
            for(int i = 0; i < slots; i++) out.stacks.set(i, out.stacks.get(i).copy());
            return out;
        }

        public String toString() {
            String out = "";
            for(HeatStack stack : stacks) out = stack.toString() + "/";
            out.substring(0, out.length()-1);
            return out;
        }

        public void fromString(String data) {
            String[] parsed = data.split("/");
            if(parsed.length != slots) throw new IllegalStateException("String has different amount of slots than this HeatStackHandler");
            for(int i = 0; i < slots; i++) stacks.get(i).fromString(parsed[i]);
        }

        public int getSlots() { return slots; }
        public boolean hasSlot(int slot) { return slot >= 0 && slot < slots; }

        public HeatStack getStackInSlot(int slot) {
            if(!hasSlot(slot)) throw new IllegalArgumentException("Invalid reservoir slot given: " + slot);
            return stacks.get(slot);
        }

        public HeatStack setStackInSlot(int slot, HeatStack stack) {
            if(!hasSlot(slot)) throw new IllegalArgumentException("Invalid reservoir slot given: " + slot);
            onContentsChanged(slot);
            return stacks.set(slot, stack);
        }


        public int getHeatInSlot(int slot) {
            if(!hasSlot(slot)) throw new IllegalArgumentException("Invalid reservoir slot given: " + slot);
            return stacks.get(slot).getHeat();
        }
        public HeatStack setHeatInSlot(int slot, int heat, boolean simulate) {
            if(!hasSlot(slot)) throw new IllegalArgumentException("Invalid reservoir slot given: " + slot);
            HeatStack out = stacks.get(slot).setHeat(heat, simulate, true);
            if(!simulate) onContentsChanged(slot);
            return simulate ? out : stacks.get(slot);
        }
        public int getSlotCapacity(int slot) {
            if(!hasSlot(slot)) throw new IllegalArgumentException("Invalid reservoir slot given: " + slot);
            return stacks.get(slot).getCapacity();
        }
        public HeatStack setSlotCapacity(int slot, int capacity, boolean simulate) {
            if(!hasSlot(slot)) throw new IllegalArgumentException("Invalid reservoir slot given: " + slot);
            HeatStack out = stacks.get(slot).setCapacity(capacity, simulate, true);
            if(!simulate) onContentsChanged(slot);
            return simulate ? out : stacks.get(slot);
        }
        public int getSourceForSlot(int slot) {
            if(!hasSlot(slot)) throw new IllegalArgumentException("Invalid reservoir slot given: " + slot);
            return stacks.get(slot).getHeatSource();
        }
        public HeatStack setSourceForSlot(int slot, int desired, boolean simulate) {
            if(!hasSlot(slot)) throw new IllegalArgumentException("Invalid reservoir slot given: " + slot);
            HeatStack out = stacks.get(slot).setDesiredHeat(desired, simulate, true);
            if(!simulate) onContentsChanged(slot);
            return simulate ? out : stacks.get(slot);
        }
        public int getExchangeRateForSlot(int slot) {
            if(!hasSlot(slot)) throw new IllegalArgumentException("Invalid reservoir slot given: " + slot);
            return stacks.get(slot).getExchangeRate();
        }
        public HeatStack setExchangeRateForSlot(int slot, int rate, boolean simulate) {
            if(!hasSlot(slot)) throw new IllegalArgumentException("Invalid reservoir slot given: " + slot);
            HeatStack out = stacks.get(slot).setExchangeRate(rate, simulate, true);
            if(!simulate) onContentsChanged(slot);
            return simulate ? out : stacks.get(slot);
        }
        public int getLossRateForSlot(int slot) {
            if(!hasSlot(slot)) throw new IllegalArgumentException("Invalid reservoir slot given: " + slot);
            return stacks.get(slot).getLossRate();
        }
        public HeatStack setLossRateForSlot(int slot, int rate, boolean simulate) {
            if(!hasSlot(slot)) throw new IllegalArgumentException("Invalid reservoir slot given: " + slot);
            HeatStack out = stacks.get(slot).setLossRate(rate, simulate, true);
            if(!simulate) onContentsChanged(slot);
            return simulate ? out : stacks.get(slot);
        }
        public int getAmbientHeatForSlot(int slot) {
            if(!hasSlot(slot)) throw new IllegalArgumentException("Invalid reservoir slot given: " + slot);
            return stacks.get(slot).getAmbientHeat();
        }
        public HeatStack setAmbientHeatForSlot(int slot, int ambient, boolean simulate) {
            if(!hasSlot(slot)) throw new IllegalArgumentException("Invalid reservoir slot given: " + slot);
            HeatStack out = stacks.get(slot).setAmbientHeat(ambient, simulate, true);
            if(!simulate) onContentsChanged(slot);
            return simulate ? out : stacks.get(slot);
        }
        public boolean canSlotInput(int slot) {
            if(!hasSlot(slot)) throw new IllegalArgumentException("Invalid reservoir slot given: " + slot);
            return stacks.get(slot).canInput();
        }
        public HeatStack setInputForSlot(int slot, boolean canInput, boolean simulate) {
            if(!hasSlot(slot)) throw new IllegalArgumentException("Invalid reservoir slot given: " + slot);
            if(stacks.get(slot).canInput() == canInput) return simulate ? stacks.get(slot).copy() : stacks.get(slot);
            HeatStack out = stacks.get(slot).setInput(canInput, simulate, true);
            if(!simulate) onContentsChanged(slot);
            return simulate ? out : stacks.get(slot);
        }
        public boolean canSlotOutput(int slot) {
            if(!hasSlot(slot)) throw new IllegalArgumentException("Invalid reservoir slot given: " + slot);
            return stacks.get(slot).canOutput();
        }
        public HeatStack setOutputForSlot(int slot, boolean canOutput, boolean simulate) {
            if(!hasSlot(slot)) throw new IllegalArgumentException("Invalid reservoir slot given: " + slot);
            if(stacks.get(slot).canOutput() == canOutput) return simulate ? stacks.get(slot).copy() : stacks.get(slot);
            HeatStack out = stacks.get(slot).setOutput(canOutput, simulate, true);
            if(!simulate) onContentsChanged(slot);
            return simulate ? out : stacks.get(slot);
        }

        public int getCreativeBonusForSlot(int slot) {
            if(!hasSlot(slot)) throw new IllegalArgumentException("Invalid reservoir slot given: " + slot);
            return stacks.get(slot).getCreativeBonus();
        }

        public HeatStack setCreativeBonusForSlot(int slot, int bonus, boolean simulate) {
            if(!hasSlot(slot)) throw new IllegalArgumentException("Invalid reservoir slot given: " + slot);
            if(stacks.get(slot).getCreativeBonus() == bonus) return simulate ? stacks.get(slot).copy() : stacks.get(slot);
            HeatStack out = stacks.get(slot).setCreativeBonus(bonus, simulate, true);
            if(!simulate) onContentsChanged(slot);
            return simulate ? out : stacks.get(slot);
        }

        public boolean isSlotCreativeHeated(int slot) {
            if(!hasSlot(slot)) throw new IllegalArgumentException("Invalid reservoir slot given: " + slot);
            return stacks.get(slot).isCreativeHeated();
        }

        public HeatStack setSlotCreativeHeated(int slot, boolean heated, boolean simulate) {
            if(!hasSlot(slot)) throw new IllegalArgumentException("Invalid reservoir slot given: " + slot);
            if(stacks.get(slot).isCreativeHeated() == heated) return simulate ? stacks.get(slot).copy() : stacks.get(slot);
            HeatStack out = stacks.get(slot).setCreativeHeated(heated, simulate, true);
            if(!simulate) onContentsChanged(slot);
            return simulate ? out : stacks.get(slot);
        }

        public HeatStack clearHeatSourceForSlot(int slot, boolean simulate) {
            if(!hasSlot(slot)) throw new IllegalArgumentException("Invalid reservoir slot given: " + slot);
            HeatStack temp = stacks.get(slot);
            (simulate ? temp : stacks.get(slot)).clearHeatSource();
            if(!simulate) onContentsChanged(slot);
            return (simulate ? temp : stacks.get(slot));
        }

        public HeatStack acceptSourceInSlot(int slot, HeatStack source, boolean simulate) {
            if(!hasSlot(slot)) throw new IllegalArgumentException("Invalid reservoir slot given: " + slot);
            HeatStack temp = stacks.get(slot);
            (simulate ? temp : stacks.get(slot)).acceptSource(source, false, true);
            if(!simulate) onContentsChanged(slot);
            return (simulate ? temp : stacks.get(slot));
        }

        public boolean canDrain(int slot, int heat) {
            if(!hasSlot(slot)) throw new IllegalArgumentException("Invalid reservoir slot given: " + slot);
            return stacks.get(slot).canDrain(heat);
        }
        public HeatStack drain(int slot, int heat, boolean simulate) {
            if(!hasSlot(slot)) throw new IllegalArgumentException("Invalid reservoir slot given: " + slot);
            HeatStack out = stacks.get(slot).drain(heat, simulate, true);
            if(!simulate) onContentsChanged(slot);
            return simulate ? out : stacks.get(slot);
        }

        public HeatStackHandler tick(boolean simulate) {
            HeatStackHandler temp0 = copy();
            if(!simulate) {
                for(int i = 0; i < slots; i++) {
                    HeatStack temp1 = stacks.get(i);
                    stacks.get(i).tick();
                    if(!temp1.equals(stacks.get(i))) onContentsChanged(i);
                }
            } else temp0.tick(false);
            return simulate ? temp0 : this;
        }

        public void onContentsChanged(int slot) {}
    }
