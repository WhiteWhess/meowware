package wtf.whess.meowware.client.main.functions.impl.misc;

import wtf.whess.meowware.client.api.eventsystem.EventTarget;
import wtf.whess.meowware.client.api.eventsystem.impl.ClientTickEvent;
import wtf.whess.meowware.client.api.utilities.misc.TimeUtil;
import wtf.whess.meowware.client.main.functions.Category;
import wtf.whess.meowware.client.main.functions.Function;

public final class MemoryFix extends Function {
    public MemoryFix() {
        super("MemoryFix", Category.misc);
    }

    private final TimeUtil timeUtil = new TimeUtil();
    private final Runtime runtime = Runtime.getRuntime();
    private int free, max;

    @EventTarget
    public void onMemoryFix(ClientTickEvent ignoredEvent) {
        if (timeUtil.hasReached(3000)) {
            free = (int) runtime.freeMemory();
            max = (int) runtime.maxMemory();
            timeUtil.reset();
        } if (free > (max / 2))
            return;
        System.gc();
    }
}
