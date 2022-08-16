package wtf.whess.meowware.client.main.functions.impl.misc;

import net.minecraft.network.play.server.SPacketEntityHeadLook;
import wtf.whess.meowware.client.api.eventsystem.EventTarget;
import wtf.whess.meowware.client.api.eventsystem.impl.PacketReceiveEvent;
import wtf.whess.meowware.client.main.functions.Category;
import wtf.whess.meowware.client.main.functions.Function;

public final class NoServerRotate extends Function {
    public NoServerRotate() {
        super("NoServerRotate", Category.misc);
    }

    @EventTarget
    public void onPacket(PacketReceiveEvent event) {
        if (event.getPacket() instanceof SPacketEntityHeadLook && ((SPacketEntityHeadLook) event.getPacket()).getEntity(mc.world) == mc.player)
            event.setCancelled(true);
    }
}
