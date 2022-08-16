package wtf.whess.meowware.client.main.functions.impl.move;

import wtf.whess.meowware.client.api.eventsystem.EventTarget;
import wtf.whess.meowware.client.api.eventsystem.impl.MotionEvent;
import wtf.whess.meowware.client.api.utilities.player.MovementUtil;
import wtf.whess.meowware.client.main.functions.Category;
import wtf.whess.meowware.client.main.functions.Function;

public final class Fly extends Function {
    public Fly() {
        super("Fly", Category.move);
    }

    @EventTarget
    public void onMove(MotionEvent ignoredEvent) {
        mc.player.motionY = 0.07;
        MovementUtil.setSpeed(7.5);
    }
}
