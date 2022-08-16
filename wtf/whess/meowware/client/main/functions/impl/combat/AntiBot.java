package wtf.whess.meowware.client.main.functions.impl.combat;

import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.Entity;
import wtf.whess.meowware.client.api.eventsystem.EventTarget;
import wtf.whess.meowware.client.api.eventsystem.impl.ClientTickEvent;
import wtf.whess.meowware.client.main.functions.Category;
import wtf.whess.meowware.client.main.functions.Function;

import java.util.ArrayList;
import java.util.List;

public final class AntiBot extends Function {
    public static List<Entity> isBotPlayer = new ArrayList<>();

    public AntiBot() {
        super("AntiBot", Category.combat);
    }

    @EventTarget
    public void onPlayerTick(ClientTickEvent ignoredEvent) {
        for (Entity entity : mc.world.loadedEntityList) {
            if (entity != mc.player && entity.ticksExisted < 5 && entity instanceof EntityOtherPlayerMP) {
                if (((EntityOtherPlayerMP) entity).hurtTime > 0 && mc.player.getDistanceToEntity(entity) <= 25 && mc.player.connection.getPlayerInfo(entity.getUniqueID()).getResponseTime() != 0) {
                    isBotPlayer.add(entity);
                }
            }
        }
    }
}
