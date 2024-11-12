package net.mo1ecule.flashpoint.chemistry.legacy.index;

import com.petrolpark.destroy.Destroy;
import com.petrolpark.destroy.chemistry.legacy.LegacyElement;
import com.petrolpark.destroy.chemistry.legacy.LegacyMolecularStructure;
import com.petrolpark.destroy.chemistry.legacy.LegacySpecies;
import com.petrolpark.destroy.chemistry.legacy.LegacySpeciesTag;
import com.petrolpark.destroy.chemistry.legacy.index.DestroyMolecules;
import static com.petrolpark.destroy.chemistry.legacy.index.DestroyMolecules.HYDROGEN;
import net.mo1ecule.flashpoint.Flashpoint;

public class FlashpointMolecules{

    public static final LegacySpecies

        SODIUM_METHOXIDE = builder() // todo remove, only used as a test molecule
            .id("sodium_methoxide")
            .structure(LegacyMolecularStructure.deserialize("destroy:linear:CONa"))
            .boilingPoint(623f)
            .density(945f)
            .molarHeatCapacity(69.45f)
            .build(),

        HYDROGEN = destroyMoleculeBuilder()
            .id("hydrogen")
            .structure(LegacyMolecularStructure.atom(LegacyElement.HYDROGEN).addAtom(LegacyElement.HYDROGEN))
            .boilingPointInKelvins(20.271f)
            .density(70.85f)
            .molarHeatCapacity(28.84f)
            .tag(FlashpointMolecules.Tags.COMBUSTIBLE)
//            .heatOfCombustion(286f)
            .build();

    private static LegacySpecies.MoleculeBuilder builder() {
        return new LegacySpecies.MoleculeBuilder(Flashpoint.MOD_ID);
    };

    private static LegacySpecies.MoleculeBuilder destroyMoleculeBuilder(){
        return new LegacySpecies.MoleculeBuilder(Destroy.MOD_ID);
    }

    public static class Tags extends DestroyMolecules.Tags{
        public static final LegacySpeciesTag

            /**
             * This Molecule will burn if ignited.
             */
            COMBUSTIBLE = new LegacySpeciesTag("flashpoint", "combustible")
            .withColor(0xF65C3B);

    }

    public static void register() {};
}
