package net.mo1ecule.flashpoint.event;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;
import net.mo1ecule.flashpoint.Flashpoint;
import net.mo1ecule.flashpoint.effect.WingsFlaps;
import net.mo1ecule.flashpoint.effect.WingsFlapsProvider;
import net.mo1ecule.flashpoint.effect.WingsMobEffect;

import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.lang.String.format;
import static net.minecraft.world.effect.MobEffects.DAMAGE_RESISTANCE;
import static net.mo1ecule.flashpoint.effect.FlashpointMobEffects.WINGS;

@Mod.EventBusSubscriber(modid = Flashpoint.MOD_ID)
public class ModEvents {

    @SubscribeEvent
    public static void onAttachCapabilitiesPlayer(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player) {
            if (!event.getObject().getCapability(WingsFlapsProvider.PLAYER_FLAPS).isPresent()) {
                event.addCapability(new ResourceLocation(Flashpoint.MOD_ID, "properties"), new WingsFlapsProvider());
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerCloned(PlayerEvent.Clone event) {
        if (event.isWasDeath()) {
            event.getOriginal().getCapability(WingsFlapsProvider.PLAYER_FLAPS).ifPresent(oldStore -> {
                event.getOriginal().getCapability(WingsFlapsProvider.PLAYER_FLAPS).ifPresent(newStore -> {
                    newStore.copyFrom(oldStore);
                });
            });
        }
    }

    @SubscribeEvent
    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
        event.register(WingsFlaps.class);
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if ((event.side == LogicalSide.SERVER)) {
            event.player.getCapability(WingsFlapsProvider.PLAYER_FLAPS).ifPresent(flaps -> {

                if ((event.player.hasEffect(WINGS.get()))) {
                Vec3 speed = event.player.getDeltaMovement();
                    if (event.player.onGround()) { // If we're on the ground...
                        flaps.resetFlaps(); // ... refresh the player's jumps
                        flaps.jumpBounce(); // ... make the player unable to double jump
                    } else { // But if we're in the air...
                        if (!Minecraft.getInstance().options.keyJump.isDown()) { //... and if we're not holding the space bar...
                            flaps.jumpDebounce(); // ... make the player able to jump again!
                        }
                    }
 // TODO cap max speed so that you can't get ridiculously fast by chaining double jumps, slime blocks, etc
                    event.player.fallDistance = 0;
                    if (!event.player.isSpectator() && !event.player.isCreative() && !event.player.onGround() && isJumping() && flaps.getFlaps() < flaps.getMaxFlaps() && flaps.isDebounced()) {
                        event.player.setDeltaMovement(speed.x*1.3, min((max(speed.y, 0)+0.5), 3), speed.z*1.3); // The 1.3x speed modifier on x and z speeds is to avoid speed loss that happens for some reason after midair jumps
                        event.player.hurtMarked = true;
                        flaps.addFlaps();
                        flaps.jumpBounce();
                    }
                }
            });
        }
    }

    private static boolean isJumping() {
        return Minecraft.getInstance().options.keyJump.isDown(); // return true if the jump button is being pressed
    }
}