package wtf.whess.meowware.client.main.functions.impl.move;

import wtf.whess.meowware.client.api.eventsystem.EventTarget;
import wtf.whess.meowware.client.api.eventsystem.impl.PlayerUpdateEvent;
import wtf.whess.meowware.client.main.functions.Category;
import wtf.whess.meowware.client.main.functions.Function;

public final class Spider extends Function {
    public Spider() {
        super("Spider", Category.move);
    }

    @EventTarget
    public void onUpdate(PlayerUpdateEvent event) {
        if (!mc.player.isCollidedHorizontally)
            return;
        if (mc.player.ticksExisted % 6 == 0) {
            event.setGround(true);
            mc.player.jump();
        }
    }
}
