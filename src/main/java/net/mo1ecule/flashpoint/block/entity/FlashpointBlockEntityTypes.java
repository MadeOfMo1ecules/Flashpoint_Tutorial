package net.mo1ecule.flashpoint.block.entity;

import com.tterrag.registrate.util.entry.BlockEntityEntry;
import net.mo1ecule.flashpoint.block.FlashpointBlocks;

import static net.mo1ecule.flashpoint.Flashpoint.REGISTRATE;

public class FlashpointBlockEntityTypes /*extends DestroyBlockEntityTypes*/ { // extending DestroyBlockEntityTypes almost definitely isn't needed

    public static final BlockEntityEntry<BlazerBlockEntity> BLAZER = REGISTRATE
            .blockEntity("blazer", BlazerBlockEntity::new)
            .validBlocks(FlashpointBlocks.BLAZER)
            .register();

    public static void register() {};
}
