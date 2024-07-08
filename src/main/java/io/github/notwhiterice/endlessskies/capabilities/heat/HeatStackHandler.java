    package io.github.notwhiterice.endlessskies.capabilities.heat;

    public class HeatStackHandler implements IHeatHandler {
        private final int slots;
        private final int[] caps;
        private final int[] storage;

        public HeatStackHandler(int slots, int... caps) {
            this.slots = slots;
            this.caps = caps;
            this.storage = new int[caps.length];
            if(caps.length != slots && caps.length != 1) throw new IllegalArgumentException("The either maximum for each reservoir or maximum for each reservoir must be provided when creating a HeatStackHandler");
        }

        public HeatStackHandler copy() {
            HeatStackHandler out = new HeatStackHandler(slots, caps);
            for(int i = 0; i < slots; i++) out.storage[i] = storage[i];
            return out;
        }

        public int getReservoirs() { return slots; }

        public int getHeatInReservoir(int slot) {
            if(slot < 0 || slot >= slots) throw new IllegalArgumentException("Invalid reservoir slot given: " + slot);
            return storage[slot];
        }

        public HeatStackHandler setHeatInReservoir(int slot, int heat, boolean simulate) {
            if(slot < 0 || slot >= slots) throw new IllegalArgumentException("Invalid reservoir slot given: " + slot);
            HeatStackHandler temp = copy();
            int value = Math.min(heat, caps[slot]);
            (simulate ? temp : this).storage[slot] = value;
            if(!simulate) onContentsChanged(slot);
            return (simulate ? temp : this);
        }

        public int getReservoirCapacity(int slot) {
            if(slot < 0 || slot >= slots) throw new IllegalArgumentException("Invalid reservoir slot given: " + slot);
            return caps[slot];
        }

        public boolean canFill(int slot, int heat) {
            if(slot < 0 || slot >= slots) throw new IllegalArgumentException("Invalid reservoir slot given: " + slot);
            return storage[slot] + heat <= caps[slot];
        }

        public HeatStackHandler fill(int slot, int heat, boolean simulate) {
            if(slot < 0 || slot >= slots) throw new IllegalArgumentException("Invalid reservoir slot given: " + slot);
            HeatStackHandler temp = copy();
            int value = Math.min(storage[slot] + heat, caps[slot]);
            (simulate ? temp : this).storage[slot] = value;
            if(!simulate) onContentsChanged(slot);
            return (simulate ? temp : this);
        }

        public boolean canDrain(int slot, int heat) {
            if(slot < 0 || slot >= slots) throw new IllegalArgumentException("Invalid reservoir slot given: " + slot);
            return storage[slot] >= heat;
        }

        public HeatStackHandler drain(int slot, int heat, boolean simulate) {
            if(slot < 0 || slot >= slots) throw new IllegalArgumentException("Invalid reservoir slot given: " + slot);
            HeatStackHandler temp = copy();
            int value = Math.max(storage[slot] - heat, 0);
            (simulate ? temp : this).storage[slot] = value;
            if(!simulate) onContentsChanged(slot);
            return (simulate ? temp : this);
        }

        public void onContentsChanged(int slot) {}
    }
