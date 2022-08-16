package wtf.whess.meowware.client.main.functions.impl.move;

import wtf.whess.meowware.client.api.eventsystem.EventTarget;
import wtf.whess.meowware.client.api.eventsystem.impl.PlayerUpdateEvent;
import wtf.whess.meowware.client.main.functions.Category;
import wtf.whess.meowware.client.main.functions.Function;

public final class NoSlowDown extends Function {
    public NoSlowDown() {
        super("NoSlowDown", Category.move);
    }

    @EventTarget
    public void onUpdate(PlayerUpdateEvent ignored) {
        if (mc.player.isHandActive()) {
            mc.player.movementInput.moveForward *= 5F * 98;
            mc.player.movementInput.moveStrafe *= 5F * 98;
            mc.player.setSprinting(true);

            if (mc.player.onGround && !mc.gameSettings.keyBindJump.isKeyDown()) {
                if (mc.player.ticksExisted % 2 == 0) {
                    mc.player.motionX *= 0.46;
                    mc.player.motionZ *= 0.46;
                }
            } else if (mc.player.fallDistance > 0.2) {
                mc.player.motionX *= 0.98;
                mc.player.motionZ *= 0.98;
            }
        }

    }
}
