package net.mo1ecule.flashpoint.effect;

import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.KeyboardInput;
import net.minecraft.client.Options;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

public class WingsMobEffect extends MobEffect {
    public WingsMobEffect() {
        super(MobEffectCategory.BENEFICIAL, 15800000);
    }

    public void applyEffectTick(LivingEntity player, int pAmplifier) {
        if (player.level().isClientSide()) {
            if(!player.isSpectator() && !player.onGround() && isJumping()){
                Vec3 speed = player.getDeltaMovement();
                player.setDeltaMovement(speed.x, 0.4, speed.z);
            }
        }
        super.applyEffectTick(player, pAmplifier);
    }

    private boolean isJumping() {
        return Minecraft.getInstance().options.keyJump.isDown(); // return true if the jump button is being pressed
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return true;
    }
}