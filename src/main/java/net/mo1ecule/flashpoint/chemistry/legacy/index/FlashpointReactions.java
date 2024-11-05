package net.mo1ecule.flashpoint.chemistry.legacy.index;

import com.petrolpark.destroy.Destroy;
import com.petrolpark.destroy.chemistry.legacy.LegacyReaction;
import com.petrolpark.destroy.chemistry.legacy.index.DestroyMolecules;
import com.petrolpark.destroy.chemistry.legacy.index.DestroyReactions;

import net.mo1ecule.flashpoint.Flashpoint;

public class FlashpointReactions extends DestroyReactions {
    public static final LegacyReaction

    NONSENSE = builder()
        .id("nonsense")
        .addReactant(DestroyMolecules.SULFUR_DIOXIDE)
        .addReactant(DestroyMolecules.PHOSGENE)
        .addProduct(DestroyMolecules.ACETYLENE)
        .activationEnergy(20f)
        .build();

    private static LegacyReaction.ReactionBuilder builder() {
        return new LegacyReaction.ReactionBuilder(Flashpoint.MOD_ID);
    };

    public static void register() {};
}
