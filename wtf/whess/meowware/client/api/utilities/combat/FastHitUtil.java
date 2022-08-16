package wtf.whess.meowware.client.api.utilities.combat;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import wtf.whess.meowware.client.api.utilities.Utility;
import wtf.whess.meowware.client.api.utilities.misc.MathUtil;

public final class FastHitUtil extends Utility {
    public static float getValue(Entity entity) {
        if (((EntityLivingBase) (entity)).getHealth() <= 6) {
            float dmg = mc.player.inventory.getCurrentItem().getItemDamage() / 5.0F;
            return MathUtil.clamp(dmg, 0, 0.9F);
        } return 0.945F;
    }
}
