package net.mo1ecule.flashpoint.config;

import com.simibubi.create.foundation.config.ConfigBase;

import java.util.EnumMap;
import java.util.function.Function;

// Copied from Destroy's code
public class FlashpointConfigBase extends ConfigBase {

    @Override
    public String getName() {
        return "testname";
    };

    protected <E extends Enum<E>> EnumMap<E, ConfigFloat> enumFloatMap(Class<E> enumClass, E[] enumValues, Function<E, String> nameGetter, Function<E, String[]> commentGetter, float min, float max, float defaultDefault, float ...defaults) {
        EnumMap<E, ConfigFloat> map = new EnumMap<>(enumClass);
        int i = 0;
        for (E value : enumValues) {
            float d = defaultDefault;
            if (i < defaults.length) d = defaults[i];
            map.put(value, f(d, min, max, nameGetter.apply(value), commentGetter.apply(value)));
            i++;
        };
        return map;
    };
}
