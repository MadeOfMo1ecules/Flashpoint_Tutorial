package net.mo1ecule.flashpoint.effect;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class WingsFlapsProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {

    public static Capability<WingsFlaps> PLAYER_FLAPS = CapabilityManager.get(new CapabilityToken<WingsFlaps>() { });

    private WingsFlaps flaps = null;
    private final LazyOptional<WingsFlaps> optional = LazyOptional.of(this::createWingsFlaps);

    private WingsFlaps createWingsFlaps() {
        if(this.flaps == null){
            this.flaps = new WingsFlaps();
        }
        return this.flaps;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == PLAYER_FLAPS){
            return optional.cast();
        }
        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createWingsFlaps().saveNBTData(nbt);

        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createWingsFlaps().loadNBTData(nbt);
    }
}
