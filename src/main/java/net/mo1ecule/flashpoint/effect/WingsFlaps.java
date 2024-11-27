package net.mo1ecule.flashpoint.effect;

import net.minecraft.nbt.CompoundTag;

public class WingsFlaps {
    int flaps;
    int maxFlaps = 3;
    boolean jump = false;

    public int getFlaps(){ return flaps; }

    public void resetFlaps(){ this.flaps = 0; }

    public void addFlaps(){ this.flaps = Math.min(this.flaps + 1, maxFlaps); }

    public int getMaxFlaps() { return maxFlaps; }



    public void jumpBounce() { this.jump = false; }

    public void jumpDebounce() { this.jump = true; }

    public boolean isDebounced() { return this.jump; }


    public void copyFrom(WingsFlaps source){
        this.flaps = source.flaps;
    }

    public void saveNBTData(CompoundTag nbt){
        nbt.putInt("flaps", flaps);
    }

    public void loadNBTData(CompoundTag nbt){
        flaps = nbt.getInt("flaps");
    }
}
