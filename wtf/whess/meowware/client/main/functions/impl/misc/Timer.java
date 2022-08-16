package wtf.whess.meowware.client.main.functions.impl.misc;

import wtf.whess.meowware.client.main.functions.Category;
import wtf.whess.meowware.client.main.functions.Function;
import wtf.whess.meowware.client.main.settings.impl.NumberSetting;

public final class Timer extends Function {
    private final NumberSetting boost = new NumberSetting("Time Boost", 20, 1, 100);

    public Timer() {
        super("Timer", Category.misc);
        addSetting(boost);
    }

    @Override
    public void onEnable() {
        mc.getTimer().setTimerSpeed((float) (boost.getValue() / 10));
    }

    @Override
    public void onDisable() {
        mc.getTimer().setTimerSpeed(1);
    }
}
