package wtf.whess.meowware.client.main.functions.impl.combat;

import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraft.network.play.server.SPacketExplosion;
import wtf.whess.meowware.client.api.eventsystem.EventTarget;
import wtf.whess.meowware.client.api.eventsystem.impl.PacketSendEvent;
import wtf.whess.meowware.client.main.functions.Category;
import wtf.whess.meowware.client.main.functions.Function;

public final class Velocity extends Function {
    public Velocity() {
        super("Velocity", Category.combat);
    }

    @EventTarget
    public void onPacketSend(PacketSendEvent e) {
        if (    e.getPacket() instanceof SPacketEntityVelocity ||
                e.getPacket() instanceof SPacketExplosion)
            e.setCancelled(true);
    }
}
