package net.mo1ecule.flashpoint.chemistry.legacy.index;

import com.petrolpark.destroy.chemistry.legacy.LegacyReaction;
import com.petrolpark.destroy.chemistry.legacy.LegacySpecies;
import com.petrolpark.destroy.chemistry.legacy.index.DestroyMolecules;

public class Dissolutions extends LegacyReaction.ReactionBuilder {

    public static final float GAS_CONSTANT = 8.3145f;

    /**
     * The namespace of the mod by which this Reaction was declared, or {@code novel} if this was generated
     * by a {@link com.petrolpark.destroy.chemistry.legacy.genericreaction.GenericReaction Reaction generator}.
     */
    private String namespace;

    public Dissolutions(String nameSpace) {
        super(nameSpace);
        namespace = nameSpace;
    }

    /**
     * Registers an acid's dissolution in ethanol. This automatically registers two {@link LegacyReaction Reactions} (one for the association,
     * one for the dissociation). The pKa is assumed to be temperature-independent - if this is not wanted, manually register
     * the two Reactions.
     * @param acid
     * @param conjugateBase This should have a charge one less than the acid and should ideally conserve Atoms
     * @param pKa
     * @param solvent
     * @return The dissociation Reaction
     */
    public LegacyReaction flashpointAcid(LegacySpecies acid, LegacySpecies conjugateBase, float pKa, LegacySpecies solvent) {

        //if (conjugateBase.getCharge() + 1 != acid.getCharge()) throw e("Acids must not violate the conservation of charge.");

        // Dissociation
        LegacyReaction dissociationReaction = this
            .id(acid.getFullID().split(":")[1] + ".dissociation")
            .addReactant(acid)
            .addCatalyst(solvent, 1)
            .addProduct(DestroyMolecules.PROTON)
            .addProduct(conjugateBase)
            .activationEnergy(GAS_CONSTANT * 0.298f) // Makes the pKa accurate at room temperature
            .preexponentialFactor((float)Math.pow(10, -pKa))
            //.dontIncludeInJei() TODO UNCOMMENT
            .build();

        // Association
        new LegacyReaction.ReactionBuilder(namespace)
            .id(acid.getFullID().split(":")[1] + ".association")
            .addReactant(conjugateBase)
            .addReactant(DestroyMolecules.PROTON)
            .addProduct(acid)
            .activationEnergy(GAS_CONSTANT * 0.298f)
            .preexponentialFactor(1f)
//          .dontIncludeInJei() TODO UNCOMMENT
            .build();

        return dissociationReaction;
    };
}
