package wtf.whess.meowware.client.main.functions.impl.move;

import wtf.whess.meowware.client.api.eventsystem.EventTarget;
import wtf.whess.meowware.client.api.eventsystem.impl.PlayerUpdateEvent;
import wtf.whess.meowware.client.api.utilities.player.MovementUtil;
import wtf.whess.meowware.client.main.functions.Category;
import wtf.whess.meowware.client.main.functions.Function;

public final class Speed extends Function {
    public Speed() {
        super("Speed", Category.move);
    }

    @EventTarget
    public void onUpdate(PlayerUpdateEvent ignored) {
        if (MovementUtil.isFullBlockAbove() && mc.player.fallDistance >= 1.1) {
            mc.getTimer().setTimerSpeed(2);
            mc.player.motionX *= 2;
            mc.player.motionZ *= 2;
            mc.player.motionY = -0.2;
        } else
            mc.getTimer().setTimerSpeed(1);
    }

    @Override
    public void onDisable() {
        mc.getTimer().setTimerSpeed(1);
    }
}
