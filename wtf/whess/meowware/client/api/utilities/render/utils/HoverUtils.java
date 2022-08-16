package wtf.whess.meowware.client.api.utilities.render.utils;

import wtf.whess.meowware.client.api.utilities.Utility;

public final class HoverUtils extends Utility {
    public static boolean hovered(float x, float y, float width, float height, float mouseX, float mouseY) {
        return x < mouseX && y < mouseY && width > mouseX && height > mouseY;
    }
}
