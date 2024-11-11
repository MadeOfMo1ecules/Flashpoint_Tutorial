package net.mo1ecule.flashpoint.chemistry.legacy.index;

import com.petrolpark.destroy.chemistry.legacy.LegacyReaction;
import com.petrolpark.destroy.chemistry.legacy.index.DestroyMolecules;
import com.petrolpark.destroy.chemistry.legacy.index.DestroyReactions;

import com.petrolpark.destroy.chemistry.legacy.reactionresult.PrecipitateReactionResult;
import net.mo1ecule.flashpoint.Flashpoint;
import net.mo1ecule.flashpoint.item.FlashpointItems;

public class FlashpointReactions extends DestroyReactions {
    public static final LegacyReaction

    WOOD_COMBUSTION = builder() // Should happen at a lower temperature than wood pyrolysis, in the presence of oxygen
        .id("wood_combustion")
        .addSimpleItemReactant(FlashpointItems.WOODCHIPS::get, 1f)
        .addReactant(DestroyMolecules.OXYGEN, 8, 1) //
        .addProduct(DestroyMolecules.CARBON_DIOXIDE, 4)
        .addProduct(DestroyMolecules.WATER, 4)
        .addProduct(DestroyMolecules.CARBON_MONOXIDE, 1)
        .withResult(1f, PrecipitateReactionResult.of(FlashpointItems.POTASH::asStack))
        .enthalpyChange(-100f)
        .preexponentialFactor(70f)
        .activationEnergy(25f)
        .build();

    //WOOD_PYROLYSIS = builder() // Should happen at a higher temperature than wood combustion and only in the absence of oxygen
    //    .id("wood_pyrolysis")
    //    .addSimpleItemReactant(FlashpointItems.WOODCHIPS::get, 1f)
    //    .addProduct(DestroyMolecules.CARBON_DIOXIDE, 4)
    //    .addProduct(DestroyMolecules.CARBON_MONOXIDE, 1)
    //    .withResult(1f, PrecipitateReactionResult.of(() -> new ItemStack(Items.CHARCOAL)))
    //    .preexponentialFactor(40f)
    //    .activationEnergy(25f)
    //    .build();

    private static LegacyReaction.ReactionBuilder builder() {
        return new LegacyReaction.ReactionBuilder(Flashpoint.MOD_ID);
    };
    public static void register() {};
}
