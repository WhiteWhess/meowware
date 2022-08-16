package wtf.whess.meowware.client.api.utilities.combat;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import wtf.whess.meowware.client.api.utilities.Utility;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public final class MultipointUtil extends Utility {
    public static Vec3d getPosition(Entity entity, double accuracy) {
        double
                renderOffsetX = (entity.posX - entity.prevPosX) * mc.getRenderPartialTicks(),
                renderOffsetY = (entity.posY - entity.prevPosY) * mc.getRenderPartialTicks(),
                renderOffsetZ = (entity.posZ - entity.prevPosZ) * mc.getRenderPartialTicks();

        AxisAlignedBB aabb = entity.getEntityBoundingBox().offset(renderOffsetX, renderOffsetY, renderOffsetZ);

        List<Vec3d> points = generateMultipoints(aabb, accuracy);

        if (points.isEmpty())
            return null;

        points.sort(Comparator.comparingDouble(multipoint -> multipoint.distanceTo(mc.player.getPositionEyes(mc.getRenderPartialTicks()))));

        return points.get(0);
    }

    private static List<Vec3d> generateMultipoints(AxisAlignedBB aabb, double accuracy) {
        List<Vec3d> multipoints = new ArrayList<>();

        accuracy = 1D / accuracy;

        for (double x = aabb.minX; x < aabb.maxX; x += accuracy * (aabb.maxX - aabb.minX))
            for (double y = aabb.minY; y < aabb.maxY; y += accuracy * (aabb.maxY - aabb.minY))
                for (double z = aabb.minZ; z < aabb.maxZ; z += accuracy * (aabb.maxZ - aabb.minZ))
                    multipoints.add(new Vec3d(x, y, z));

        return multipoints;
    }
}
