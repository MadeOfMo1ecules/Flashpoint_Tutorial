package net.mo1ecule.flashpoint.item;

import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import net.mo1ecule.flashpoint.Flashpoint;
//import net.mo1ecule.flashpoint.item.custom.FuelItem;

public class FlashpointItems {
    public static final DeferredRegister<Item> ITEMS =
        DeferredRegister.create(ForgeRegistries.ITEMS, Flashpoint.MOD_ID);

    public static final RegistryObject<Item> GAS_CYLINDER = ITEMS.register("gas_cylinder",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> WOODCHIPS = ITEMS.register("woodchips",
            () -> new FuelItem(new Item.Properties(), 150)); // Same burn time as a slab, todo figure out how to make compostable


    public static void register(IEventBus eventbus){
        ITEMS.register(eventbus);
    }
}
