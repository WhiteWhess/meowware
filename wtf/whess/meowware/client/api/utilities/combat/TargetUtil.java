package wtf.whess.meowware.client.api.utilities.combat;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import wtf.whess.meowware.client.api.utilities.Utility;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public final class TargetUtil extends Utility {
    public static EntityLivingBase getTarget(double range, boolean players, boolean mobs, boolean animals, boolean invisible, Priority priority) {
        Comparator<Entity> comparator = null;

        switch(priority.name()) {
            case "DISTANCE": {
                comparator = Comparator.comparingDouble(enemy -> enemy.getDistanceToEntity(mc.player));
                break;
            }
            case "ANGLE": {
                comparator = Comparator.comparingDouble(FOVUtil::getAngleDifference);
                break;
            }
            case "HEALTH": {
                comparator = Comparator.comparingDouble(enemy -> ((EntityLiving) enemy).getHealth());
                break;
            }
        }

        List<Entity> possibleEnemies = mc.world.loadedEntityList.stream()
                .filter(
                        enemy -> enemy instanceof EntityLivingBase &&
                                !(enemy instanceof EntityArmorStand) &&
                                enemy != mc.player &&
                                !FriendUtil.isFriend(enemy) &&
                                !enemy.isDead &&
                                ((EntityLivingBase) enemy).getHealth() > 0 &&
                                enemy.getDistanceToEntity(mc.player) <= range &&
                                (!enemy.isInvisible() && !invisible) &&
                                (enemy instanceof EntityPlayer && players) &&
                                ((enemy instanceof EntityMob || enemy instanceof EntitySlime) && mobs) &&
                                (enemy instanceof EntityAnimal && animals)
                )
                .sorted(comparator)
                .collect(Collectors.toList());

        if(possibleEnemies.size() <= 0)
            return null;

        return (EntityLivingBase) possibleEnemies.get(0);
    }

    public static Priority parsePriority(String priority) {
        for (Priority p: Priority.values()) {
            if (p.name().equalsIgnoreCase(priority)) {
                return p;
            }
        } return null;
    }

    public enum Priority {
        DISTANCE,
        HEALTH,
        ANGLE,
    }
}
