package wtf.whess.meowware.client.main.functions.impl.move;

import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import wtf.whess.meowware.client.api.eventsystem.EventTarget;
import wtf.whess.meowware.client.api.eventsystem.impl.MotionEvent;
import wtf.whess.meowware.client.api.eventsystem.impl.PlayerUpdateEvent;
import wtf.whess.meowware.client.api.utilities.player.MovementUtil;
import wtf.whess.meowware.client.main.functions.Category;
import wtf.whess.meowware.client.main.functions.Function;
import wtf.whess.meowware.client.main.settings.impl.ModeSetting;
import wtf.whess.meowware.client.main.settings.impl.NumberSetting;

public final class Jesus extends Function {
    private final ModeSetting mode = new ModeSetting("Mode", "Solid", "Solid", "Zoom");
    private final NumberSetting speed = new NumberSetting("Speed", 10, 1, 10);
    private int tick;
    public boolean swap;

    public Jesus() {
        super("Jesus", Category.move);
        addSetting(mode, speed);
    }

    @EventTarget
    public void onUpdate(PlayerUpdateEvent ignoredEvent) {
        if (mode.is("Zoom")) {
            BlockPos blockPos = new BlockPos(mc.player.posX, mc.player.posY - 0.1, mc.player.posZ);
            Block block = mc.world.getBlockState(blockPos).getBlock();
            if (Block.getIdFromBlock(block) == 9) {
                mc.player.setSprinting(true);
                if (!mc.player.onGround && !mc.player.isInWater()) {
                    MovementUtil.setSpeed(speed.getValue());
                    if (mc.world.getBlockState(new BlockPos(mc.player.posX, mc.player.posY, mc.player.posZ)).getBlock() == Block.getBlockById(9)) {
                        mc.player.fallDistance = -1.0f;
                        mc.player.motionX *= 0.0;
                        mc.player.motionZ *= 0.0;
                        mc.player.motionY = 0.03930;
                    }
                } if (mc.player.isInWater() && !mc.player.isInLava()) {
                    mc.player.motionX = 0.0;
                    mc.player.motionZ = 0.0;
                    MovementUtil.setSpeed(0);
                    mc.player.jump();
                    mc.player.motionY = 0.175;
                }
            }
        }
    }

    @EventTarget
    public void onMove(MotionEvent ignored) {
        if (mode.is("Solid")) {
            if (Block.getIdFromBlock(mc.world.getBlockState(new BlockPos(mc.player.posX, mc.player.posY - 0.02, mc.player.posZ)).getBlock()) == 9) {
                mc.player.motionY = 0.08;
                MovementUtil.strafe(1);
                MovementUtil.setSpeed(speed.getValue());
            }
        }
    }
}
