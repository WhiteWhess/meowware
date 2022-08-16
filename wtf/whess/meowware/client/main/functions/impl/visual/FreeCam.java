package wtf.whess.meowware.client.main.functions.impl.visual;

import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.network.play.client.CPacketPlayer;
import wtf.whess.meowware.client.api.eventsystem.EventTarget;
import wtf.whess.meowware.client.api.eventsystem.impl.PacketReceiveEvent;
import wtf.whess.meowware.client.api.eventsystem.impl.PlayerUpdateEvent;
import wtf.whess.meowware.client.api.utilities.player.MovementUtil;
import wtf.whess.meowware.client.main.functions.Category;
import wtf.whess.meowware.client.main.functions.Function;

public final class FreeCam extends Function {
    private EntityOtherPlayerMP fakePlayer;
    public FreeCam() {
        super("FreeCam", Category.visual);
    }

    @Override
    public void onEnable() {
        mc.player.setPositionAndRotation(mc.player.posX, mc.player.posY, mc.player.posZ, mc.player.rotationYaw, mc.player.rotationPitch);
        fakePlayer = new EntityOtherPlayerMP(mc.world, mc.player.getGameProfile());
        fakePlayer.setEntityId(-1882);
        fakePlayer.copyLocationAndAnglesFrom(mc.player);
        fakePlayer.rotationYawHead = mc.player.rotationYawHead;
        fakePlayer.rotationPitchHead = mc.player.rotationPitchHead;
        mc.world.addEntityToWorld(fakePlayer.getEntityId(), fakePlayer);
        if (mc.player.isRiding()) {
            assert mc.player.getRidingEntity() != null;
            mc.player.startRiding(mc.player.getRidingEntity(), true);
        }
    }

    @EventTarget
    public void onPacketReceive(PacketReceiveEvent event) {
        if (event.getPacket() instanceof CPacketPlayer)
            event.setCancelled(true);
    }

    @EventTarget
    public void onUpdate(PlayerUpdateEvent ignoredEvent) {
        mc.player.noClip = true;
        mc.player.onGround = false;
        mc.player.fallDistance = 0.0f;
        mc.player.motionY = 0;
        if (mc.gameSettings.keyBindJump.isKeyDown())
            mc.player.motionY = 0.82;
        else if (mc.gameSettings.keyBindSneak.isKeyDown())
            mc.player.motionY = -0.82;
        MovementUtil.setSpeed(1);
    }

    @Override
    public void onDisable() {
        mc.world.removeEntityFromWorld(fakePlayer.getEntityId());
    }
}
