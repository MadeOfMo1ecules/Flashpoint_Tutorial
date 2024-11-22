package net.mo1ecule.flashpoint.item;

import com.simibubi.create.foundation.item.CombustibleItem;
import com.tterrag.registrate.util.entry.ItemEntry;
import net.minecraft.world.item.Item;

import static net.mo1ecule.flashpoint.Flashpoint.REGISTRATE;

public class FlashpointItems {

    public static final ItemEntry<Item>
        COCOA_EXTRACT = REGISTRATE.item("cocoa_extract", Item::new) // todo add to creative tab
            .register(),
        GAS_CYLINDER = REGISTRATE.item("gas_cylinder", Item::new)
            .register(),
        POTASH = REGISTRATE.item("potash", Item::new)
            .register();

    public static final ItemEntry<CombustibleItem>
            WOODCHIPS = REGISTRATE.item("woodchips", CombustibleItem::new)
            .onRegister(i -> i.setBurnTime(150))
            .register();

//    public static final DeferredRegister<Item> ITEMS =
//        DeferredRegister.create(ForgeRegistries.ITEMS, Flashpoint.MOD_ID);
//
//    public static final RegistryObject<Item> GAS_CYLINDER = ITEMS.register("gas_cylinder",
//            () -> new Item(new Item.Properties()));
//    public static final RegistryObject<Item> WOODCHIPS = ITEMS.register("woodchips",
//            () -> new FuelItem(new Item.Properties(), 150)); // Same burn time as a slab, todo figure out how to make compostable
//    public static final RegistryObject<Item> POTASH = ITEMS.register("potash",
//            () -> new Item(new Item.Properties()));

    public static void register() {};
}