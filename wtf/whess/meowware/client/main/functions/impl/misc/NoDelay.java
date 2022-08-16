package wtf.whess.meowware.client.main.functions.impl.misc;

import wtf.whess.meowware.client.api.eventsystem.EventTarget;
import wtf.whess.meowware.client.api.eventsystem.impl.PlayerUpdateEvent;
import wtf.whess.meowware.client.main.functions.Category;
import wtf.whess.meowware.client.main.functions.Function;
import wtf.whess.meowware.client.main.settings.impl.BooleanSetting;

public final class NoDelay extends Function {
    private final BooleanSetting rightClick = new BooleanSetting("Right Click", false);
    private final BooleanSetting jumpTicks = new BooleanSetting("Jump Ticks", true);

    public NoDelay() {
        super("NoDelay", Category.misc);
        addSetting(rightClick, jumpTicks);
    }

    @EventTarget
    public void onUpdate(PlayerUpdateEvent ignoredEvent) {
        if (jumpTicks.isToggled())
            mc.player.jumpTicks = 0;
        if (rightClick.isToggled())
            mc.rightClickDelayTimer = 0;
    }
}
