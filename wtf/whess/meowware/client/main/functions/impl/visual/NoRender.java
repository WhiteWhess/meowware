package wtf.whess.meowware.client.main.functions.impl.visual;

import wtf.whess.meowware.client.main.functions.Category;
import wtf.whess.meowware.client.main.functions.Function;
import wtf.whess.meowware.client.main.settings.impl.BooleanSetting;

public final class NoRender extends Function {
    public static final BooleanSetting NO_RENDER_FIRE = new BooleanSetting("Fire", true);
    public static final BooleanSetting NO_RENDER_HURT_CAM = new BooleanSetting("HurtCam", true);

    public NoRender() {
        super("NoRender", Category.visual);
        addSetting(NO_RENDER_FIRE, NO_RENDER_HURT_CAM);
    }
}
