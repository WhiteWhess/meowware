package wtf.whess.meowware.client.api.utilities.combat;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import wtf.whess.meowware.client.api.utilities.Utility;

public final class FOVUtil extends Utility {
    public static boolean isInFOV(Entity entity, int fov) {
        return getDistanceFromMouse(entity) <= fov;
    }

    public static float getAngleDifference(Entity entity) {
        return getYawDifference(entity) + getPitchDifference(entity);
    }

    public static float[] getDifference(Entity entity) {
        return RotationUtil.getRotations(new Vec3d(entity.posX, (entity.getEntityBoundingBox().maxY + entity.getEntityBoundingBox().minY) / 2f, entity.posZ));
    }

    public static float getYawDifference(Entity entity) {
        return Math.abs(getDifference(entity)[0] - mc.player.rotationYaw);
    }

    public static float getPitchDifference(Entity entity) {
        return Math.abs(getDifference(entity)[1] - mc.player.rotationPitch);
    }

    public static Vec3d getEyesPos() {
        return new Vec3d(mc.player.posX, mc.player.posY + mc.player.getEyeHeight(), mc.player.posZ);
    }

    public static float[] getNeededRotations(Entity vec) {
        Vec3d eyesPos = getEyesPos();
        double diffX = vec.posX - eyesPos.xCoord;
        double diffY = vec.posY - eyesPos.yCoord;
        double diffZ = vec.posZ - eyesPos.zCoord;
        double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);
        float yaw = (float)Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0f;
        float pitch = (float)(-Math.toDegrees(Math.atan2(diffY, diffXZ)));
        return new float[]{
                mc.player.rotationYaw + MathHelper.wrapDegrees(yaw - mc.player.rotationYaw),
                mc.player.rotationPitch + MathHelper.wrapDegrees(pitch - mc.player.rotationPitch)
        };
    }

    public static float getDistanceFromMouse(Entity entity) {
        float[] neededRotations = getNeededRotations(entity);
        float neededYaw = mc.player.rotationYaw - neededRotations[0];
        float neededPitch = mc.player.rotationPitch - neededRotations[1];
        return MathHelper.sqrt(neededYaw * neededYaw + neededPitch * neededPitch * 2.0f);
    }
}
