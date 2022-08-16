package wtf.whess.meowware.client.main.functions.impl.visual;

import wtf.whess.meowware.client.main.functions.Category;
import wtf.whess.meowware.client.main.functions.Function;
import wtf.whess.meowware.client.main.settings.impl.NumberSetting;

public final class FogColor extends Function {
    public static final NumberSetting DISTANCE = new NumberSetting("Distance", 12, 2, 16);

    public FogColor() {
        super("FogColor", Category.visual);
        addSetting(DISTANCE);
    }

    @Override
    public void onEnable() {
        mc.gameSettings.ofFogType = 2;
    }
}
