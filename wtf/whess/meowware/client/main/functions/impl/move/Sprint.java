package wtf.whess.meowware.client.main.functions.impl.move;

import wtf.whess.meowware.client.api.eventsystem.EventTarget;
import wtf.whess.meowware.client.api.eventsystem.impl.PlayerUpdateEvent;
import wtf.whess.meowware.client.main.functions.Category;
import wtf.whess.meowware.client.main.functions.Function;

public final class Sprint extends Function {
    public Sprint() {
        super("Sprint", Category.move);
    }

    @EventTarget
    public void onUpdate(PlayerUpdateEvent ignored) {
        if (mc.player.getFoodStats().getFoodLevel() < 4)
            return;
        if (mc.gameSettings.keyBindForward.isKeyDown())
            mc.player.setSprinting(true);
    }
}
