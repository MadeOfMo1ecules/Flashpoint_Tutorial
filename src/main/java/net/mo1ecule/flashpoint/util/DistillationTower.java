package net.mo1ecule.flashpoint.util;

import com.petrolpark.destroy.capability.Pollution;
import com.simibubi.create.content.processing.basin.BasinBlockEntity;
import com.simibubi.create.content.processing.burner.BlazeBurnerBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.mo1ecule.flashpoint.block.BlazerBlock;

public class DistillationTower extends com.petrolpark.destroy.util.DistillationTower {
    public DistillationTower(Level level, BlockPos controllerPos) {
        super(level, controllerPos);
    }

    /**
     * Get the temperature (in kelvins) to which this Heat Level will heat the Distillation Tower.
     */
//    @Override
//    public static float getTemperatureForDistillationTower(Level level, BlockPos pos) {
//        float roomTemperature = Pollution.getLocalTemperature(level, pos);
//        float temperature = roomTemperature;
//        BlazeBurnerBlock.HeatLevel heatLevel = BasinBlockEntity.getHeatLevelOf(level.getBlockState(pos.below()));
//        if ("FROSTING".equals(heatLevel.name())) temperature = 273f;
//        if ("BLAZING".equals(heatLevel.name())) {
//            int burnRate = BlazerBlock.getBurnRate(level.getBlockState(pos.below()));
//            temperature = roomTemperature + (1000-roomTemperature)*burnRate/100; // Sets temperature range to between room temperature and 1000K
//            //HARP WUZ HERE
//            /* Should check if the block below is a Bunsen Blazer and call+return its specific temperature if so */
//        }
//        switch (heatLevel) {
//            case FADING:
//                temperature = 350f;
//                break;
//            case KINDLED:
//                temperature = 400f;
//                break;
//            case SEETHING:
//                temperature = 650f;
//                break;
//            default:
//        };
//        return temperature;
//    };
}
