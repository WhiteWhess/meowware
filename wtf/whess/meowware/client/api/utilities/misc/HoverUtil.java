package wtf.whess.meowware.client.api.utilities.misc;

import wtf.whess.meowware.client.api.utilities.Utility;

public final class HoverUtil extends Utility {
    public static boolean hovered(double x, double y, double width, double height, int mouseX, int mouseY) {
        return x < mouseX && y < mouseY && width > mouseX && height > mouseY;
    }
}
