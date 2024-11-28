package net.mo1ecule.flashpoint.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class WingsMobEffect extends MobEffect {
    public WingsMobEffect() {
        super(MobEffectCategory.BENEFICIAL, 15800000);
    }

    public void applyEffectTick(LivingEntity player, int pAmplifier) {
        super.applyEffectTick(player, pAmplifier);
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return true;
    }
}