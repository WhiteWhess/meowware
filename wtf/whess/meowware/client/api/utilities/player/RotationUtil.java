package wtf.whess.meowware.client.api.utilities.player;

import wtf.whess.meowware.client.api.utilities.Utility;
import wtf.whess.meowware.client.api.utilities.misc.MathUtil;

public final class RotationUtil extends Utility {
    public static float updateRotation(float currentRotation, float nextRotation, float rotationSpeed) {
        float f = MathUtil.wrapDegrees(nextRotation - currentRotation);
        if (f > rotationSpeed) {
            f = rotationSpeed;
        } if (f < -rotationSpeed) {
            f = -rotationSpeed;
        } return currentRotation + f;
    }
}
