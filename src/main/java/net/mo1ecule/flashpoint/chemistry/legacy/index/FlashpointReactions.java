package net.mo1ecule.flashpoint.chemistry.legacy.index;

import com.petrolpark.destroy.chemistry.legacy.LegacyReaction;
import com.petrolpark.destroy.chemistry.legacy.index.DestroyMolecules;
import com.petrolpark.destroy.chemistry.legacy.index.DestroyReactions;

import net.mo1ecule.flashpoint.Flashpoint;

public class FlashpointReactions extends DestroyReactions {
    public static final LegacyReaction

    SODIUM_METHOXIDE_SYNTHESIS = builder() // todo remove
        .id("sodium_methoxide_synthesis")
        .addReactant(DestroyMolecules.SODIUM_METAL, 2)
        .addReactant(DestroyMolecules.METHANOL, 2)
        .addProduct(FlashpointMolecules.SODIUM_METHOXIDE, 2)
        .addProduct(DestroyMolecules.HYDROGEN, 1)
        .enthalpyChange(-375f)
        .build();

    private static LegacyReaction.ReactionBuilder builder() {
        return new LegacyReaction.ReactionBuilder(Flashpoint.MOD_ID);
    };

    public static void register() {};
}
