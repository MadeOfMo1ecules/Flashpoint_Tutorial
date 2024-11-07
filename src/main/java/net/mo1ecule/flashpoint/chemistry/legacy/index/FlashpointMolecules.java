package net.mo1ecule.flashpoint.chemistry.legacy.index;

import com.petrolpark.destroy.Destroy;
import com.petrolpark.destroy.chemistry.legacy.LegacyMolecularStructure;
import com.petrolpark.destroy.chemistry.legacy.LegacySpecies;
import com.petrolpark.destroy.chemistry.legacy.index.DestroyMolecules;
import com.petrolpark.destroy.chemistry.legacy.index.DestroyMolecules.Tags;
import net.mo1ecule.flashpoint.Flashpoint;

public class FlashpointMolecules{

    public static final LegacySpecies

            SODIUM_METHOXIDE = builder() // todo remove
            .id("sodium_methoxide")
            .structure(LegacyMolecularStructure.deserialize("destroy:linear:CONa"))
            .boilingPoint(623f)
            .density(945f)
            .molarHeatCapacity(69.45f)
            .tag(Tags.ACUTELY_TOXIC)
            .build();

    private static LegacySpecies.MoleculeBuilder builder() {
        return new LegacySpecies.MoleculeBuilder(Flashpoint.MOD_ID);
    };

    public static void register() {};
}
