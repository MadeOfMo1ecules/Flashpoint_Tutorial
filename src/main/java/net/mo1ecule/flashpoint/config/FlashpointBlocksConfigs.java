package net.mo1ecule.flashpoint.config;

public class FlashpointBlocksConfigs extends FlashpointConfigBase{

    //HARP WUZ HERE
    public final ConfigGroup blazer = group(0, "blazer", "Bunsen Blazer");
    public final ConfigInt maximumBlazingTicks = i(600000, 1, "maximumBlazingTicks", "[in ticks]", "Roughly the length of blazing time a Bunsen Blazer must have before it stops accepting more combustibles, assuming a 1 mB/s burn rate.");
    public final ConfigFloat blazerEfficiency = f(1f, 0f, "blazerEfficiency", "A multiplier for the standard heating-second-kelvin-per-joule of Bunsen Blazers", "[0 will disable Bunsen Blazers]");
    public final ConfigBool blazerEnhancedByPurity = b(true, "blazerEnhancedByPurity", "Whether to scale the efficiency of blazers with the purity of supplied combustibles");

    @Override
    public String getName() {
        return "blocks";
    };
}
