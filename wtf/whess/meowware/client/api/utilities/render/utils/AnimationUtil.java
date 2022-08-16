package wtf.whess.meowware.client.api.utilities.render.utils;

import wtf.whess.meowware.client.api.utilities.Utility;

import java.awt.*;

public final class AnimationUtil extends Utility {
    public static float smooth(float from, float to, float speed) {
        float fromValue = from;
        if (to < from) {
            fromValue += speed;
        } if (from > to) {
            fromValue -= speed;
        } return fromValue;
    }

    public static Color animateColor(Color from, Color to, float speed) {
        int red = from.getRed();
        int green = from.getGreen();
        int blue = from.getBlue();
        int alpha = from.getAlpha();
        if (red < to.getRed()) {
            red += speed;
        } if (green < to.getGreen()) {
            green += speed;
        } if (blue < to.getBlue()) {
            blue += speed;
        } if (alpha < to.getAlpha()) {
            alpha += speed;
        } return new Color(red,green,blue,alpha);
    }
}
