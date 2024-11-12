package net.mo1ecule.flashpoint.config;

import com.simibubi.create.infrastructure.config.AllConfigs;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import org.apache.commons.lang3.tuple.Pair;

import java.util.EnumMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Supplier;

// This is all copied from Destroy's codebase
@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class FlashpointAllConfigs extends AllConfigs {
    private static final Map<ModConfig.Type, FlashpointConfigBase> CONFIGS = new EnumMap<>(ModConfig.Type.class);

//    public static DestroyClientConfigs CLIENT;
//    public static DestroyCommonConfigs COMMON;
    public static FlashpointWorldConfigs SERVER;

    //This is all copied directly from the Create source code
    private static <T extends FlashpointConfigBase> T register(Supplier<T> factory, ModConfig.Type side) {
		Pair<T, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(builder -> {
			T config = factory.get();
			config.registerAll(builder);
			return config;
	    });

        T config = specPair.getLeft();
		config.specification = specPair.getRight();
		CONFIGS.put(side, config);
		return config;
    };

    public static void register(ModLoadingContext context) {
//		CLIENT = register(DestroyClientConfigs::new, ModConfig.Type.CLIENT);
//		COMMON = register(DestroyCommonConfigs::new, ModConfig.Type.COMMON);
		SERVER = register(FlashpointWorldConfigs::new, ModConfig.Type.SERVER);

		for (Entry<ModConfig.Type, FlashpointConfigBase> pair : CONFIGS.entrySet()) {
			context.registerConfig(pair.getKey(), pair.getValue().specification);
        };
	};

    @SubscribeEvent
	public static void onLoad(ModConfigEvent.Loading event) {
		for (FlashpointConfigBase config : CONFIGS.values()) {
			if (config.specification == event.getConfig().getSpec()) {
				config.onLoad();
            };
        };
	};

	@SubscribeEvent
	public static void onReload(ModConfigEvent.Reloading event) {
		for (FlashpointConfigBase config : CONFIGS.values()) {
			if (config.specification == event.getConfig().getSpec()) {
				config.onReload();
            };
        };
	};
};  
