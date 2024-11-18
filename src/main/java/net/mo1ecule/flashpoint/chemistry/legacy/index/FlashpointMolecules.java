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

//        SODIUM_METHOXIDE = builder() // only used as a test molecule
//            .id("sodium_methoxide")
//            .structure(LegacyMolecularStructure.deserialize("destroy:linear:CONa"))
//            .boilingPoint(623f)
//            .density(945f)
//            .molarHeatCapacity(69.45f)
//            .build(),

        PURINE = builder()
            .id("purine")
            .structure(LegacyMolecularStructure.deserialize("flashpoint:purine:,,,"))
            //.color(0xFFD00000)
            //.boilingPoint(444.6f)
            //.density(2070f)
            //.molarHeatCapacity(21.64f)
            //.tag(DestroyMolecules.Tags.SMELLY)
            //.tag(DestroyMolecules.Tags.SMOG)
            .build(),

        THEOBROMINE = builder()
            .id("theobromine")
            .structure(LegacyMolecularStructure.deserialize("flashpoint:theobromine_base:,O,C,,C,O"))
            .boilingPoint(560f)
            .density(1524f)
            .molarHeatCapacity(226.49f) // Couldn't find, using caffeine's instead
            .build(),

        CAFFEINE = builder()
            .id("caffeine")
            .structure(LegacyMolecularStructure.deserialize("flashpoint:theobromine_base:C,O,C,,C,O"))
            .boilingPoint(451f)
            .density(1219f)
            .molarHeatCapacity(226.49f)
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
            COMBUSTIBLE = new LegacySpeciesTag("flashpoint", "combustible") // todo add little fire image
            .withColor(0xF65C3B);

    }

    public static void register() {};
}
