package wtf.whess.meowware.client.api;

import wtf.whess.meowware.client.api.utilities.render.utils.ColorUtil;

import java.awt.*;

public abstract class ClientColors {
    public static Color mainColor() {
        return ColorUtil.astolfoColors1(0,0);
    }

    public static Color mainColor(int alpha) {
        return new Color(ColorUtil.astolfoColors1(0,0).getRed(), ColorUtil.astolfoColors1(0,0).getGreen(), ColorUtil.astolfoColors1(0,0).getBlue(), alpha);
    }

}
