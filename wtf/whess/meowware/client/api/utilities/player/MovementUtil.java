package wtf.whess.meowware.client.api.utilities.player;

import net.minecraft.block.state.IBlockState;
import wtf.whess.meowware.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.BlockPos;
import wtf.whess.meowware.client.api.utilities.Utility;

public final class MovementUtil extends Utility {
    public static final double WALK_SPEED = 0.221;

    public static float wrapAngleTo180_float(float p_76142_0_) {
        p_76142_0_ %= 360.0F;
        if (p_76142_0_ >= 180.0F) {
            p_76142_0_ -= 360.0F;
        }

        if (p_76142_0_ < -180.0F) {
            p_76142_0_ += 360.0F;
        }

        return p_76142_0_;
    }

    public static boolean isInputMoving() {
        return mc.player.movementInput.moveForward != 0 || mc.player.movementInput.moveStrafe != 0;
    }

    public static float getMovementDirection() {
        double forward = mc.player.movementInput.moveForward;
        double strafe = mc.player.movementInput.moveStrafe;
        float direction = 0.0f;
        if (forward < 0.0f) {
            direction += 180.0f;
            if (strafe > 0.0f) {
                direction += 45.0f;
            } else if (strafe < 0.0f) {
                direction -= 45.0f;
            }
        } else if (forward > 0.0f) {
            if (strafe > 0.0f) {
                direction -= 45.0f;
            } else if (strafe < 0.0f) {
                direction += 45.0f;
            }
        } else if (strafe > 0.0f) {
            direction -= 90.0f;
        } else if (strafe < 0.0f) {
            direction += 90.0f;
        } return wrapAngleTo180_float(direction += mc.player.rotationYaw);
    }

    public static boolean isFullBlockAbove() {
        IBlockState blockState = mc.world.getBlockState(new BlockPos(mc.player.posX, mc.player.posY - 0.13, mc.player.posZ));
        return blockState.getBlock() != Blocks.AIR && blockState.isFullBlock();
    }

    public static boolean isBlockAbove() {
        IBlockState blockState = mc.world.getBlockState(new BlockPos(mc.player.posX, mc.player.posY - 0.13, mc.player.posZ));
        return blockState.getBlock() != Blocks.AIR;
    }

    public static float getDirection() {
        Minecraft mc = Minecraft.getMinecraft();
        float var1 = mc.player.rotationYaw;
        if (mc.player.moveForward < 0.0f) {
            var1 += 180.0f;
        } float forward = 1.0f;
        if (mc.player.moveForward < 0.0f) {
            forward = -0.5f;
        } else if (mc.player.moveForward > 0.0f) {
            forward = 0.5f;
        } if (mc.player.moveStrafing > 0.0f) {
            var1 -= 90.0f * forward;
        } if (mc.player.moveStrafing < 0.0f) {
            var1 += 90.0f * forward;
        } return var1 *= (float)Math.PI / 180;
    }

    public static double getXDirAt(float angle) {
        Minecraft mc = Minecraft.getMinecraft();
        double rot = 90.0;
        return Math.cos((rot += angle) * Math.PI / 180.0);
    }

    public static double getZDirAt(float angle) {
        Minecraft mc = Minecraft.getMinecraft();
        double rot = 90.0;
        return Math.sin((rot += angle) * Math.PI / 180.0);
    }

    public static void setSpeed(double speed) {
        double forward = mc.player.movementInput.moveForward;
        double strafe = mc.player.movementInput.moveStrafe;
        float yaw = mc.player.rotationYaw;
        if (forward == 0.0 && strafe == 0.0) {
            mc.player.motionX = 0.0;
            mc.player.motionZ = 0.0;
        } else {
            if (forward != 0.0) {
                if (strafe > 0.0) {
                    yaw += (float)(forward > 0.0 ? -45 : 45);
                } else if (strafe < 0.0) {
                    yaw += (float)(forward > 0.0 ? 45 : -45);
                }
                strafe = 0.0;
                if (forward > 0.0) {
                    forward = 1.0;
                } else if (forward < 0.0) {
                    forward = -1.0;
                }
            } mc.player.motionX = forward * speed * Math.cos(Math.toRadians(yaw + 90.0f)) + strafe * speed * Math.sin(Math.toRadians(yaw + 90.0f));
            mc.player.motionZ = forward * speed * Math.sin(Math.toRadians(yaw + 90.0f)) - strafe * speed * Math.cos(Math.toRadians(yaw + 90.0f));
        }
    }

    public static void strafe() {
        strafe(getSpeed());
    }

    public static float getSpeed() {
        return (float)Math.sqrt(mc.player.motionX * mc.player.motionX + mc.player.motionZ * mc.player.motionZ);
    }

    public static boolean isMoving() {
        if (mc.player == null) return false;
        if (mc.player.moveForward != 0.0f) return true;
        return mc.player.moveStrafing != 0.0f;
    }

    public static boolean hasMotion() {
        if (mc.player.motionX == 0.0) return false;
        if (mc.player.motionZ == 0.0) return false;
        return mc.player.motionY != 0.0;
    }

    public static void strafe(float speed) {
        if (!isMoving()) {
            return;
        } double yaw = getDirection();
        mc.player.motionX = -Math.sin(yaw) * (double)speed;
        mc.player.motionZ = Math.cos(yaw) * (double)speed;
    }

    public static boolean moveKeysDown() {
        Minecraft mc = Minecraft.getMinecraft();
        if (mc.player.moveForward != 0.0f) return true;
        return mc.player.moveStrafing != 0.0f;
    }

    public static double getPressedMoveDir() {
        Minecraft mc = Minecraft.getMinecraft();
        double rot = Math.atan2(mc.player.moveForward, mc.player.moveStrafing) / Math.PI * 180.0;
        if (rot == 0.0) {
            if (mc.player.moveStrafing == 0.0f) {
                rot = 90.0;
            }
        } return (rot += mc.player.rotationYaw) - 90.0;
    }

    public static double getPlayerMoveDir() {
        Minecraft mc = Minecraft.getMinecraft();
        double xspeed = mc.player.motionX;
        double zspeed = mc.player.motionZ;
        double direction = Math.atan2(xspeed, zspeed) / Math.PI * 180.0;
        return -direction;
    }

    public static void startFakePos() {
        mc.player.setPosition(mc.player.posX, mc.player.posY + 0.3, mc.player.posZ);
        double x = mc.player.posX;
        double y = mc.player.posY;
        double z = mc.player.posZ;
        for (int i = 0; i < 3000; ++i) {
            mc.player.connection.sendPacket(new CPacketPlayer.Position(x, y + 0.09999999999999, z, false));
            mc.player.connection.sendPacket(new CPacketPlayer.Position(x, y, z, true));
        } mc.player.motionY = 0.0;
    }

    public static String getEntityCoordinates(Entity entity) {
        return entity.posX + " " + entity.posY + " " + entity.posZ;
    }
}
