package wtf.whess.meowware.client.main.functions.impl.combat;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.CPacketChatMessage;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.MathHelper;
import wtf.whess.meowware.client.api.eventsystem.EventTarget;
import wtf.whess.meowware.client.api.eventsystem.impl.PlayerUpdateEvent;
import wtf.whess.meowware.client.api.utilities.combat.RotationUtil;
import wtf.whess.meowware.client.main.functions.Category;
import wtf.whess.meowware.client.main.functions.Function;
import wtf.whess.meowware.client.main.settings.impl.BooleanSetting;
import wtf.whess.meowware.client.main.settings.impl.NumberSetting;

import java.util.Comparator;
import java.util.Random;

public final class Aura extends Function {
    private static final Random random = new Random();

    private static final NumberSetting RANGE = new NumberSetting("Range", 3.5, 2, 4.5);
    private final BooleanSetting criticals = new BooleanSetting("Criticals", true);
    private final BooleanSetting roflMessages = new BooleanSetting("Rofl Messages", false);

    private static final String[] ROFL_MESSAGES = new String[] {
            "хе падай нищ",
            "гетни мяувар нищ",
            "хехе упал опять нищ без мяувара",
            "вхвх гетни мяувар не терпи"
    };

    public Aura() {
        super("Aura", Category.combat);
        addSetting(RANGE, criticals, roflMessages);
    }

    @EventTarget
    public void onUpdate(PlayerUpdateEvent event) {
        if (getTarget() == null)
            return;

        event.setYaw(rotation(getTarget())[0]);
        mc.player.setRotationYawHead(rotation(getTarget())[0]);
        mc.player.setRenderYawOffset(rotation(getTarget())[0]);
        event.setPitch(rotation(getTarget())[1]);
        mc.player.setRotationPitchHead(rotation(getTarget())[1]);

        if (criticals.isToggled() && mc.player.fallDistance < 0.1)
            return;

        if (mc.player.getCooledAttackStrength(0) >= 0.94) {
            mc.playerController.attackEntity(mc.player, getTarget());
            mc.player.swingArm(EnumHand.MAIN_HAND);
            mc.player.resetCooldown();
            if (roflMessages.isToggled())
                mc.player.connection.sendPacket(new CPacketChatMessage(getRoflMessage()));
        }
    }

    public static EntityPlayer getTarget() {
        return mc.world.playerEntities.stream().filter(entityPlayer -> entityPlayer != mc.player).min(Comparator.comparing(entityPlayer ->
                entityPlayer.getDistanceToEntity(mc.player))).filter(entityPlayer -> entityPlayer.getDistanceToEntity(mc.player) <= RANGE.getValue()).orElse(null);
    }

    public static String getRoflMessage() {
        return ROFL_MESSAGES[random.nextInt(ROFL_MESSAGES.length)];
    }

    private float[] rotation(Entity entity) {
        double diffX = entity.posX - mc.player.posX;
        double diffZ = entity.posZ - mc.player.posZ;
        double diffY = entity.posY + entity.getEyeHeight() - (mc.player.posY + mc.player.getEyeHeight()) - 0.44;

        double dist = MathHelper.sqrt(diffX * diffX + diffZ * diffZ);

        float yaw = (float) (((Math.atan2(diffZ, diffX) * 180 / Math.PI) - 90)) + 1;
        float pitch = (float) (-(Math.atan2(diffY, dist) * 180 / Math.PI));

        yaw = mc.player.prevRotationYaw + RotationUtil.getFixedRotation(MathHelper.wrapDegrees(yaw - mc.player.rotationYaw));
        pitch = MathHelper.clamp(mc.player.prevRotationPitch + RotationUtil.getFixedRotation(MathHelper.wrapDegrees(pitch - mc.player.rotationPitch)), -180, 180);

        return new float[] {
                yaw,
                pitch
        };
    }
}
