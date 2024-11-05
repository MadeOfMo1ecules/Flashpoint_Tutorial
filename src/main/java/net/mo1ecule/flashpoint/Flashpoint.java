package net.mo1ecule.flashpoint;

import com.mojang.logging.LogUtils;
import com.petrolpark.destroy.Destroy;
import com.petrolpark.destroy.advancement.DestroyAdvancementTrigger;
import com.petrolpark.destroy.chemistry.api.Chemistry;
import com.petrolpark.destroy.chemistry.forge.event.ForgeChemistryEventFirer;
import com.petrolpark.destroy.chemistry.legacy.index.*;
import com.petrolpark.destroy.config.DestroyAllConfigs;
import com.petrolpark.destroy.fluid.pipeEffectHandler.DestroyOpenEndedPipeEffects;
import com.petrolpark.destroy.item.compostable.DestroyCompostables;
import com.petrolpark.destroy.item.potatoCannonProjectileType.DestroyPotatoCannonProjectileTypes;
import com.petrolpark.destroy.network.DestroyMessages;
import com.petrolpark.destroy.recipe.DestroyExtrusions;
import com.petrolpark.destroy.stats.DestroyStats;
import com.petrolpark.destroy.util.vat.VatMaterial;
import com.simibubi.create.content.equipment.goggles.GogglesItem;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import net.mo1ecule.flashpoint.chemistry.legacy.index.FlashpointReactions;
import net.mo1ecule.flashpoint.item.FlashpointCreativeModeTabs;
import net.mo1ecule.flashpoint.item.FlashpointItems;

import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Flashpoint.MOD_ID)
public class Flashpoint {
    public static final String MOD_ID = "flashpoint";
    public static final Logger LOGGER = LogUtils.getLogger();

    public Flashpoint() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        FlashpointCreativeModeTabs.register(modEventBus);

        FlashpointItems.register(modEventBus);

        //modEventBus.addListener(this::commonSetup);

        MinecraftForge.EVENT_BUS.register(this);
        modEventBus.addListener(this::addCreative);

        //Initiation Events
        modEventBus.addListener(Flashpoint::init);
    }

    //Initiation Events
    public static void init(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            DestroyGroupFinder.register();
            DestroyTopologies.register();
            DestroyMolecules.register();
            DestroyReactions.register();
            DestroyGenericReactions.register();
            DestroyReactions.register();
            FlashpointReactions.register();
        });

        // Config
        //GogglesItem.addIsWearingPredicate(player -> player.isCreative() && DestroyAllConfigs.SERVER.automaticGoggles.get());
    };

    //private void commonSetup(final FMLCommonSetupEvent event) {
//
    //}

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event) {

    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {

        }
    }
}
