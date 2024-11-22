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
        .build(),

    /**
    Caffeine Chemistry
     **/

//    THEOBROMINE_DISSOLUTION = builder()
//        .id("theobromine_dissolution")
//            .addReactant(DestroyMolecules.METHANOL)
////            .addSimpleItemReactant()
//            .addProduct(FlashpointMolecules.THEOBROMINE, 5)
//        .build(),

    /**
    Chlorine Chemistry
     **/

    METHANOL_CHLORINATION = builder()
        .id("methanol_chlorination")
        .addReactant(DestroyMolecules.METHANOL)
        .addReactant(DestroyMolecules.PROTON)
        .addReactant(DestroyMolecules.CHLORIDE)
        .addProduct(FlashpointMolecules.METHYL_CHLORIDE)
        .addProduct(DestroyMolecules.WATER)
        .build(),

    METHANE_CHLORINATION = builder()
        .id("methane_chlorination")
        .addReactant(DestroyMolecules.METHANE)
        .addReactant(DestroyMolecules.CHLORINE)
        .addProduct(FlashpointMolecules.METHYL_CHLORIDE)
        .addProduct(DestroyMolecules.HYDROCHLORIC_ACID)
        .build(),

    METHYL_CHLORIDE_CHLORINATION = builder()
        .id("methyl_chloride_chlorination")
        .addReactant(FlashpointMolecules.METHYL_CHLORIDE)
        .addReactant(DestroyMolecules.CHLORINE)
        .addProduct(FlashpointMolecules.DICHLOROMETHANE)
        .addProduct(DestroyMolecules.HYDROCHLORIC_ACID)
        .build(),

    DCM_CHLORINATION = builder()
        .id("dcm_chlorination")
        .addReactant(FlashpointMolecules.DICHLOROMETHANE)
        .addReactant(DestroyMolecules.CHLORINE)
        .addProduct(DestroyMolecules.CHLOROFORM)
        .addProduct(DestroyMolecules.HYDROCHLORIC_ACID)
        .build(),

    CHLOROFORM_CHLORINATION = builder()
        .id("chloroform_chlorination")
        .addReactant(DestroyMolecules.CHLOROFORM)
        .addReactant(DestroyMolecules.CHLORINE)
        .addProduct(DestroyMolecules.CARBON_TETRACHLORIDE)
        .addProduct(DestroyMolecules.HYDROCHLORIC_ACID)
        .build(),

    //WOOD_PYROLYSIS = builder() // Should happen at a higher temperature than wood combustion and only in the absence of oxygen
    //    .id("wood_pyrolysis")
    //    .addSimpleItemReactant(FlashpointItems.WOODCHIPS::get, 1f)
    //    .addProduct(DestroyMolecules.CARBON_DIOXIDE, 4)
    //    .addProduct(DestroyMolecules.CARBON_MONOXIDE, 1)
    //    .withResult(1f, PrecipitateReactionResult.of(() -> new ItemStack(Items.CHARCOAL)))
    //    .preexponentialFactor(40f)
    //    .activationEnergy(25f)
    //    .build();

    CYANIDE_NEUTRALIZATION = builder()
        .id("cyanide_neutralization")
        .addReactant(DestroyMolecules.HYDROGEN_CYANIDE)
        .addReactant(DestroyMolecules.HYDROXIDE)
        .addProduct(DestroyMolecules.WATER)
        .addProduct(DestroyMolecules.CYANIDE)
        .build();

    // Acids
    static {
        dissolutionBuilder().flashpointAcid(DestroyMolecules.HYDROGEN_CYANIDE, DestroyMolecules.CYANIDE, 1f, DestroyMolecules.ETHANOL); // kPa chosen arbitrarily
    };

    private static LegacyReaction.ReactionBuilder builder() {
        return new LegacyReaction.ReactionBuilder(Flashpoint.MOD_ID);
    };

    private static Dissolutions dissolutionBuilder(){
        return new Dissolutions(Flashpoint.MOD_ID);
    }
    public static void register() {};
}
