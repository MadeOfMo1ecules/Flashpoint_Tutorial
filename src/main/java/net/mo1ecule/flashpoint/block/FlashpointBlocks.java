package net.mo1ecule.flashpoint.block;

import com.simibubi.create.foundation.data.SharedProperties;
import com.simibubi.create.foundation.data.TagGen;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;

import static com.simibubi.create.foundation.data.ModelGen.customItemModel;

import static net.mo1ecule.flashpoint.Flashpoint.REGISTRATE;

public class FlashpointBlocks /*extends DestroyBlocks*/ { // extending DestroyBlocks almost definitely isn't needed here

    public static final BlockEntry<BlazerBlock>
            BLAZER = REGISTRATE.block("blazer", BlazerBlock::new)
            .initialProperties(SharedProperties::stone)
            .properties(p -> p
                    .mapColor(MapColor.COLOR_GRAY)
                    .noOcclusion()
                    .sound(SoundType.METAL)
            ).transform(TagGen.pickaxeOnly())
            .item()
            .transform(customItemModel())
            .register();

    public static void register() {};
}