package wtf.whess.meowware.client.main.functions.impl.misc;

import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import wtf.whess.meowware.client.api.eventsystem.EventTarget;
import wtf.whess.meowware.client.api.eventsystem.impl.InventoryClickEvent;
import wtf.whess.meowware.client.api.eventsystem.impl.PlayerUpdateEvent;
import wtf.whess.meowware.client.api.utilities.player.ItemUtil;
import wtf.whess.meowware.client.main.functions.Category;
import wtf.whess.meowware.client.main.functions.Function;
import wtf.whess.meowware.client.main.settings.impl.NumberSetting;

public final class AutoTotem extends Function {
    private final NumberSetting health = new NumberSetting("Health", 20, 0, 100, 1);
    private final NumberSetting swapDelay = new NumberSetting("Swap delay", 10, 0, 1000, 5);

    public AutoTotem() {
        super("AutoTotem", Category.misc);
        addSetting(health, swapDelay);
    }

    private int swappedSlot = -1;
    private boolean swapping = false;

    @EventTarget
    public void onPlayerTick(PlayerUpdateEvent ignoredEvent) {
        new Thread(() -> {
            if (mc.player.getHeldItemOffhand().getItem() == Items.TOTEM_OF_UNDYING)
                swapping = false;

            if (mc.player.getHealth() / mc.player.getMaxHealth() < health.getValue() / 100f && mc.player.getHeldItemOffhand().getItem() != Items.TOTEM_OF_UNDYING && !swapping)
                swap();
            if (mc.player.getHealth() / mc.player.getMaxHealth() > health.getValue() / 100f && mc.player.getHeldItemOffhand().getItem() == Items.TOTEM_OF_UNDYING)
                swapBack();
        }).start();
    }

    @EventTarget
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getSlot() == swappedSlot)
            swappedSlot = -2;
    }

    private void swap() {
        int totemSlot = ItemUtil.getItemSlot(Items.TOTEM_OF_UNDYING);
        if (totemSlot != -999) {
            swapping = true;

            boolean offhandEmpty = !mc.player.inventoryContainer.getSlot(45).getHasStack();

            mc.playerController.windowClick(0, totemSlot, 0, ClickType.PICKUP, mc.player);

            try {
                Thread.sleep((long) swapDelay.getValue());
            } catch (Exception ignored) {}

            mc.playerController.windowClick(0, 45, 0, ClickType.PICKUP, mc.player);

            try {
                Thread.sleep((long) swapDelay.getValue());
            } catch (Exception ignored) {}

            if (!offhandEmpty) {
                int i = ItemUtil.findNearestEmptySlot();
                mc.playerController.windowClick(0, i, 0, ClickType.PICKUP, mc.player);
                swappedSlot = i;
            } else
                swappedSlot = -1;
        }
    }

    private void swapBack() {
        if (swappedSlot < 0)
            return;

        mc.playerController.windowClick(0, swappedSlot, 0, ClickType.PICKUP, mc.player);
        mc.playerController.windowClick(0, 45, 0, ClickType.PICKUP, mc.player);
        mc.playerController.windowClick(0, ItemUtil.findFartherEmptySlot(), 0, ClickType.PICKUP, mc.player);
    }
}
