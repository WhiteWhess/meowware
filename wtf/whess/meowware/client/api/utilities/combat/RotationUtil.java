package wtf.whess.meowware.client.api.utilities.combat;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import wtf.whess.meowware.client.api.utilities.Utility;
import wtf.whess.meowware.client.api.utilities.misc.MathUtil;

public final class RotationUtil extends Utility {
    public static float [ ] getRotations(Entity entity) {
        return getRotations(entity.posX, entity.posY, entity.posZ);
    }

    public static float [ ] getRotations(Vec3d vec3d) {
        return getRotations(vec3d.xCoord, vec3d.yCoord, vec3d.zCoord);
    }

    public static float [ ] getRotations(PositionUtil.Position position) {
        return getRotations(position.getX(), position.getY(), position.getZ());
    }

    public static float [ ] getRotations(BlockPos blockPos) {
        return getRotations(blockPos.getX(), blockPos.getY(), blockPos.getZ());
    }

    public static float [ ] getJitter(float s, boolean sinus) {
        float x = (sinus ? (float) (Math.sin((double) System.currentTimeMillis()) * s) : MathUtil.random(-s, s)) * 0.930F,
              y = (sinus ? (float) (Math.sin((double) System.currentTimeMillis()) * s) : MathUtil.random(-s, s)) * 0.465F;
        return new float [ ] { x, y };
    }

    public static float nextRotation(float currentRotation, float nextRotation, float rotationSpeed) {
        float f = MathHelper.wrapDegrees(nextRotation - currentRotation);
        if (f > rotationSpeed) {
            f = rotationSpeed;
        } if (f < -rotationSpeed) {
            f = -rotationSpeed;
        } return currentRotation + f;
    }

    private static float [ ] getRotations(double x, double y, double z) {
        double deltaX = x - mc.player.posX;
        double deltaY = y - (mc.player.posY - mc.player.getEyeHeight()) - 1.77;
        double deltaZ = z - mc.player.posZ;

        double delta = MathHelper.sqrt(deltaX * deltaX + deltaZ * deltaZ);

        float yaw = (float) (Math.atan2(deltaZ, deltaX) * 180F / Math.PI) - 90F;
        float pitch = (float) -(Math.atan2(deltaY, delta) * 180 / Math.PI);

        yaw = mc.player.prevRotationYaw + getFixedRotation(MathUtil.wrapDegrees(yaw - mc.player.rotationYaw));
        pitch = mc.player.prevRotationPitch + getFixedRotation(MathUtil.wrapDegrees(pitch - mc.player.rotationPitch));
        pitch = MathUtil.clamp(pitch, -180, 180);

        return new float [ ] { yaw, pitch };
    }

    public static float getFixedRotation(float rot) {
        return getDeltaMouse(rot) * getGCDValue();
    }

    private static float getGCDValue() {
        return getGCD() * 0.15F;
    }

    private static float getGCD() {
        float f1 = (float) ((double) mc.gameSettings.mouseSensitivity * 0.6 + 0.2);
        return f1 * f1 * f1 * 8.0f;
    }

    private static float getDeltaMouse(float delta) {
        return Math.round(delta / getGCDValue());
    }
}
