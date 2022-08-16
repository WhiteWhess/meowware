package wtf.whess.meowware.client.main.functions.impl.move;

import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketEntityAction;
import wtf.whess.meowware.client.api.eventsystem.EventTarget;
import wtf.whess.meowware.client.api.eventsystem.impl.PlayerUpdateEvent;
import wtf.whess.meowware.client.api.utilities.misc.MathUtil;
import wtf.whess.meowware.client.api.utilities.player.ItemUtil;
import wtf.whess.meowware.client.api.utilities.player.MovementUtil;
import wtf.whess.meowware.client.main.functions.Category;
import wtf.whess.meowware.client.main.functions.Function;

public final class Glide extends Function {
    public static long lastStartFalling;
    public Glide() {
        super("Glide", Category.move);
    }

    @EventTarget
    public void onUpdate(PlayerUpdateEvent ignoredEvent) {
        int elytraSlot = ItemUtil.getItemSlot(Items.ELYTRA);
        swap();

        if (mc.player.ticksExisted % 18 == 0) {
            disabler(elytraSlot);
        }

        mc.player.motionY = 0.2f;

        if (mc.player.ticksExisted % 20==0){
            mc.player.motionY-=0.3D;
        }

        if (!mc.player.onGround && MovementUtil.isInputMoving()) {
            float f = (float) Math.toRadians(mc.player.rotationYaw);
            mc.player.motionX -= MathUtil.sin(f) * 0.21F;
            mc.player.motionZ += MathUtil.cos(f) * 0.21F;
        }
    }

    public static void swap() {
        for (ItemStack stack : mc.player.inventory.armorInventory) {
            if (stack.getItem() == Items.ELYTRA) {
                int chest = getItem();
                int slot = chest < 9 ? chest + 36 : chest;
                if (chest != -1) {
                    pick(slot, 1);
                    pick(6, 1);
                    pick(slot, 0);
                }
            }
        }
    }

    private static void pick(int slot, int button) {
        mc.playerController.windowClick(0, slot, button, ClickType.PICKUP, mc.player);
    }

    private static int getItem() {
        int find = -1;
        for (int i = 0; i <= 8; i++)
            if (mc.player.inventory.getStackInSlot(i).getItem() == Items.DIAMOND_CHESTPLATE)
                find = i;
        return find;
    }

    private int getElytra() {
        for (int i = 0; i < 45; ++i)
            if (mc.player.openContainer.getSlot(i).getStack().getItem() == Items.ELYTRA)
                return i;
        return -1;
    }

    private void disabler(int elytraSlot) {
        mc.playerController.windowClick(0, elytraSlot, 0, ClickType.PICKUP, mc.player);
        mc.playerController.windowClick(0, 6, 0, ClickType.PICKUP, mc.player);
        mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.START_FALL_FLYING));
        mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.START_FALL_FLYING));
        mc.playerController.windowClick(0, 6, 0, ClickType.PICKUP, mc.player);
        mc.playerController.windowClick(0, elytraSlot, 0, ClickType.PICKUP, mc.player);
    }

    public static void swap(int elytra) {
        if (elytra != -2) {
            mc.playerController.windowClick(0, elytra, 1, ClickType.PICKUP, mc.player);
            mc.playerController.windowClick(0, 6, 1, ClickType.PICKUP, mc.player);
        }

        mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.START_FALL_FLYING));
        mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.START_FALL_FLYING));
        if (elytra != -2) {
            mc.playerController.windowClick(0, 6, 1, ClickType.PICKUP, mc.player);
            mc.playerController.windowClick(0, elytra, 1, ClickType.PICKUP, mc.player);
        }

        lastStartFalling = System.currentTimeMillis();
    }
}
