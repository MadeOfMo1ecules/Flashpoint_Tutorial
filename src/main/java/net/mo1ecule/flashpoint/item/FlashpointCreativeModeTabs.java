package net.mo1ecule.flashpoint.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import net.mo1ecule.flashpoint.Flashpoint;
import net.mo1ecule.flashpoint.block.FlashpointBlocks;

public class FlashpointCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Flashpoint.MOD_ID);

    public static final RegistryObject<CreativeModeTab> FLASHPOINT_TAB = CREATIVE_MODE_TABS.register("flashpoint_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(FlashpointItems.GAS_CYLINDER.get()))
                    .title(Component.translatable("creativetab.flashpoint_tab"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(FlashpointItems.GAS_CYLINDER.get());
                        output.accept(FlashpointItems.WOODCHIPS.get());
                        output.accept(FlashpointItems.POTASH.get());
                        output.accept(FlashpointBlocks.BLAZER.get());
                    })
                    .build());

    public static void register(IEventBus eventbus){
        CREATIVE_MODE_TABS.register(eventbus);
    }
}
