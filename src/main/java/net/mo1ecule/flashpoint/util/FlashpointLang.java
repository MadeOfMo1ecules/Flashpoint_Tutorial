package net.mo1ecule.flashpoint.util;

import com.petrolpark.destroy.util.DestroyLang;
import com.simibubi.create.foundation.utility.LangBuilder;

public class FlashpointLang extends DestroyLang {
    public static LangBuilder builder() {
        return new LangBuilder("flashpoint");
    }
    public static LangBuilder translate(String langKey, Object... args) {
        return builder().translate(langKey, args);
    }
}