package wtf.whess.meowware.client.main.functions.impl.misc;

import net.minecraft.client.gui.GuiGameOver;
import wtf.whess.meowware.client.api.eventsystem.EventTarget;
import wtf.whess.meowware.client.api.eventsystem.impl.PlayerUpdateEvent;
import wtf.whess.meowware.client.main.functions.Category;
import wtf.whess.meowware.client.main.functions.Function;

public final class AutoRespawn extends Function {
    public AutoRespawn() {
        super("AutoRespawn", Category.misc);
    }

    @EventTarget
    public void onUpdate(PlayerUpdateEvent ignoredEvent) {
        if (mc.player.getHealth() < 0 || !mc.player.isEntityAlive() || mc.currentScreen instanceof GuiGameOver) {
            mc.player.respawnPlayer();
            mc.displayGuiScreen(null);
        }
    }
}
