package wtf.whess.meowware.client.api.utilities.combat;

import net.minecraft.init.MobEffects;
import wtf.whess.meowware.client.api.utilities.Utility;

public final class CriticalUtil extends Utility {
    public static boolean canCriticalHit(float fallDistance) {
        return (mc.player.fallDistance > fallDistance && !mc.player.onGround) ||
                mc.player.isInWater() ||
                mc.player.isInWeb ||
                mc.player.isPotionActive(MobEffects.BLINDNESS) ||
                mc.player.isOnLadder() ||
                mc.player.isRiding();
    }
}
