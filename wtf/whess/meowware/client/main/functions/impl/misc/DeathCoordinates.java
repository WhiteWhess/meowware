package wtf.whess.meowware.client.main.functions.impl.misc;

import wtf.whess.meowware.client.api.eventsystem.EventTarget;
import wtf.whess.meowware.client.api.eventsystem.impl.PlayerUpdateEvent;
import wtf.whess.meowware.client.api.utilities.client.ChatUtil;
import wtf.whess.meowware.client.api.utilities.player.MovementUtil;
import wtf.whess.meowware.client.main.functions.Category;
import wtf.whess.meowware.client.main.functions.Function;

public final class DeathCoordinates extends Function {
    public DeathCoordinates() {
        super("DeathCoordinates", Category.misc);
    }

    @EventTarget
    public void onUpdate(PlayerUpdateEvent ignoredEvent) {
        if (mc.player.isDead && !mc.player.prevIsDead) {
            ChatUtil.printChat("&fDeath coordinates&7: &f" + MovementUtil.getEntityCoordinates(mc.player));
        }
    }
}
