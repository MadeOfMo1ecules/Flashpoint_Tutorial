package net.mo1ecule.flashpoint.config;

public class FlashpointWorldConfigs extends FlashpointConfigBase {

    public final FlashpointBlocksConfigs blocks = nested(0, FlashpointBlocksConfigs::new, "Flashpoint's blocks");
//    public final FlashpointCompatConfigs compat = nested(0, FlashpointCompatConfigs::new, "Compatibility with other mods");
    
    @Override
    public String getName() {
        return "world";
    };
};
