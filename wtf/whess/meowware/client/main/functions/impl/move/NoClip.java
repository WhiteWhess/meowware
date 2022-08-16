package wtf.whess.meowware.client.main.functions.impl.move;

import net.minecraft.block.Block;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.BlockPos;
import wtf.whess.meowware.client.api.eventsystem.EventTarget;
import wtf.whess.meowware.client.api.eventsystem.impl.PlayerUpdateEvent;
import wtf.whess.meowware.client.main.functions.Category;
import wtf.whess.meowware.client.main.functions.Function;

public final class NoClip extends Function {
    public NoClip() {
        super("NoClip", Category.move);
    }

    @EventTarget
    public void onUpdate(PlayerUpdateEvent ignoredEvent) {
        BlockPos blockPos = new BlockPos(mc.player.posX, mc.player.posY, mc.player.posZ);
        Block block = mc.world.getBlockState(blockPos).getBlock();
        mc.player.noClip = true;
        mc.player.isCollidedHorizontally = false;
        mc.player.isCollidedVertically = false;
        if (Block.getIdFromBlock(block) != 0) {
            if (mc.gameSettings.keyBindForward.isKeyDown()) {
                mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY, mc.player.posZ, true));
                mc.player.setPosition(mc.player.posX - Math.sin(mc.player.rotationYaw * 0.017453292) * 0.002494944989f, mc.player.posY, mc.player.posZ + Math.cos(mc.player.rotationYaw * 0.017453292) * 0.002494944989f * 4);
                mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY, mc.player.posZ, false));
            } if (mc.gameSettings.keyBindBack.isKeyDown()) {
                mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY, mc.player.posZ, true));
                mc.player.setPosition(mc.player.posX + Math.sin(mc.player.rotationYaw * 0.017453292) * 0.002494944989f, mc.player.posY, mc.player.posZ - Math.cos(mc.player.rotationYaw * 0.017453292) * 0.002494944989f * 4);
                mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY, mc.player.posZ, false));
            } if (mc.gameSettings.keyBindLeft.isKeyDown()) {
                mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY, mc.player.posZ, true));
                mc.player.setPosition(mc.player.posX - Math.sin(mc.player.rotationYaw * 0.017453292) * 0.002494944989f, mc.player.posY, mc.player.posZ - Math.cos(mc.player.rotationYaw * 0.017453292) * 0.002494944989f * 4);
                mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY, mc.player.posZ, false));
            } if (mc.gameSettings.keyBindRight.isKeyDown()) {
                mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY, mc.player.posZ, true));
                mc.player.setPosition(mc.player.posX + Math.sin(mc.player.rotationYaw * 0.017453292) * 0.002494944989f, mc.player.posY, mc.player.posZ + Math.cos(mc.player.rotationYaw * 0.017453292) * 0.002494944989f * 4);
                mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY, mc.player.posZ, false));
            }
        }
    }
}
