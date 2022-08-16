package wtf.whess.meowware.client.main.functions.impl.move;

import wtf.whess.meowware.client.api.utilities.misc.TimeUtil;
import wtf.whess.meowware.client.main.functions.Category;
import wtf.whess.meowware.client.main.functions.Function;

import java.util.concurrent.TimeUnit;

public final class HighJump extends Function {
    private final TimeUtil timeUtil = new TimeUtil();

    public HighJump() {
        super("HighJump", Category.move);
    }

    @Override
    public void onEnable() {
        if (mc.player.onGround)
            mc.player.jump();
        if (!timeUtil.hasReached(1000.0f))
            return;
        new Thread(() -> {
            mc.player.motionY = 9.0;
            try {
                TimeUnit.MILLISECONDS.sleep(200L);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
            mc.player.motionY = 8.742f;
            this.toggle();
        }).start();
        this.timeUtil.reset();
    }
}
