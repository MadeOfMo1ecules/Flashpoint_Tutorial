package net.mo1ecule.flashpoint.chemistry.legacy.index;

import com.petrolpark.destroy.chemistry.legacy.LegacyBond;
import com.petrolpark.destroy.chemistry.legacy.LegacyElement;
import com.petrolpark.destroy.chemistry.legacy.LegacyMolecularStructure;

import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.mo1ecule.flashpoint.Flashpoint;


public class FlashpointTopologies{

    public static final LegacyMolecularStructure.Topology

            PURINE = create(LegacyElement.NITROGEN) // 0
                .atom(LegacyElement.CARBON, new Vec3(0d, 1d, 0d)) // 1
                    .withSideBranch(new Vec3(-cos(30), sin(30), 0d), new Vec3(-cos(30), sin(30), 0d))
                    .withBondTo(0, LegacyBond.BondType.AROMATIC)
                    .attach()
                .atom(LegacyElement.NITROGEN, new Vec3(cos(30), 1d + sin(30), 0d)) // 2
                    .withBondTo(1, LegacyBond.BondType.AROMATIC)
                    .attach()
                .atom(LegacyElement.CARBON, new Vec3(2 * cos(30), 1d, 0d)) // 3
                    .withBondTo(2, LegacyBond.BondType.AROMATIC)
                    .attach()
                .atom(LegacyElement.NITROGEN, new Vec3(2 * cos(30) + cos(18), 1d + sin(18), 0d)) // 4
                    .withSideBranch(new Vec3(cos(72), sin(72), 0d), new Vec3(cos(72), sin(72), 0d), LegacyBond.BondType.SINGLE)
                    .withBondTo(3, LegacyBond.BondType.SINGLE)
                    .attach()
                .atom(LegacyElement.CARBON, new Vec3(2 * cos(30) + cos(18) + sin(36), 0.5d, 0d)) // 5
                    .withSideBranch(new Vec3(1d, 0d, 0d), new Vec3(cos(72), sin(72), 0d), LegacyBond.BondType.SINGLE)
                    .withBondTo(4, LegacyBond.BondType.SINGLE)
                    .attach()
                .atom(LegacyElement.NITROGEN, new Vec3(2 * cos(30) + cos(18), -sin(18), 0d)) // 6
                    .withBondTo(5, LegacyBond.BondType.DOUBLE)
                    .attach()
                .atom(LegacyElement.CARBON, new Vec3(2 * cos(30), 0d, 0d)) // 7
                    .withBondTo(3, LegacyBond.BondType.AROMATIC)
                    .withBondTo(6, LegacyBond.BondType.SINGLE)
                    .attach()
                .atom(LegacyElement.CARBON, new Vec3(cos(30), -sin(30), 0d)) // 8
                    .withSideBranch(new Vec3(0d, -1d, 0d), new Vec3(0d, -1d, 0d))
                    .withBondTo(0, LegacyBond.BondType.AROMATIC)
                    .withBondTo(7, LegacyBond.BondType.AROMATIC)
                    .attach()
    //              .reflections(new int[][]{
    //                    new int[]{1, 0, 5, 4, 3, 2}})
            .build("purine"),

            THEOBROMINE_BASE = create(LegacyElement.NITROGEN) // 0
                    .sideChain(new Vec3(-cos(30), -sin(30), 0d), new Vec3(-cos(60), -sin(60), 0d))
                .atom(LegacyElement.CARBON, new Vec3(0d, 1d, 0d)) // 1
                    .withSideBranch(new Vec3(-cos(30), sin(30), 0d), new Vec3(-cos(30), sin(30), 0d), LegacyBond.BondType.DOUBLE)
                    .withBondTo(0, LegacyBond.BondType.SINGLE)
                    .attach()
                .atom(LegacyElement.NITROGEN, new Vec3(cos(30), 1d + sin(30), 0d)) // 2
                    .withSideBranch(new Vec3(0d, 1d, 0d), new Vec3(0d, 1d, 0d), LegacyBond.BondType.SINGLE)
                    .withBondTo(1, LegacyBond.BondType.SINGLE)
                    .attach()
                .atom(LegacyElement.CARBON, new Vec3(2 * cos(30), 1d, 0d)) // 3
                    .withBondTo(2, LegacyBond.BondType.SINGLE)
                    .attach()
                .atom(LegacyElement.NITROGEN, new Vec3(2 * cos(30) + cos(18), 1d + sin(18), 0d)) // 4
                    .withBondTo(3, LegacyBond.BondType.SINGLE)
                    .attach()
                .atom(LegacyElement.CARBON, new Vec3(2 * cos(30) + cos(18) + sin(36), 0.5d, 0d)) // 5
                    .withSideBranch(new Vec3(1d, 0d, 0d), new Vec3(cos(72), sin(72), 0d), LegacyBond.BondType.SINGLE)
                    .withBondTo(4, LegacyBond.BondType.DOUBLE)
                    .attach()
                .atom(LegacyElement.NITROGEN, new Vec3(2 * cos(30) + cos(18), -sin(18), 0d)) // 6
                    .withSideBranch(new Vec3(cos(72), -sin(72), 0d), new Vec3(cos(72), -sin(72), 0d), LegacyBond.BondType.SINGLE)
                    .withBondTo(5, LegacyBond.BondType.SINGLE)
                    .attach()
                .atom(LegacyElement.CARBON, new Vec3(2 * cos(30), 0d, 0d)) // 7
                    .withBondTo(3, LegacyBond.BondType.DOUBLE)
                    .withBondTo(6, LegacyBond.BondType.SINGLE)
                    .attach()
                .atom(LegacyElement.CARBON, new Vec3(cos(30), -sin(30), 0d)) // 8
                    .withSideBranch(new Vec3(0d, -1d, 0d), new Vec3(0d, -1d, 0d), LegacyBond.BondType.DOUBLE)
                    .withBondTo(0, LegacyBond.BondType.SINGLE)
                    .withBondTo(7, LegacyBond.BondType.SINGLE)
                    .attach()
    //            .reflections(new int[][]{
    //                    new int[]{1, 0, 5, 4, 3, 2}})
            .build("theobromine_base");

    private static double cos(float value) {
        return Mth.cos(value * Mth.PI / 180f);
    };

    private static double sin(float value) {
        return Mth.sin(value * Mth.PI / 180f);
    };

    private static LegacyMolecularStructure.Topology.Builder create(LegacyElement startingElement) {
        return new LegacyMolecularStructure.Topology.Builder(Flashpoint.MOD_ID).startWith(startingElement);
    };

    public static void register() {};
}
