package net.mo1ecule.flashpoint.effect;

import com.petrolpark.destroy.effect.*;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class FlashpointMobEffects {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS;
    public static final RegistryObject<MobEffect> WINGS;

    public FlashpointMobEffects() {
    }

    public static void register(IEventBus eventBus) {
        MOB_EFFECTS.register(eventBus);
    }

    static {
        MOB_EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, "flashpoint");
        WINGS = MOB_EFFECTS.register("wings", WingsMobEffect::new);
        //CHEMICAL_POISON = MOB_EFFECTS.register("chemical_poison", ChemicalPoisonMobEffect::new);
    }
}