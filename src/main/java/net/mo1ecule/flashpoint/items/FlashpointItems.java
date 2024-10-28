package net.mo1ecule.flashpoint.items;

import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.mo1ecule.flashpoint.Flashpoint;

public class FlashpointItems {
    public static final DeferredRegister<Item> ITEMS =
        DeferredRegister.create(ForgeRegistries.ITEMS, Flashpoint.MOD_ID);

    public static final RegistryObject<Item> GAS_CYLINDER = ITEMS.register("gas_cylinder",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> EXAMPLE2 = ITEMS.register("example2",
            () -> new Item(new Item.Properties()));


    public static void register(IEventBus eventbus){
        ITEMS.register(eventbus);
    }
}
