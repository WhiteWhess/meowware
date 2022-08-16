package wtf.whess.meowware.client.main.functions.impl.move;

import net.minecraft.network.play.server.SPacketEntityVelocity;
import wtf.whess.meowware.client.api.eventsystem.EventTarget;
import wtf.whess.meowware.client.api.eventsystem.impl.PacketReceiveEvent;
import wtf.whess.meowware.client.api.eventsystem.impl.PlayerUpdateEvent;
import wtf.whess.meowware.client.api.utilities.player.MovementUtil;
import wtf.whess.meowware.client.main.functions.Category;
import wtf.whess.meowware.client.main.functions.Function;

public final class DamageFly extends Function {
    boolean getDamage;
    boolean isVelocity;
    int tick;
    boolean velocity;
    double motion;

    public DamageFly() {
        super("DamageFly", Category.move);
    }

    @EventTarget
    private void onPacket(PacketReceiveEvent event) {
        if (!(event.getPacket() instanceof SPacketEntityVelocity)) return;
        if (((SPacketEntityVelocity) event.getPacket()).getMotionY() > 0) {
            this.isVelocity = true;
        }
        if (!((double)((SPacketEntityVelocity)event.getPacket()).getMotionY() / 8000.0 > 0.2)) return;
        this.motion = (double)((SPacketEntityVelocity)event.getPacket()).getMotionY() / 8000.0;
        this.velocity = true;
    }

    @EventTarget
    public void onUpdate(PlayerUpdateEvent ignoredEvent) {
        if (mc.player.hurtTime == 9) {
            this.getDamage = true;
        }
        if (this.getDamage) {
            this.tick = (int)((float)this.tick + 1.0f / mc.getTimer().getTimerSpeed());
            mc.player.motionY = this.motion;
            mc.player.jumpMovementFactor = 0.4f;
            MovementUtil.setSpeed(MovementUtil.getSpeed());
        }
        if (this.tick < 24) return;
        this.getDamage = false;
        this.tick = 0;
    }

    @Override
    public void onDisable() {
        mc.getTimer().setTimerSpeed(1.0f);
    }
}
