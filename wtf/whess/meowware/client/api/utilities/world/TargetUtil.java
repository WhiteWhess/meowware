package wtf.whess.meowware.client.api.utilities.world;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import wtf.whess.meowware.client.api.utilities.Utility;

import java.util.Comparator;

public final class TargetUtil extends Utility {
    public static Entity getTarget(double range, boolean players, boolean mobs, boolean animals, boolean invisible) {
        return mc.world.getLoadedEntityList().stream().min(Comparator.comparing(entityPlayer ->
                entityPlayer.getDistanceToEntity(mc.player))).filter(entity ->
                        entity instanceof EntityLivingBase
                        && entity != mc.player

                        && entity.getDistanceToEntity(mc.player) <= range

                        && ((entity instanceof EntityPlayer && players)
                        || (entity instanceof EntityMob | entity instanceof EntitySlime && mobs)
                        || (entity instanceof EntityAnimal && animals))

                        && (entity.isInvisible() && !invisible)

                        && entity.isEntityAlive()
                        && ((EntityLivingBase) entity).getHealth() > 0
                        && !entity.isDead
        ).orElse(null);
    }
}
