package net.mo1ecule.flashpoint.item.food;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;
import net.mo1ecule.flashpoint.effect.FlashpointMobEffects;

public class FlashpointFoods {

    // Foods
    public static final FoodProperties
        RED_BELL = new FoodProperties.Builder().nutrition(2).saturationMod(0.2f).effect(() -> new MobEffectInstance(FlashpointMobEffects.WINGS.get(), 36000), 1f).build();
    };
