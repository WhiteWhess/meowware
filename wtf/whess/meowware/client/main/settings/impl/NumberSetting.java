package wtf.whess.meowware.client.main.settings.impl;

import lombok.Getter;
import wtf.whess.meowware.client.main.settings.Setting;
import wtf.whess.meowware.client.api.utilities.misc.MathUtil;

public final class NumberSetting extends Setting {

    @Getter
    private double value;

    @Getter
    private final double min, max, increment;

    public NumberSetting(String name, double value, double min, double max, double increment) {
        super(name);
        this.min = min;
        this.max = max;
        this.increment = increment;

        setValue(value);
    }

    public NumberSetting(String name, double value, double min, double max) {
        this(name, value, min, max, 0.1);
    }

    public void setValue(double value) {
        double increment = 1f / this.increment;
        this.value = MathUtil.clamp(Math.round(value * increment) / increment, min, max);
    }

}
